package com.ggl.cloud.transaction; 


import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/*
 *@Author Lori Shu
 *@Date 2022/4/20
 */
public class MyTransactionListener implements TransactionListener {
//    回调方法，消息预提交成功时调用
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        System.out.println(message);
        if(message.getKeys().equals("我有问题，不要提交我！")){
            return LocalTransactionState.UNKNOW;
        }
        if(message.getKeys().equals("当我不知道有没有成功吧！")){
            System.out.println(message.getKeys());
            return LocalTransactionState.UNKNOW;
        }

        return LocalTransactionState.COMMIT_MESSAGE;
    }
//回查方法，预提交结果为unknow时被调用,没有收到全局确认指令时调用
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        System.out.println("回查方法执行了！！！"+messageExt);
        return LocalTransactionState.COMMIT_MESSAGE;
    }

}
