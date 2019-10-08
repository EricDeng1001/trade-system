package ai.techfin.xtpms.utils;

import com.zts.xtp.trade.model.request.OrderInsertRequest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class UserBlockingQueue {

    //此字段用于判断当前用户是否有堆积请求，如果有，入队列，如果没有，执行队列出列操作
    private volatile boolean work;

    private BlockingQueue<OrderInsertRequest> userRequestQueue;

    public UserBlockingQueue(boolean work, BlockingQueue<OrderInsertRequest> userRequestQueue){
        this.work = work;
        this.userRequestQueue = new LinkedBlockingQueue<>();
    }

    public boolean isWork() {
        return work;
    }

    public void setWork(boolean work) {
        this.work = work;
    }

    public BlockingQueue<OrderInsertRequest> getUserRequestQueue() {
        return userRequestQueue;
    }

    public void setUserRequestQueue(BlockingQueue<OrderInsertRequest> userRequestQueue) {
        this.userRequestQueue = userRequestQueue;
    }
}
