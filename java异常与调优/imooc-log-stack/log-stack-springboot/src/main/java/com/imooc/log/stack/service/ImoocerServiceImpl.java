package com.imooc.log.stack.service;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.log.stack.vo.Imoocer;
import com.imooc.log.stack.vo.RedBlueCount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * <h1>Imoocer 相关功能实现类</h1>
 * */
@Slf4j
@Service
public class ImoocerServiceImpl {

    /** Imoocer 在 Redis 中的 key */
    private final static String IMOOCER_LIST_KEY = "SpringBoot:Imoocer:List";

    private final ObjectMapper mapper;
    private final StringRedisTemplate redisTemplate;

    public ImoocerServiceImpl(ObjectMapper mapper, StringRedisTemplate redisTemplate) {
        this.mapper = mapper;
        this.redisTemplate = redisTemplate;
    }

    /**
     * <h2>创建一个 Imoocer</h2>
     * */
    public Long createImoocer(Imoocer imoocer) throws JsonProcessingException {

        Long size = redisTemplate.opsForList().size(IMOOCER_LIST_KEY);
        if (null == size) {
            size = 0L;
        }

        imoocer.setId(size + 1);
        imoocer.setProfile("fangao.jpg");
        Long result = redisTemplate.opsForList().leftPush(IMOOCER_LIST_KEY, mapper.writeValueAsString(imoocer));
        log.info("left push imoocer to list: [{}]", result);

        return imoocer.getId();
    }

    /**
     * <h2>查看 Imoocer 的信息</h2>
     * */
    public List<Imoocer> imoocerInfo() throws IOException {

        List<String> imoocersCache = redisTemplate.opsForList().range(IMOOCER_LIST_KEY, 0, -1);
        if (CollectionUtil.isEmpty(imoocersCache)) {
            return Collections.emptyList();
        }

        List<Imoocer> imoocers = new ArrayList<>(imoocersCache.size());
        for (int i = 0; i != imoocersCache.size(); ++i) {
            imoocers.add(mapper.readValue(imoocersCache.get(i), Imoocer.class));
        }

        return imoocers;
    }

    /**
     * <h2>从 Redis 中获取 Imoocer, 并审核头像(计算红蓝像素点的个数 * 10000)</h2>
     * */
    public Callable<Integer> callableAudit() throws IOException {

        List<String> imoocersCache = redisTemplate.opsForList().range(
                IMOOCER_LIST_KEY, 0, -1
        );
        if (CollectionUtil.isEmpty(imoocersCache)) {
            imoocersCache = Collections.singletonList(
                    mapper.writeValueAsString(Imoocer.defaultObj())
            );
        }

        List<Imoocer> imoocers = new ArrayList<>(imoocersCache.size() * 10);
        for (int i = 0; i != 10; ++i) {
            int index = imoocersCache.size() > i ? i : imoocersCache.size() - 1;
            imoocers.add(mapper.readValue(imoocersCache.get(index), Imoocer.class));
        }

        return () -> {

            List<CompletableFuture<RedBlueCount>> futureList =
                    new ArrayList<>(imoocers.size());
            // 并发计算红蓝像素点的个数
            imoocers.forEach(icer -> futureList.add(
                    CompletableFuture.supplyAsync(() -> computePic(icer.getProfile()))
            ));
            List<RedBlueCount> result = futureList.stream().map(CompletableFuture::join)
                    .collect(Collectors.toList());
            log.info("audit imoocer count: [{}]", result.size());
            return result.size();
        };
    }


    /**
     * <h2>计算给定图片红蓝像素点的个数</h2>
     * */
    private RedBlueCount computePic(String filename) {

        log.info("coming in compute pic: [{}]", filename);

        BufferedImage image;

        try {
            File file = ResourceUtils.getFile(String.format("classpath:%s", filename));
            image = ImageIO.read(file);
        } catch (IOException ex) {
            log.error("process error: [{}]", filename, ex);
            return new RedBlueCount(0, 0);
        }

        int width = image.getWidth();
        int height = image.getHeight();
        int minx = image.getMinX();
        int miny = image.getMinY();

        int[] rgb = new int[3];
        int red = 0;
        int blue = 0;

        // 全图扫描 * 10000
        for (int x = 0; x != 10000; ++x) {
            for (int i = minx; i < width; i++) {
                for (int j = miny; j < height; j++) {

                    int pixel = image.getRGB(i, j);

                    // 下面三行代码将一个数字转换为 RGB 数字
                    rgb[0] = (pixel & 0xff0000) >> 16;
                    rgb[1] = (pixel & 0xff00) >> 8;
                    rgb[2] = (pixel & 0xff);

                    if (rgb[0] > 250 && rgb[0] < 255 && rgb[1] < 100 && rgb[2] < 100) {
                        red += 1;
                    }

                    if (rgb[2] > 250 && rgb[2] < 255 && rgb[0] < 100 && rgb[1] < 100) {
                        blue += 1;
                    }
                }
            }
        }

        log.info("pic: [{}], [red count={}], [blue count={}]", filename, red, blue);
        return new RedBlueCount(red, blue);
    }
}
