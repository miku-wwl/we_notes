package com.imooc.log.stack.chapter7;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <h1>CPU 使用异常 ---> CPU 使用率过高</h1>
 * 找出 CPU 使用率最高的那个线程
 * */
public class AbnormalCpu {

    private static List<String> removeIntersection(List<String> preDates,
                                                   Map<String, Date> preDefine) {

        Iterator<String> iterator = preDates.iterator();

        while (iterator.hasNext()) {

            String value = iterator.next();

            for (String define : preDefine.keySet()) {
                if (define.equals(/* iterator.next()*/ value)) {
                    iterator.remove();
                }
            }
        }

        return preDates;
    }

    public static void main(String[] args) {

        List<String> preDates = new ArrayList<>();
        preDates.add("2021.01.01");
        preDates.add("2021.01.02");
        preDates.add("2021.01.03");

        Map<String, Date> preDefine = new HashMap<>();
        preDefine.put("2021.01.01", new Date());
        preDefine.put("2021.01.02", new Date());

//        System.out.println(removeIntersection(preDates, new HashMap<>()));
        System.out.println(removeIntersection(preDates, preDefine));
    }
}
