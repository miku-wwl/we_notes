package com.weilai.aviator.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorLong;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Collection;
import java.util.Map;

public class UserDefinedFn2 {

    public static void main(String[] args) {
        //seq.list(1,2,3,4,5)  =  15
        AviatorEvaluator.addFunction(new SumFunction());
        Object execute = AviatorEvaluator.execute("imoocSum(seq.list(1,2,3,4,5))");
        System.out.println(execute);

        Object execute2 = AviatorEvaluator.execute("imoocSum(seq.set(1,4,5))");
        System.out.println(execute2);
    }
}
class SumFunction extends AbstractFunction {
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
        Collection<Object> collection = (Collection<Object>) arg1.getValue(env);
        long sum = 0;
        for (Object value: collection) {
            sum += (long)value;
        }
        return AviatorLong.valueOf(sum);
    }

    @Override
    public String getName() {
        return "imoocSum";
    }
}
