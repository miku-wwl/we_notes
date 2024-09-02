package com.weilai.rocketmq.mq;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@RocketMQTransactionListener
@Component
public class MyTransactionListener implements RocketMQLocalTransactionListener {
    /**
     * 第一种情况，saveFlag = true, timeout = true，模拟数据插入成功，但是超时，broker就会回查
     * 事务，也就是调用 checkLocalTransaction。最终情况是 本地事务执行成功，也就是消费者接收消息成功
     */
    /**
     * 第二种情况，saveFlag = false, timeout = true，模拟数据插入失败，但是超时，broker就会回查
     * 事务，也就是调用 checkLocalTransaction。最终情况是 本地事务执行失败，也就是消费者不能接收消息
     */
    /**
     * 第三种情况，saveFlag = false, timeout = false，模拟数据插入失败，不超时，
     * 最终情况是 本地事务执行失败，也就是消费者不能接收消息
     */
    /**
     * 第四种情况，saveFlag = true, timeout = false，模拟数据插入成功，不超时，
     * 最终情况是 本地事务执行成功，也就是消费者能接收消息
     */
    private boolean saveFlag = true;
    private boolean timeout = false;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        //用来执行本地事务

        //比如说这里做一个数据插入，插入到mysql
        try {
            save();
            if (timeout) {
                //这里模拟的是超时，broker就不知道生产者本地事务是否执行成功，这个时候broker就会来回调checkLocalTransaction
                return RocketMQLocalTransactionState.UNKNOWN;
            }
            if (!saveFlag) {
                //模拟插入失败，报错
                int a = 1 / 0;
            }
            //表示我们本地事务执行成功（数据插入成功），broker就会将半消息转成普通消息，这样消费者就可以收到消息
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            //数据插入失败，本地事务执行失败，broker丢弃消息
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 做数据库插入
     */
    private void save() {
        System.out.println("transaction事务插入" + (saveFlag ? "成功" : "失败"));
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        //用来给Broker回查本地事务，如果本地事务执行成功，那么半消息会转成普通消息，即消息发送成功
        //如果本地事务执行失败，那么半消息就会被丢弃
        boolean exists = existsOne();
        if (exists) {
            //二次回查，告诉broker，本地事务确实执行成功，broker就会将半消息转成普通消息，消息也发送成功
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            //本地事务执行失败，告诉broker丢弃半消息
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 查询数据库是否存在当前这条记录
     *
     * @return
     */
    private boolean existsOne() {
        System.out.println("transaction事务回查" + (saveFlag ? "存在" : "不存在"));
        return saveFlag;
    }
}

