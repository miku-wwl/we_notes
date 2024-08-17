package com.imooc.log.stack.chapter3;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * <h1>其他需要遵守的一些规约</h1>
 * */
public class OtherPrinciples {

    /**
     * <h2>捕获有必要的代码段, 不要大段的使用</h2>
     * */
    public static long suitableTryCatch(String start, String end) {

        LocalDate startDay;
        LocalDate endDay;

        try {
            startDay = LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    .parse(start));
            endDay = LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    .parse(end));
        } catch (DateTimeException ex) {
            ex.printStackTrace();
            return -1;
        }

        return startDay.until(endDay, ChronoUnit.DAYS);
    }

    /**
     * <h2>尽量不要捕获 Exception, 而是捕获更加具体的异常</h2>
     * */
    public static void classifyException(String fileName) {

        try {
            FileInputStream file = new FileInputStream(fileName);
            int x = (byte) file.read();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * <h2>异常处理后要及时的清理, 释放资源</h2>
     * */
    public static void closeResource(String fileName) {

        FileInputStream file = null;

        try {
            file = new FileInputStream(fileName);
            int x = (byte) file.read();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (null != file) {
                    file.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * <h2>尽早的让异常暴露出来</h2>
     * */
    public static void earlyException(String input) {

        // 详细的判断
        if (null != input && !"".equals(input) && input.contains("qinyi")) {
            // ....
        } else {
            throw new IllegalArgumentException("error input: " + input);
        }

        // do something
    }
}
