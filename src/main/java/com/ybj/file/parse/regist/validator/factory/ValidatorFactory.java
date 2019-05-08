package com.ybj.file.parse.regist.validator.factory;

import com.ybj.file.parse.regist.ICanRegist;
import com.ybj.file.parse.regist.validator.Validator;

/**
 * 校验器的生成工厂，决定校验是否使用单例模式或者原型（不同的列或者多列需要不同的校验器对象）
 * 
 * @author yanbenjun
 *
 */
public interface ValidatorFactory extends ICanRegist {

    /**
     * 根据参数生成一个校验器
     * a. 如果校验器是列或者行无关的，则使用单例模式，eg. IntegerValidator
     * b.
     * 如果校验器是列相关的，不同的列应该有不同的校验器对象，校验器需要在初始化列模板的时候初始化，params表示列参数，如EnumValidator
     * c. 普通多列校验器，暂无
     * d. 多列相关的行校验器，如唯一键或者联合主键校验器，需要在初始化Sheet模板的时候，初始化行校验器（从数据库中获取已经存在的主键等）
     * 
     * @param params
     *            通过params可以初始化校验器
     * @return 初始化完毕的校验器对象
     */
    public Validator newValidator(String... params);
}
