package com.yanbenjun.file.parse.regist.validator.factory;

import java.util.ArrayList;
import java.util.List;

import com.yanbenjun.file.parse.regist.validator.SingleCellValidator;

public interface SingleCellValidatorFactory
{
    public static List<SingleCellValidator> getSingleCellValidators(String validatorstrs) {
        String[] vsarr = validatorstrs.split("\\|");
        List<SingleCellValidator> validators = new ArrayList<>();
        for (String type : vsarr) {
            validators.add((SingleCellValidator)ValidatorFactory.build(type).newValidator());
        }
        return validators;
    }
}
