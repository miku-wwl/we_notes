package com.imooc.log.stack.chapter6;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>广义的调试</h1>
 * */
@Slf4j
public class CodeDebug {

    private int sum(int x, int y) {
        System.out.println("x, y: " + x + ", " + y);
        return x + y;
    }

    private String joint(String x, String y) {

        assert null != x;
        assert null != y;

        return x + y;
    }

    private int sum2(int x, int y) {

        log.debug("args: [{}], [{}]", x, y);
        return x + y;
    }
}
