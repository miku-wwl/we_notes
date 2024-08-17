package com.imooc.log.stack.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * <h1>定时任务生成 excel</h1>
 * */
@Slf4j
@Component
@SuppressWarnings("all")
public class ImoocerReportTask {

//    @Async("mvcTaskExecutor")
//    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void generateXSSFWorkbook() throws Exception {

        log.info("start to generate XSSFWorkbook");

        StopWatch sw = StopWatch.createStarted();

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Imoocer");

        int rowNumber = 10000 * 100;    // 行数
        int columnNumber = 8;   // 列数

        for (int i = 0; i < rowNumber; ++i) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < columnNumber; ++j) {
                row.createCell(j).setCellValue("Imoocer-Qinyi");
            }
        }

        sw.stop();
        log.info("generate XSSFWorkbook elapsed: [{}ms]",
                sw.getTime(TimeUnit.MILLISECONDS));
        wb.write(new FileOutputStream("/tmp/imoocer.xlsx"));
    }

    @Async("mvcTaskExecutor")
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void generateSXSSFWorkbook() throws Exception {

        log.info("start to generate SXSSFWorkbook");

        StopWatch sw = StopWatch.createStarted();

        int memoryRowNum = 200;
        SXSSFWorkbook wb = new SXSSFWorkbook(memoryRowNum);
        wb.setCompressTempFiles(true);

        Sheet sheet = wb.createSheet("Imoocer");

        int rowNumber = 10000 * 100;    // 行数
        int columnNumber = 8;   // 列数

        for (int i = 0; i < rowNumber; ++i) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < columnNumber; ++j) {
                row.createCell(j).setCellValue("Imoocer-Qinyi");
            }
        }

        sw.stop();
        log.info("generate SXSSFWorkbook elapsed: [{}ms]",
                sw.getTime(TimeUnit.MILLISECONDS));
        wb.write(new FileOutputStream("/tmp/imoocer.xlsx"));
    }
}
