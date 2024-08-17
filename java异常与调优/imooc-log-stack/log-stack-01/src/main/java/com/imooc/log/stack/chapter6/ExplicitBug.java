package com.imooc.log.stack.chapter6;

import lombok.Data;

/**
 * <h1>学会解决显性问题</h1>
 * */
public class ExplicitBug {

    /**
     * <h2>通用对象</h2>
     * */
    @Data
    private static class GeneralObject {

        private String name;
        private int age;
        private String gender;
    }

    // 不要修复一个 Bug, 又引入了另一个 Bug
    // 可以看到, 有多个地方使用到了 GeneralObject, 所以, 如果需要修改, 会影响到很多地方, 两种方式
    // 1. 仍旧修改 GeneralObject, 但是只增不减属性, 且给出新增加的默认值, 以及合适的构造函数
    // 2. 新写一个对象去修改业务逻辑

    private GeneralObject process01(GeneralObject object) {
        return null;
    }

    private GeneralObject process02(GeneralObject object) {
        return null;
    }

    private GeneralObject process03(GeneralObject object) {
        return null;
    }
}
