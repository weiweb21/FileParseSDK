package com.ybj.file.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

public class ValidatorExpressionUtils {

    public static String[] extractParams(String expression, String validatorType) {
        if (expression.trim().startsWith(validatorType)) {
            String paramstr = expression.substring(validatorType.length(), expression.length()).trim();
            // 去掉括号
            if (paramstr.startsWith("(") && paramstr.endsWith(")")) {
                paramstr = paramstr.substring(1, paramstr.length() - 1);
                String[] paramsarr = paramstr.split(",");
                String[] result = new String[paramsarr.length];
                return Stream.of(paramsarr).map(item -> {
                    return item.trim();
                }).collect(Collectors.toList()).toArray(result);
            }
        }
        throw new RuntimeException(validatorType + "的表达式错误：" + expression);
    }

    public static String[] parseValidatorExpression(String expression) {
        if (StringUtils.isBlank(expression)) {
            throw new RuntimeException("表达式错误：" + expression);
        }
        expression = expression.trim();
        int firstParamEndIndex = expression.indexOf("(");
        if (firstParamEndIndex == -1) {
            return new String[] { expression.trim() };
        }
        
        String validatorType = expression.substring(0, firstParamEndIndex);
        String[] validatorParams = expression.substring(firstParamEndIndex + 1, expression.length() - 1).split(",");
        
        List<String> list = new ArrayList<>();
        list.add(validatorType);
        list.addAll(Arrays.asList(validatorParams));
        list = list.stream().map(item -> {
            return (String) item.trim();
        }).collect(Collectors.toList());
        String[] result = new String[list.size()];
        return list.toArray(result);
    }

    public static void main(String[] args) {
        String[] params = parseValidatorExpression("notnull");
        String[] params1 = parseValidatorExpression("enum ( query, queryRoomNameIdMap)");
        String[] params2 = parseValidatorExpression("unique (beanName, mei, name)       ");
        String[] params3 = parseValidatorExpression("integer");
        String[] params4 = parseValidatorExpression("integerfdsakl(0fdaf9)fdaf0fa(0");
        System.out.println(1);
    }
}
