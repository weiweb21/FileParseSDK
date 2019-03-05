package com.yanbenjun.file.config.element;

import com.yanbenjun.file.parse.regist.type.FirstNoneEmptyMerger;
import com.yanbenjun.file.parse.regist.type.NoneValidator;
import com.yanbenjun.file.parse.regist.type.StringConvertor;
import com.yanbenjun.file.parse.regist.type.StringJoinMerger;

/**
 * 
 * @author Administrator
 *
 */
@XmlElement(name="columnHead")
public class ColumnHead extends XElement
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
    
    private String validatorType = NoneValidator.REGIST_KEY;
    
    /**
     * 擴展信息
     */
    @XmlAttribute(name="xmlExtendInfo")
    private String extendInfo;
    
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
}
