package com.weilai.aviator.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.LambdaFunction;
import com.googlecode.aviator.runtime.type.AviatorLong;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorRuntimeJavaType;


import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UserDefinedFn3 {

    public static void main(String[] args) {
        AviatorEvaluator.addFunction(new FilterFunction());
        Object execute = AviatorEvaluator.execute("imoocFilter(seq.list(1,2,3,4,5,6,7,8,9), lambda(x) -> x % 2 == 0 end)");
        System.out.println(execute);

        Object execute2 = AviatorEvaluator.execute("imoocFilter(seq.set(1,2,3,4,5,6,7,8,9), lambda(x) -> x % 3 == 0 && x != 3 end)");
        System.out.println(execute2);
    }
}
class FilterFunction extends AbstractFunction {
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        //let list2 = filter(list, lambda(x) -> x % 2 == 0 end);
        //获取列表数据
        Collection<Object> collection = (Collection<Object>) arg1.getValue(env);
        //获取匿名lambda函数
        LambdaFunction function = (LambdaFunction) arg2;

        List<Object> resultList = List.of();
        for (Object value: collection) {
            if (function.call(env, AviatorLong.valueOf(value)).getValue(null) instanceof Boolean filter && filter) {
                resultList.add(value);
            }
        }
        return AviatorRuntimeJavaType.valueOf(resultList);
    }

    @Override
    public String getName() {
        return "imoocFilter";
    }
}
