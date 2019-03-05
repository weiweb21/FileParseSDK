package com.yanbenjun.file.config.element;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import com.yanbenjun.file.model.ToParseHead;
import com.yanbenjun.file.model.ToParserTemplateType;
import com.yanbenjun.file.parse.regist.ToParserFileTypeRegister;
import com.yanbenjun.file.parse.regist.TypeConvertorRegister;
import com.yanbenjun.file.parse.regist.TypeHorizontalMergerRegister;
import com.yanbenjun.file.parse.regist.TypeValidatorRegister;
import com.yanbenjun.file.parse.regist.TypeVerticalMergerRegister;
import com.yanbenjun.file.parse.regist.type.TypeConvertor;
import com.yanbenjun.file.parse.regist.type.TypeHorizontalMerger;
import com.yanbenjun.file.parse.regist.type.TypeValidator;
import com.yanbenjun.file.parse.regist.type.TypeVerticalMerger;


//TODO
//1。错误信息系统需要改进，属性校验系统融入（自定义校验等），表头校验系统融入
//2。依次完善xls，txt，xml解析
//3。删除无用代码，添加注释，注意代码规范，多使用JAVA8新特性
//4***.最重要的，模板相关信息全部实现由xml配置而来，解析xml配置文件生成待解析模板实例，各个业务域根据自己的业务，设计模板与数据库模型对应关系，配置到xml，实现导入自动实现
@XmlElement(name="toParseTemplate")
public class ToParseTemplate extends XElement implements XElementAddable
{
    /**
     * 第几个sheet页
     */
    @XmlAttribute(name="sheetIndex")
    private int sheetIndex;
    
    /**
     * 第几个sheet页
     */
    @XmlAttribute(name = "postChain")
    private String postChain;

    /**
     * 当前sheet页解析优先级
     */
    private int priority;
    
    /**
     * 内容起始行
     */
    @XmlAttribute(name="contentStartRow")
    private int startContent;
    
    /**
     * 表头行
     */
    @XmlAttribute(name="headRow")
    private int headRow;
    
    private String modelClass;
    
    private String type;
    
    private String typeEnm;
    
    private String userDefined;
    
    private ToParserTemplateType templateType;
    
    private Class<?> clss;
    
    @XmlElement(name="columnHead", subElement=ColumnHead.class)
    @XmlElement(name="refColumn")
    private ToParseHead toParseHead;
    
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    
    public ToParseTemplate()
    {
        this(0,0,1);
    }
    
    public ToParseTemplate(int sheetIndex)
    {
        this(sheetIndex,0,1);
    }
   
    public ToParseTemplate(int sheetIndex, int keyIndex, int startContent)
    {  
        this.headRow = keyIndex;
        this.sheetIndex = sheetIndex;
        ToParseHead tph = new ToParseHead();
        this.toParseHead = tph;
        this.setStartContent(startContent);
        this.setTemplateType(ToParserFileTypeRegister.singletonInstance().getDefault());
    }
    
    public ToParseTemplate(int keyIndex, int startContent, List<ColumnHead> columnHeads)
    {
        this.headRow = keyIndex;
        ToParseHead tph = new ToParseHead();
        this.toParseHead = tph;
        this.setStartContent(startContent);
        this.setTemplateType(ToParserFileTypeRegister.singletonInstance().getDefault());
        this.toParseHead.addAll(columnHeads);
    }
    
    public static ToParseTemplate getDefaultTemplate(int id)
    {
        return DefaultTemplates.defaultTemplates.computeIfAbsent(id, k -> new ToParseTemplate(id));
    }
    
    private static class DefaultTemplates
    {
        private static final Map<Integer,ToParseTemplate> defaultTemplates = new ConcurrentHashMap<Integer, ToParseTemplate>();
    }
    
    @Override
    public void add(XElement xe)
    {
        add((ColumnHead)xe);
    }
    
