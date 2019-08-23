package ai.techfin.tradesystem.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class ModelOrderListTwoDimArrayVM {

    private String model;

    private String product;

    private Data data;

    public ModelOrderListTwoDimArrayVM() {}

    @Override
    public String toString() {
        return "ModelOrderListTwoDimArrayVM{" +
            "model: " + model +
            "orders: " + data +
            "}";
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    private static class Data {

        @JsonProperty("sell_list")
        private String[][] sellList;

        @JsonProperty("buy_list")
        private String[][] buyList;

        public String[][] getSellList() { return sellList; }

        public void setSellList(String[][] sellList) { this.sellList = sellList; }

        public String[][] getBuyList() { return buyList; }

        public void setBuyList(String[][] buyList) { this.buyList = buyList; }

        @Override
        public String toString() {
            return "{" +
                Arrays.deepToString(sellList) +
                Arrays.deepToString(buyList) +
                "}";
        }

    }

}
