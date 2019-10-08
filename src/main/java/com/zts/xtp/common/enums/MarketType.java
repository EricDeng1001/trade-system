package com.zts.xtp.common.enums;


/**
 * 市场类型
 */
public enum MarketType {
    /**
     * 初始化值或者未知
     */
    XTP_MKT_INIT(0, 0),
    /**
     * 深圳A股
     */
    XTP_MKT_SZ_A(1, 2),
    /**
     * 上海A股
     */
    XTP_MKT_SH_A(2, 1),
    /**
     * 未知交易市场类型
     */
    XTP_MKT_UNKNOWN(3, 3);

    private final int tradeType;

    private final int quoteType;

    // must keep this for jni
    public int type;

    MarketType(int tradeType, int quoteType) {
        this.type = tradeType;
        this.tradeType = tradeType;
        this.quoteType = quoteType;
    }

    public static MarketType forType(int type) {
        switch (type) {
            case 0:
                return XTP_MKT_INIT;
            case 1:
                return XTP_MKT_SZ_A;
            case 2:
                return XTP_MKT_SH_A;
            default:
                return XTP_MKT_UNKNOWN;
        }
    }

    public static MarketType forType(String type) {
        switch (type) {
            case "INIT":
                return XTP_MKT_INIT;
            case "SZ":
                return XTP_MKT_SZ_A;
            case "SH":
                return XTP_MKT_SH_A;
            default:
                return XTP_MKT_UNKNOWN;
        }
    }

    public int getType() { return type; }

    public int getTradeType() { return tradeType; }

    public int getQuoteType() { return quoteType; }

}
