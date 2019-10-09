package ai.techfin.xtpms.adapter;

import ai.techfin.tradesystem.config.KafkaTopicConfiguration;
import ai.techfin.tradesystem.domain.enums.BrokerType;
import ai.techfin.tradesystem.service.dto.PriceUpdateDTO;
import ai.techfin.xtpms.service.broker.mapper.PriceUpdateDTOMapper;
import com.zts.xtp.common.enums.JniLogLevel;
import com.zts.xtp.common.enums.MarketType;
import com.zts.xtp.common.enums.TransferProtocol;
import com.zts.xtp.common.enums.XtpLogLevel;
import com.zts.xtp.quote.api.QuoteApi;
import com.zts.xtp.quote.model.response.DepthMarketDataExResponse;
import com.zts.xtp.quote.model.response.DepthMarketDataResponse;
import com.zts.xtp.quote.spi.QuoteSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@PropertySource("classpath:config/xtp.properties")
@Component("quoteApi")
public class AsyncQuoteApi implements QuoteSpi {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncQuoteApi.class);

    private final QuoteApi quoteApi;

    private final String USERNAME;

    private final String PASSWORD;

    private final String XTP_QUOTE_SERVER_IP;

    private final int XTP_QUOTE_SERVER_PORT;

    private final String LOG_FOLDER;

    private final KafkaTemplate<String, PriceUpdateDTO> kafkaTemplate;

    private final PriceUpdateDTOMapper priceUpdateDTOMapper;

    private final ConcurrentHashMap<String, Double> subStockPrice = new ConcurrentHashMap<>();

    @Autowired
    public AsyncQuoteApi(
            @Value("${logFolder}") String LOG_FOLDER,
            @Value("${quoteUser}") String USERNAME,
            @Value("${quoteUserPassword}") String PASSWORD,
            @Value("${quoteServerIP}") String XTP_QUOTE_SERVER_IP,
            @Value("${quoteServerPort}") int XTP_QUOTE_SERVER_PORT,
            KafkaTemplate<String, PriceUpdateDTO> kafkaTemplate,
            PriceUpdateDTOMapper priceUpdateDTOMapper) {
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.XTP_QUOTE_SERVER_IP = XTP_QUOTE_SERVER_IP;
        this.XTP_QUOTE_SERVER_PORT = XTP_QUOTE_SERVER_PORT;
        this.LOG_FOLDER = LOG_FOLDER;
        this.quoteApi = new QuoteApi(this);
        this.kafkaTemplate = kafkaTemplate;
        this.priceUpdateDTOMapper = priceUpdateDTOMapper;
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

    void close() {
        quoteApi.logout();
        quoteApi.disconnect();
    }


    public boolean init() {
        quoteApi.init(AsyncTradeApi.clientId, LOG_FOLDER, XtpLogLevel.XTP_LOG_LEVEL_INFO,
                JniLogLevel.JNI_LOG_LEVEL_ERROR);
        quoteApi.setUDPBufferSize(512);

        quoteApi.setHeartBeatInterval(180);
        int status = quoteApi.login(XTP_QUOTE_SERVER_IP, XTP_QUOTE_SERVER_PORT,
                USERNAME, PASSWORD,
                TransferProtocol.XTP_PROTOCOL_TCP);

        if (status != 0) {
            LOGGER.error("quote api login failed: " + status);
            return false;
        }
        LOGGER.info("quoteApi init");
        return true;
    }


    public void subscribePrice(String name, MarketType market) {
        this.quoteApi.subscribeMarketData(new String[]{name}, 1, market.getQuoteType());
    }


    @Override
    public void onDepthMarketData(DepthMarketDataResponse depthMarketData, DepthMarketDataExResponse depthQuote) {
        String ticker = depthMarketData.getTicker();
        if(!subStockPrice.containsKey(ticker) || !(subStockPrice.get(ticker) == depthMarketData.getLastPrice())){
            subStockPrice.put(ticker,depthMarketData.getLastPrice());
            PriceUpdateDTO priceUpdateDTO = priceUpdateDTOMapper.priceToPriceDto(depthMarketData);
            priceUpdateDTO.setBroker(BrokerType.XTP);
            kafkaTemplate.send(KafkaTopicConfiguration.XTP_PRICE_CHANGE_TOPIC,priceUpdateDTO);
            LOGGER.info("priceUpdateDTO : {}", priceUpdateDTO);
        }
    }
}

