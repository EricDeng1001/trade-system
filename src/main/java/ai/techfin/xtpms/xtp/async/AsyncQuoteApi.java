package ai.techfin.xtpms.xtp.async;

import com.zts.xtp.common.enums.JniLogLevel;
import com.zts.xtp.common.enums.MarketType;
import com.zts.xtp.common.enums.TransferProtocol;
import com.zts.xtp.common.enums.XtpLogLevel;
import com.zts.xtp.common.model.ErrorMessage;
import com.zts.xtp.quote.api.QuoteApi;
import com.zts.xtp.quote.model.response.TickerPriceInfoResponse;
import com.zts.xtp.quote.spi.QuoteSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@PropertySource("classpath:config/xtp.properties")
@Component("asyncQuoteApi")
@Lazy
public class AsyncQuoteApi implements QuoteSpi, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncQuoteApi.class);

    private final QuoteApi quoteApi;

    private final String USERNAME;

    private final String PASSWORD;

    private final String XTP_QUOTE_SERVER_IP;

    private final int XTP_QUOTE_SERVER_PORT;

    private final String LOG_FOLDER;

    @Autowired
    public AsyncQuoteApi(
            @Value("${logFolder}") String LOG_FOLDER,
            @Value("${quoteUser}") String USERNAME,
            @Value("${quoteUserPassword}") String PASSWORD,
            @Value("${quoteServerIP}") String XTP_QUOTE_SERVER_IP,
            @Value("${quoteServerPort}") int XTP_QUOTE_SERVER_PORT) {
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.XTP_QUOTE_SERVER_IP = XTP_QUOTE_SERVER_IP;
        this.XTP_QUOTE_SERVER_PORT = XTP_QUOTE_SERVER_PORT;
        this.LOG_FOLDER = LOG_FOLDER;
        this.quoteApi = new QuoteApi(this);
        LOGGER.info("AsyncQuoteApi Bean Constructor start ");
    }

    @Override
    public void onDisconnected(int reason) {
        LOGGER.info("disconnected: " + reason);
        int status;
        do {
            status = quoteApi.login(
                    XTP_QUOTE_SERVER_IP, XTP_QUOTE_SERVER_PORT,
                    USERNAME, PASSWORD, TransferProtocol.XTP_PROTOCOL_TCP);
        } while (status != 0);
    }

    @Async
    public void queryTickerPrice(String ticker, MarketType marketType) {
        quoteApi.queryTickersPriceInfo(new String[]{ticker}, 1, marketType.getQuoteType());
        LOGGER.info("QueryTickerPrice invoke");
    }

    @Override
    public void onQueryTickersPriceInfo(TickerPriceInfoResponse tickerInfo, ErrorMessage errorMessage) {
        if (errorMessage == null || errorMessage.getErrorId() == 0) {
            LOGGER.info("TickersPriceInfo : {}", tickerInfo);
        } else {
            LOGGER.error("Get TickersPriceInfo Error : {} " + errorMessage);
        }
        //kafka send
    }

    void close() {
        quoteApi.logout();
        quoteApi.disconnect();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        quoteApi.init(AsyncTradeApi.clientId, LOG_FOLDER, XtpLogLevel.XTP_LOG_LEVEL_INFO,
                JniLogLevel.JNI_LOG_LEVEL_ERROR);
        quoteApi.setUDPBufferSize(512);

        quoteApi.setHeartBeatInterval(180);
        int status = quoteApi.login(XTP_QUOTE_SERVER_IP, XTP_QUOTE_SERVER_PORT,
                USERNAME, PASSWORD,
                TransferProtocol.XTP_PROTOCOL_TCP);

        if (status != 0) {
            throw new Error("quote api login failed: " + status);
        }
        LOGGER.info("quoteApi init");
    }
}
