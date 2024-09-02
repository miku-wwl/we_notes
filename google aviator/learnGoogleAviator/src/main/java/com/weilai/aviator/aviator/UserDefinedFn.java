package com.weilai.aviator.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

public class UserDefinedFn {

    public static void main(String[] args) {
        //第一步写我们的类，继承AbstractFunction
        //第二步就是来注册我们的类
        AviatorEvaluator.addFunction(new AddFunction());
        Object execute = AviatorEvaluator.execute("imoocAdd(1,2)");
        System.out.println(execute);
        Object execute2 = AviatorEvaluator.execute("imoocAdd(imoocAdd(1,2), 3)");
        System.out.println(execute2);
    }
}

class AddFunction extends AbstractFunction {
    @Override
    public String getName() {
        return "imoocAdd";
    }
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        Number n1 = FunctionUtils.getNumberValue(arg1, env);
        Number n2 = FunctionUtils.getNumberValue(arg2, env);
        return new AviatorDouble(n1.doubleValue() + n2.doubleValue());
    }
}
