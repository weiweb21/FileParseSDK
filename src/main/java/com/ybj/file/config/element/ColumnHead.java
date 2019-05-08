package com.ybj.file.config.element;

import java.util.ArrayList;
import java.util.List;

import com.ybj.file.config.ParseInitializable;
import com.ybj.file.parse.regist.ValidatorFactoryRegister;
import com.ybj.file.parse.regist.convertor.StringConvertor;
import com.ybj.file.parse.regist.merger.horizontal.StringJoinMerger;
import com.ybj.file.parse.regist.merger.vertical.FirstNoneEmptyMerger;
import com.ybj.file.parse.regist.validator.NoneValidator;
import com.ybj.file.parse.regist.validator.SingleCellValidator;
import com.ybj.file.parse.regist.validator.Validator;
import com.ybj.file.parse.regist.validator.factory.ValidatorFactory;
import com.ybj.file.util.ValidatorExpressionUtils;


/**
 * 
 * @author Administrator
 *
 */
@XmlElement(name="columnHead")
public class ColumnHead extends XElement implements ParseInitializable
{
    /**
     * 文件中的表头名称，用来确定映射关系、表头校验等
     */
    @XmlAttribute(name="titleName")
    private String titleName;
    
    /**
     * 在表头中，是否必须出现，true表示必须出现
     */
    @XmlAttribute(name="required")
    private boolean required;
    
    @XmlAttribute(name="fieldName")
    private String fieldName;
    
    private String dbFieldName;
    
    @XmlAttribute(name="type")
    private String convertorType = StringConvertor.REGIST_KEY;
    
    @XmlAttribute(name="horizontalMerger")
    private String horizontalMergerType = StringJoinMerger.REGIST_KEY;
    
    private String verticalMergerType = FirstNoneEmptyMerger.REGIST_KEY;
    
    @XmlAttribute(name="validator")
    private String validatorType = NoneValidator.REGIST_KEY;
    
    private List<SingleCellValidator> singleCellValidators = new ArrayList<>();
    /**
     * 擴展信息
     */
    @XmlAttribute(name="xmlExtendInfo")
    private String extendInfo;
    
    /**
     * 父级模板
     */
    private ToParseTemplate parentToParseTemplate;

    public ColumnHead()
    {
        
    }
    
    public ColumnHead(String titleName, String fieldName)
    {
        this(titleName,fieldName,false);
    }
    
    public ColumnHead(String titleName, String fieldName, boolean required)
    {
        this.titleName = titleName;
        this.fieldName = fieldName;
        this.required = required;
    }
    
    public String getTitleName()
    {
        return titleName;
    }

    public void setTitleName(String titleName)
    {
        this.titleName = titleName;
    }

    public boolean isRequired()
    {
        return required;
    }

    public void setRequired(boolean required)
    {
        this.required = required;
    }
    
    public String toString()
    {
        return "{" + this.titleName + ", " + this.required +"}";
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public String getDbFieldName()
    {
        return dbFieldName;
    }

    public void setDbFieldName(String dbFieldName)
    {
        this.dbFieldName = dbFieldName;
    }

    public String getConvertorType()
    {
        return convertorType;
    }

    public ColumnHead setConvertorType(String convertorType)
    {
        this.convertorType = convertorType;
        return this;
    }

    public String getHorizontalMergerType()
    {
        return horizontalMergerType;
    }

    public ColumnHead setHorizontalMergerType(String horizontalMergerType)
    {
        this.horizontalMergerType = horizontalMergerType;
        return this;
    }

    public String getVerticalMergerType()
    {
        return verticalMergerType;
    }

    public ColumnHead setVerticalMergerType(String verticalMergerType)
    {
        this.verticalMergerType = verticalMergerType;
        return this;
    }

    public String getValidatorType()
    {
        return validatorType;
    }

    public ColumnHead setValidatorType(String validatorType)
    {
        this.validatorType = validatorType;
        return this;
    }

    public String getExtendInfo()
    {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo)
    {
        this.extendInfo = extendInfo;
    }

    @Override
    public void clear()
    {
        // TODO Auto-generated method stub
        
    }

    public List<SingleCellValidator> getSingleCellValidators()
    {
        return singleCellValidators;
    }
    
    public void add(SingleCellValidator cellValidator) {
        singleCellValidators.add(cellValidator);
    }

    public ToParseTemplate getParentToParseTemplate() {
        return parentToParseTemplate;
    }

    public void setParentToParseTemplate(ToParseTemplate parentToParseTemplate) {
        this.parentToParseTemplate = parentToParseTemplate;
    }

    @Override
    public void before() {
        String[] expressions = this.validatorType.split("\\|");
        singleCellValidators.clear();
        for (String expression : expressions) {
            String[] params = ValidatorExpressionUtils.parseValidatorExpression(expression);
            ValidatorFactory vFactory = ValidatorFactoryRegister.singleton().getValidatorFactory(params[0]);
            Validator validator = vFactory.newValidator(params);
            singleCellValidators.add((SingleCellValidator) validator);
        }
    }
}
