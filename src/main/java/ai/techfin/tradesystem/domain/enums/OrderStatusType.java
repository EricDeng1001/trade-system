package ai.techfin.tradesystem.domain.enums;

public enum  OrderStatusType {

    /**初始化*/
    INIT,
    /**全部成交*/
    ALLTRADED,
    /**部分成交*/
    PARTTRADEDQUEUEING,
    /**部分撤单*/
    PARTTRADEDNOTQUEUEING,
    /**未成交*/
    NOTRADEQUEUEING,
    /**已撤单*/
    CANCELED,
    /**已拒绝*/
    REJECTED,
    /**未知订单状态*/
    UNKNOWN
}
