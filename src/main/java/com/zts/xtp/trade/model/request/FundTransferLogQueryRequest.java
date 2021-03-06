// Generated by delombok at Sun Sep 22 21:37:45 CST 2019
package com.zts.xtp.trade.model.request;

public class FundTransferLogQueryRequest {
    /**
     * 资金内转编号
     */
    private String serialId;


    @SuppressWarnings("all")
    public static class FundTransferLogQueryRequestBuilder {
        @SuppressWarnings("all")
        private String serialId;

        @SuppressWarnings("all")
        FundTransferLogQueryRequestBuilder() {
        }

        @SuppressWarnings("all")
        public FundTransferLogQueryRequestBuilder serialId(final String serialId) {
            this.serialId = serialId;
            return this;
        }

        @SuppressWarnings("all")
        public FundTransferLogQueryRequest build() {
            return new FundTransferLogQueryRequest(serialId);
        }

        @Override
        @SuppressWarnings("all")
        public String toString() {
            return "FundTransferLogQueryRequest.FundTransferLogQueryRequestBuilder(serialId=" + this.serialId + ")";
        }
    }

    @SuppressWarnings("all")
    public static FundTransferLogQueryRequestBuilder builder() {
        return new FundTransferLogQueryRequestBuilder();
    }

    /**
     * 资金内转编号
     */
    @SuppressWarnings("all")
    public String getSerialId() {
        return this.serialId;
    }

    /**
     * 资金内转编号
     */
    @SuppressWarnings("all")
    public void setSerialId(final String serialId) {
        this.serialId = serialId;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "FundTransferLogQueryRequest(serialId=" + this.getSerialId() + ")";
    }

    @SuppressWarnings("all")
    public FundTransferLogQueryRequest() {
    }

    @SuppressWarnings("all")
    public FundTransferLogQueryRequest(final String serialId) {
        this.serialId = serialId;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FundTransferLogQueryRequest)) return false;
        final FundTransferLogQueryRequest other = (FundTransferLogQueryRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$serialId = this.getSerialId();
        final Object other$serialId = other.getSerialId();
        if (this$serialId == null ? other$serialId != null : !this$serialId.equals(other$serialId)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof FundTransferLogQueryRequest;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $serialId = this.getSerialId();
        result = result * PRIME + ($serialId == null ? 43 : $serialId.hashCode());
        return result;
    }
}
