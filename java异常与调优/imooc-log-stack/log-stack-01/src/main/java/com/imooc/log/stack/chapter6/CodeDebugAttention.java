package com.imooc.log.stack.chapter6;

/**
 * <h1>代码调试要注意的点</h1>
 * */
@SuppressWarnings("all")
public class CodeDebugAttention {

    public static void main(String[] args) {

        // -------------------------------------------------------------------------------------------------------------
        // 1. 不要把复杂的逻辑写在一行代码中，分开去写
        String x = "qinyi";
        String y = "imooc";
        String z = "imooc-qinyi";

        // 这就是分开去写多个判断条件, 而不是把 three 写在一行里面
        boolean one = x.startsWith("qinyi") && y.startsWith("imooc");
        boolean two = x.endsWith("yi") && y.endsWith("ooc");
        boolean three = one && two && z.equalsIgnoreCase("imooc-qinyi");

        boolean result = one && two && three;
        // -------------------------------------------------------------------------------------------------------------
    }
}
