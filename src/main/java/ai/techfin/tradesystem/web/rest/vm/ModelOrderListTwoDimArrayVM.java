package ai.techfin.tradesystem.web.rest.vm;

import ai.techfin.tradesystem.domain.ModelOrder;
import ai.techfin.tradesystem.domain.Stock;
import ai.techfin.tradesystem.domain.enums.MarketType;
import ai.techfin.tradesystem.domain.enums.TradeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

public class ModelOrderListTwoDimArrayVM {

    @NotNull
    private String model;

    @NotNull
    private String product;

    @JsonProperty("sell_list")
    @NotNull
    private String[][] sellList;

    @JsonProperty("buy_list")
    @NotNull
    private String[][] buyList;

    @JsonIgnore
    private HashSet<ModelOrder> sellOrders = null;

    @JsonIgnore
    private HashSet<ModelOrder> buyOrders = null;

    @Override
    public String toString() {
        return "ModelOrderListTwoDimArrayVM{" +
            "model: " + model +
            "sell: " + Arrays.deepToString(sellList) +
            "buy: " + Arrays.deepToString(buyList) +
            "}";
    }

    private static HashSet<ModelOrder> toModelOrderArray(String[][] list, TradeType tradeType) {
        HashSet<ModelOrder> orders = new HashSet<>(list.length);
        for (String[] pair : list) {
            // format is "stock.market"
            int i = pair[0].indexOf('.');
            orders.add(new ModelOrder(
                new Stock(pair[0].substring(0, i), MarketType.valueOf(pair[0].substring(i + 1))),
                new BigDecimal(pair[1]), tradeType));
        }
        return orders;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public HashSet<ModelOrder> getSellOrders() {
        if (sellOrders != null) { return sellOrders; }
        return sellOrders = toModelOrderArray(sellList, TradeType.SELL);
    }

    public HashSet<ModelOrder> getBuyOrders() {
        if (buyOrders != null) { return buyOrders; }
        return buyOrders = toModelOrderArray(buyList, TradeType.BUY);
    }

}
