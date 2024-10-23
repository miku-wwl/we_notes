package com.weilai.rheadkv;

import com.alipay.remoting.exception.CodecException;
import com.alipay.sofa.jraft.rhea.client.DefaultRheaKVStore;
import com.alipay.sofa.jraft.rhea.client.RheaKVStore;
import com.alipay.sofa.jraft.rhea.options.PlacementDriverOptions;
import com.alipay.sofa.jraft.rhea.options.RegionRouteTableOptions;
import com.alipay.sofa.jraft.rhea.options.RheaKVStoreOptions;
import com.alipay.sofa.jraft.rhea.options.configured.MultiRegionRouteTableOptionsConfigured;
import com.alipay.sofa.jraft.rhea.options.configured.PlacementDriverOptionsConfigured;
import com.alipay.sofa.jraft.rhea.options.configured.RheaKVStoreOptionsConfigured;
import com.alipay.sofa.jraft.rhea.storage.KVEntry;
import com.alipay.sofa.jraft.util.Bits;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
public class ReadStartup {
    private static RheaKVStore rheaKVStore = new DefaultRheaKVStore();
    private static BodyCodec bodyCodec = new BodyCodec();
    private static int packNo = -1;

    public static void main(String[] args) throws CodecException, InterruptedException {
        String seqUrlList = "127.0.0.1:8891,127.0.0.1:8892,127.0.0.1:8893";
        final List<RegionRouteTableOptions> regionRouteTableOptions = MultiRegionRouteTableOptionsConfigured.newConfigured()
                .withInitialServerList(-1L, seqUrlList)
                .config();
        final PlacementDriverOptions pdOptions = PlacementDriverOptionsConfigured.newConfigured()
                .withFake(true)
                .withRegionRouteTableOptionsList(regionRouteTableOptions)
                .config();
        final RheaKVStoreOptions options = RheaKVStoreOptionsConfigured.newConfigured()
                .withPlacementDriverOptions(pdOptions)
                .config();
        rheaKVStore.init(options);

        while (true) {
            Thread.sleep(100);
            //主动抓取数据
            byte[] firstKey = new byte[8];
            Bits.putLong(firstKey, 0, packNo + 1);   //(firstKey included)
            byte[] lastKey = new byte[8];
            Bits.putLong(lastKey, 0, packNo + 2);     //(lastKey excluded)

            final List<KVEntry> kvEntries = rheaKVStore.bScan(firstKey, lastKey);
            log.info("kvEntries {}", kvEntries);

            if (!CollectionUtils.isEmpty(kvEntries)) {
                List<StrsPack> collect = Lists.newArrayList();
                for (KVEntry entry : kvEntries) {
                    byte[] value = entry.getValue();
                    if (ArrayUtils.isNotEmpty(value)) {
                        StrsPack strsPack = bodyCodec.deserialize(value, StrsPack.class);
                        log.info("strsPack = {}", strsPack.toString());
                        collect.add(strsPack);
                    }
                }
            }
            packNo++;
        }
    }
}