    public void add(ColumnHead columnHead)
    {
        toParseHead.add(columnHead);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public int getStartContent()
    {
        return startContent;
    }

    public void setStartContent(int startContent)
    {
        this.startContent = startContent;
    }

    public ToParserTemplateType getTemplateType()
    {
        return templateType;
    }

    public void setTemplateType(ToParserTemplateType templateType)
    {
        this.templateType = templateType;
    }
    
    public ToParseHead getToParseHead()
    {
        return toParseHead;
    }

    public TypeConvertor<?> getTypeConvertor(String title)
    {
        String convertorType = this.toParseHead.getByFieldName(title).getConvertorType();
        return TypeConvertorRegister.singleton().getTypeConvertor(convertorType);
    }
    
    public TypeValidator getTypeValidator(String title)
    {
        String validatorType = this.toParseHead.getByFieldName(title).getValidatorType();
        return TypeValidatorRegister.singleton().getTypeValidator(validatorType);
    }

    public TypeHorizontalMerger<?> getTypeHorizontalMerger(String title)
    {
        String horizontalMergerType = this.toParseHead.getByFieldName(title).getHorizontalMergerType();
        return TypeHorizontalMergerRegister.singleton().getTypeHorizontalMerger(horizontalMergerType);
    }
    
    public TypeVerticalMerger<?> getTypeVerticalMerger(String title)
    {
        String verticalMergerType = this.toParseHead.getByFieldName(title).getVerticalMergerType();
        return TypeVerticalMergerRegister.singleton().getTypeVerticalMerger(verticalMergerType);
    }
    
    public String getPrimaryKey()
    {
        List<ColumnHead> heads = toParseHead.getColumnHeads();
        List<String> pKeys = heads.stream().filter(ColumnHead::isRequired).map(ColumnHead::getFieldName).collect(Collectors.toList());
        return StringUtils.join(pKeys, "##");
    }
    
    public String getFieldName(String title)
    {
        List<ColumnHead> heads = toParseHead.getColumnHeads();
        for(ColumnHead ch : heads)
        {
            if(StringUtils.equals(ch.getTitleName(), title))
            {
                return ch.getFieldName();
            }
        }
        return null;
    }
    
    public Map<String,Object> getFullFieldEmptyMap()
    {
        Map<String,Object> map = new LinkedHashMap<String,Object>();
        List<ColumnHead> heads = toParseHead.getColumnHeads();
        for(ColumnHead ch : heads)
        {
            map.put(ch.getFieldName(), null);
        }
        return map;
    }
    
    @Override
    public String toString()
    {
        return "\r\n{id = " + this.id +"\r\n"
                +"name = " + this.name +"\r\n"
                +"priority = " + this.priority +"\r\n"
                +"startContent = " + this.startContent +"\r\n"
                +"type = " + this.type +"\r\n"
                +"toParseHead = " + this.toParseHead +"}";
    }

    public int getHeadRow()
    {
        return headRow;
    }

    public void setHeadRow(int headRow)
    {
        this.headRow = headRow;
    }

    public String getModelClass()
    {
        return modelClass;
    }

    public void setModelClass(String modelClass)
    {
        this.modelClass = modelClass;
    }

    public String getTypeEnm()
    {
        return typeEnm;
    }

    public void setTypeEnm(String typeEnm)
    {
        this.typeEnm = typeEnm;
    }

    public String getUserDefined()
    {
        return userDefined;
    }

    public void setUserDefined(String userDefined)
    {
        this.userDefined = userDefined;
    }

    public Class<?> getClss()
    {
        return clss;
    }

    public void setClss(Class<?> clss)
    {
        this.clss = clss;
    }

    public int getSheetIndex()
    {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex)
    {
        this.sheetIndex = sheetIndex;
    }

    public String getPostChain() {
        return postChain;
    }

    public void setPostChain(String postChain) {
        this.postChain = postChain;
    }

    @Override
    public void clear()
    {
        toParseHead = null;
    }

}
