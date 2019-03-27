package com.yanbenjun.file.test;

import com.yanbenjun.file.parse.regist.validator.datasource.ViewModelMapper;

public enum CityEnum implements ViewModelMapper
{
    SHENZHEN(1,"深圳"),
    GUANGZHOU(2,"广州"),
    DONGGUAN(3,"东莞");
    
    private Integer value;
    private String key;
    private CityEnum(Integer value, String key) {
        this.value = value;
        this.key = key;
    }
    
    @Override
    public Integer getValue(){
        return this.value;
    }
    
    @Override
    public String getKey(){
        return this.key;
    }
}
