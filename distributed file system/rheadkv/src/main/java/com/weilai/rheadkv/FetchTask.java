package com.weilai.rheadkv;

import com.alipay.sofa.jraft.util.Bits;
import com.alipay.sofa.jraft.util.BytesUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

/**
 * 继承TimerTask类，从而实现定时功能，任务在run方法中执行
 * 只有主节点才执行抓取任务
 */
@Slf4j
@RequiredArgsConstructor
public class FetchTask extends TimerTask {

    @NonNull
    private NodeConfig nodeConfig;

    @Override
    public void run() {
        //只有主节点获取数据
        if (!nodeConfig.getNode().isLeader()) {
            return;
        }

        long packNo = getPackNoFromStore();

        List<String> strs = Arrays.asList("Str1", "str2", "str3", "str4", String.valueOf(packNo));
        StrsPack strsPack = new StrsPack(packNo, strs);
        log.info(strsPack.toString());

        try {
            //生成PackNo并打包成Cmd
            //入库,将类使用Hessian2转换为字节流并存到KVStore里
            byte[] serialize = nodeConfig.getBodyCodec().serialize(strsPack);
            insertToKVStore(packNo, serialize);

            //测试KV read
            byte[] deserialize = getKVStore(packNo);
            StrsPack strsPackFromKV = nodeConfig.getBodyCodec().deserialize(deserialize, StrsPack.class);
            log.info(strsPackFromKV.toString());

            //更新PackNo++
            updatePacketNoInStore(packNo + 1);

        } catch (Exception e) {
            log.error("encode cmd error ", e);
        }
    }


    //将key写为byte数组的形式
    private static final byte[] PACKET_NO_KEY = BytesUtil.writeUtf8("seq_pqcket_no");

    /**
     * 从KVStore里取PacketNo,如果空的话则返回0
     *
     * @return 查询到的PacketNo
     */
    private long getPackNoFromStore() {
        log.info("[getPackNoFromStore] start:");
        //得到对应的packetNo的字节数组
        final byte[] bPacketNo = nodeConfig.getNode().getRheaKVStore().bGet(PACKET_NO_KEY);

        long packetNo = 0;
        if (ArrayUtils.isNotEmpty(bPacketNo)) {
            packetNo = Bits.getLong(bPacketNo, 0);
        }
        log.info("[getPackNoFromStore] packetNo:{}.", packetNo);
        return packetNo;
    }

    /**
     * 将CmdPack的字节数组保存到KVStore中
     *
     * @param packNo    包序号
     * @param serialize 包类通过序列化得到的字节数组
     */
    private void insertToKVStore(long packNo, byte[] serialize) {
        log.info("[insertToKVStore] start: packNo ={}.", packNo);
        byte[] key = new byte[8];
        Bits.putLong(key, 0, packNo);    //将long型的包名转换为8位长的字节数组
        nodeConfig.getNode().getRheaKVStore().put(key, serialize).join();   //put是异步操作，提高性能
    }

    private byte[] getKVStore(long packNo) {
        log.info("[getKVStore] start: packNo ={}.", packNo);
        byte[] key = new byte[8];
        Bits.putLong(key, 0, packNo);    //将long型的包名转换为8位长的字节数组
        return nodeConfig.getNode().getRheaKVStore().get(key).join(); // 阻塞等待结果
    }

    /**
     * 更新PacketNo
     *
     * @param newPackNo 要更新的包序号
     */
    private void updatePacketNoInStore(long newPackNo) {
        log.info("[updatePacketNoInStore] start: newPackNo={}.", newPackNo);
        final byte[] bytes = new byte[8];
        Bits.putLong(bytes, 0, newPackNo);
        nodeConfig.getNode().getRheaKVStore().put(PACKET_NO_KEY, bytes);
    }

}
