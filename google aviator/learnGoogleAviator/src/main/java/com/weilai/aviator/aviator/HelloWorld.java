package com.weilai.aviator.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

import java.io.IOException;

public class HelloWorld {

    public static void main(String[] args) throws IOException {
        //脚本执行
        Expression expression = AviatorEvaluator.getInstance().compileScript("C:\\workspace\\learnGoogleAviator\\src\\main\\java\\com\\weilai\\aviator\\aviator\\hello.av");
        expression.execute();

        //字符串表达式
        Expression expression2 = AviatorEvaluator.getInstance().compile("println(\"hello world aviator\");");
        expression2.execute();

        Expression expression3 = AviatorEvaluator.compile("println(\"hello world aviator\");");
        expression3.execute();

        //算术操作符 + - * /
        p("p(1+3)");
        p("p(1-3)");
        p("p(3*3)");
        p("p(6/3)");
        p("p((2 + 3) * 4)");
        p("p((10 - 5) / 2)");

        //整数都会转成 long 类型， 浮点类型都会转成 double
        p("p(type(3))"); //long
        p("p(type(3.1))"); //double
        p("p(type('hello world'))"); //string

        //比较操作符
        p("p(2==2)"); // true
        p("p(2!=2)"); // false
        p("p(3>2)"); // true
        p("p(3<2)"); //false

        //逻辑操作符
        p("p(true && false)"); // false
        p("p(true || false)"); // true
        p("p(!(3>2))"); //false

        //位运算符
        p("p(12 & 6)"); // 逻辑与， 12：1100 ，6：0110，-》 0100 = 4
        p("p(12 | 6)"); // 逻辑或， 12：1100 ，6：0110，-》 1110 = 14
        p("p(12 ^ 6)"); // 异或， 12：1100 ，6：0110，-》 1010 = 10
        p("p(8 << 2)"); // 左移，1000 -》 100000 = 32
        p("p(8 >> 2)"); // 左移，1000 -》 10 = 2

        //if 表达式
        Expression expression1 = AviatorEvaluator.getInstance().compileScript("C:\\workspace\\learnGoogleAviator\\src\\main\\java\\com\\weilai\\aviator\\aviator\\if.av");
        expression1.execute();

        //while 表达式
        Expression expression4 = AviatorEvaluator.getInstance().compileScript("C:\\workspace\\learnGoogleAviator\\src\\main\\java\\com\\weilai\\aviator\\aviator\\while.av");
        expression4.execute();

        Expression expression5 = AviatorEvaluator.getInstance().compileScript("C:\\workspace\\learnGoogleAviator\\src\\main\\java\\com\\weilai\\aviator\\aviator\\for.av");
        expression5.execute();
        p("assert(1<2,'1不能大于2')");
    }

    public static void p(String expressionStr) {
        Expression expression = AviatorEvaluator.compile(expressionStr);
        expression.execute();
    }
}
