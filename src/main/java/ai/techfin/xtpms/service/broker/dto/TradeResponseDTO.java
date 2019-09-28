package ai.techfin.xtpms.service.broker.dto;

import ai.techfin.tradesystem.domain.enums.MarketType;
import ai.techfin.tradesystem.domain.enums.SideType;

import java.io.Serializable;

public class TradeResponseDTO implements Serializable {

    private String placementId;

    private String tradeId;

    private long quantity;

    private double price;

    private String user;

    private String ticker;

    private MarketType marketType;

    private SideType sideType;

    public String getPlacementId() {
        return placementId;
    }

    public void setPlacementId(String placementId) {
        this.placementId = placementId;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public MarketType getMarketType() {
        return marketType;
    }

    public void setMarketType(MarketType marketType) {
        this.marketType = marketType;
    }

    public SideType getSideType() {
        return sideType;
    }

    public void setSideType(SideType sideType) {
        this.sideType = sideType;
    }

    public TradeResponseDTO() {
    }

    @Override
    public String toString() {
        return "TradeResponseDTO{" +
                "placementId='" + placementId + '\'' +
                ", tradeId='" + tradeId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", user='" + user + '\'' +
                ", ticker='" + ticker + '\'' +
                ", marketType=" + marketType +
                ", sideType=" + sideType +
                '}';
    }
}
