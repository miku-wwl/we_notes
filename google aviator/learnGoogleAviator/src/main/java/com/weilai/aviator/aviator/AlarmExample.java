package com.weilai.aviator.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AlarmExample {

    public static void main(String[] args) {
        /**
         * 定义告警规则表达式，如果处于高温并且处于低湿度，50%概率是故障的
         * 工作人员输入一个表达式， [(temperature * 系数1 + humidity * 系数2) / (humidity * 系数3)正弦函数的绝对值] < 某一个值
         */
        AviatorEvaluator.addFunction(new IsHighTemperatureFunction());
        AviatorEvaluator.addFunction(new IsLowHumidityFunction());

        String subExpression = "isHighTemperature(temperature) && isLowHumidity(humidity)";

        //输入的表达式： ((temperature * a + humidity * b) / math.abs(math.sin(humidity * c))) < d
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            String newInput = scanner.nextLine();
            if (newInput.isEmpty()) {
                break;
            }
            sb.append(newInput);
        }
        sb.append(" && ").append(subExpression);
        String expression = sb.toString();
        System.out.println("执行的表达式: "+ expression);

        //设置Aviator表达式环境变量
        Map<String, Object> data = new HashMap<>();
        data.put("temperature", 28);
        data.put("humidity", 60);
        data.put("a", 0.3);
        data.put("b", 0.5);
        data.put("c", 0.1);
        data.put("d", 1000);

        Boolean isAlarm = (Boolean) AviatorEvaluator.execute(expression, data);
        if (Boolean.TRUE.equals(isAlarm)) {
            System.out.println("参数异常，生成告警!");
        } else {
            System.out.println("一切正常...");
        }
    }
}
class IsHighTemperatureFunction extends AbstractFunction {
    private static final double normalTemperature = 25;
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
        double temperature = FunctionUtils.getNumberValue(arg1, env).doubleValue();
        return AviatorBoolean.valueOf(temperature > normalTemperature);
    }

    @Override
    public String getName() {
        return "isHighTemperature";
    }
}
class IsLowHumidityFunction extends AbstractFunction {
    private static final double normalHumidity = 70;
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
        double humidity = FunctionUtils.getNumberValue(arg1, env).doubleValue();
        return AviatorBoolean.valueOf(humidity < normalHumidity);
    }

    @Override
    public String getName() {
        return "isLowHumidity";
    }
}
