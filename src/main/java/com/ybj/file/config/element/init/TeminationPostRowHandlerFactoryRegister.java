package com.ybj.file.config.element.init;

import java.util.ArrayList;
import java.util.List;


/**
 * TeminationPostRowHandlerFactory的注册类
 * @author Administrator
 *
 */
public class TeminationPostRowHandlerFactoryRegister
{
    private static List<TeminationPostRowHandlerFactory> handlerFactorys = new ArrayList<>();
    
    static {
        TeminationPostRowHandlerFactoryRegister.regist(new ClassTeminationPostRowHandlerFactory());
    }
    
    public static void regist(TeminationPostRowHandlerFactory handlerFactory) {
        handlerFactorys.add(handlerFactory);
    }
    
    public static List<TeminationPostRowHandlerFactory> getAllRegistedHandlerFactorys() {
        return handlerFactorys;
    }
}
