package com.weilai.aviator.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

import java.io.IOException;

public class FnExample {

    public static void main(String[] args) throws IOException {
        //系统函数
        //断言
        p("assert(1<2, '这个断言不能通过')");
        //随机数 [0,5)
        p("p(rand(5))");
        //比较大小，cmp(x,y) , x<y 就会返回-1，x=y返回0，x>y返回1
        p("p(cmp(3,2))");
        //强制转换，所有的整数都会转换成 long，所有的浮点数都会转成 double
        p("p(long('3'))");
        p("p(double('3.1'))");
        p("p(type(3.1))");
        //is_a, 'str'是String类型的一个实例
        p("p(is_a('str',String))");
        p("p(is_a(1.1,Double))");
        p("p(max(23,11,19,31,26))");

        System.out.println();
        //字符串函数
        //判断x是否包含字符串y
        p("p(string.contains('abc','bcd'))");
        //获取字符串长度
        p("p(string.length('abc'))");
        //字符串截取 [a,b), 从下标0开始
        p("p(string.substring('abcdef',1,3))");
        //找不到就返回-1
        p("p(string.indexOf('abc','bcd'))");

        //数学函数
        //求绝对值
        p("p(math.abs(-1))");
        //四舍五入
        p("p(math.round(3.5))");

        //集合处理函数
        //创建一个指定类型的数组
        p("p(seq.array(long,2,5,4,3,6)[1])");
        //创建一个arrayList
        p("p(type(seq.list(2,5,4,3,6)))");
        //给arrayList动态添加
        p("let a=seq.list(2,5,4,3,6); seq.add(a, 10); p(a)");
        //创建一个set
        p("p(type(seq.set(2,5,4,3,6)))");
        //创建一个hashmap
        p("p(seq.map('k1','v1','k2','v2'))");
        //返回对应hashmap的key, values
        p("let a = seq.map('k1','v1','k2','v2'); p(seq.keys(a)); p(seq.vals(a))");
        //map函数，它可以应用于集合中每一个元素，然后返回一个新的集合
        p("let list = seq.list(1,2,3,4,5); let list2 = map(list, lambda(x) -> x*2 end); p(list2)");
        //filter函数
        p("let list = seq.list(1,2,3,4,5); let list2 = filter(list, lambda(x) -> x % 2 == 0 end); p(list2)");
        //count
        p("p(count(seq.set(2,5,4,3,6)))");

        Expression expression = AviatorEvaluator.getInstance().compileScript("D:\\imooc\\project\\data-center\\example\\src\\main\\java\\com\\imooc\\aviator\\fn.av");
        expression.execute();
    }

    public static void p(String expressionStr) {
        Expression expression = AviatorEvaluator.compile(expressionStr);
        expression.execute();
    }
}
