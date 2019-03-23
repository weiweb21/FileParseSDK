package com.yanbenjun.file.parse.regist.validator.factory;

import java.util.ArrayList;
import java.util.List;

import com.yanbenjun.file.parse.regist.type.SingleCellValidator;

public interface SingleCellValidatorFactory {

    public SingleCellValidator newSingleCellValidator(String vstr);

    public static SingleCellValidatorFactory build(String vstr) {
        if (vstr.startsWith("enum")) {
            return new EnumCellValidatorFactory();
        } else {
            return new SimpleCellValidatorFactory();
        }
    }

    public static List<SingleCellValidator> getSingleCellValidators(String validatorstrs) {
        String[] vsarr = validatorstrs.split("\\|");
        List<SingleCellValidator> validators = new ArrayList<>();
        for (String type : vsarr) {
            validators.add(SingleCellValidatorFactory.build(type).newSingleCellValidator(type));
        }
        return validators;
    }
}
