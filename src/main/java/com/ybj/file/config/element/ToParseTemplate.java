package com.ybj.file.config.element;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import com.ybj.file.config.ParseConfigurationException;
import com.ybj.file.config.ParseInitializable;
import com.ybj.file.config.element.init.TeminationPostRowHandlerFactory;
import com.ybj.file.config.element.init.TeminationPostRowHandlerFactoryRegister;
import com.ybj.file.model.ToParseHead;
import com.ybj.file.model.ToParserTemplateType;
import com.ybj.file.parse.core.post.TeminationPostRowHandler;
import com.ybj.file.parse.regist.TypeConvertorRegister;
import com.ybj.file.parse.regist.TypeHorizontalMergerRegister;
import com.ybj.file.parse.regist.TypeVerticalMergerRegister;
import com.ybj.file.parse.regist.ValidatorFactoryRegister;
import com.ybj.file.parse.regist.convertor.TypeConvertor;
import com.ybj.file.parse.regist.merger.horizontal.TypeHorizontalMerger;
import com.ybj.file.parse.regist.merger.vertical.TypeVerticalMerger;
import com.ybj.file.parse.regist.validator.MultiCellValidator;
import com.ybj.file.parse.regist.validator.SingleCellValidator;
import com.ybj.file.parse.regist.validator.Validator;
import com.ybj.file.parse.regist.validator.factory.ValidatorFactory;
import com.ybj.file.util.ValidatorExpressionUtils;



//TODO
//1。错误信息系统需要改进，属性校验系统融入（自定义校验等），表头校验系统融入
//2。依次完善xls，txt，xml解析
//3。删除无用代码，添加注释，注意代码规范，多使用JAVA8新特性
//4***.最重要的，模板相关信息全部实现由xml配置而来，解析xml配置文件生成待解析模板实例，各个业务域根据自己的业务，设计模板与数据库模型对应关系，配置到xml，实现导入自动实现
@XmlElement(name="toParseTemplate")
public class ToParseTemplate extends XElement implements ParseInitializable
{
    /**
     * 第几个sheet页
     */
    @XmlAttribute(name="sheetIndex")
    private int sheetIndex;
    
    /**
     * 用户自己的处理器,Class全路劲或者SpringBeanName
     */
    @XmlAttribute(name = "handler")
    private String handler;
    
    /**
     * 用户自己的处理器
     */
    private TeminationPostRowHandler teminationPostRowHandler;

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
    
    @XmlAttribute(name = "validator")
    private String multiCellValidatorsExpression;

    private List<MultiCellValidator> multiCellValidators = new ArrayList<>();
    
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
    }
    
    public ToParseTemplate(int keyIndex, int startContent, List<ColumnHead> columnHeads)
    {
        this.headRow = keyIndex;
        ToParseHead tph = new ToParseHead();
        this.toParseHead = tph;
        this.setStartContent(startContent);
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

    public void addMultiValidator(MultiCellValidator multiCellValidator) {
        multiCellValidators.add(multiCellValidator);
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

    public TypeConvertor<?> getTypeConvertor(String field)
    {
        String convertorType = this.toParseHead.getByFieldName(field).getConvertorType();
        return TypeConvertorRegister.singleton().getTypeConvertor(convertorType);
    }
    
    public List<SingleCellValidator> getSingleCellValidator(String field)
    {
        return this.toParseHead.getByFieldName(field).getSingleCellValidators();
    }

    public TypeHorizontalMerger<?> getTypeHorizontalMerger(String field)
    {
        String horizontalMergerType = this.toParseHead.getByFieldName(field).getHorizontalMergerType();
        return TypeHorizontalMergerRegister.singleton().getTypeHorizontalMerger(horizontalMergerType);
    }
    
    public TypeVerticalMerger<?> getTypeVerticalMerger(String field)
    {
        String verticalMergerType = this.toParseHead.getByFieldName(field).getVerticalMergerType();
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
    
    public Map<String, Object> getFullFieldEmptyMap() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        List<ColumnHead> heads = toParseHead.getColumnHeads();
        for (ColumnHead ch : heads) {
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

    @Override
    public void clear()
    {
        toParseHead = null;
    }

    public String getMultiCellValidatorsExpression() {
        return multiCellValidatorsExpression;
    }

    public void setMultiCellValidatorsExpression(String multiCellValidatorsExpression) {
        this.multiCellValidatorsExpression = multiCellValidatorsExpression;
    }

    public String getHandler()
    {
        return handler;
    }

    public void setHandler(String handler)
    {
        this.handler = handler;
        for (TeminationPostRowHandlerFactory factory : TeminationPostRowHandlerFactoryRegister.getAllRegistedHandlerFactorys()) {
            try
            {
                this.teminationPostRowHandler = factory.build(handler);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        if (this.teminationPostRowHandler == null) {
            throw new ParseConfigurationException("找不到“"+this.handler+"”对应的处理器对象。");
        }
    }

    public TeminationPostRowHandler getTeminationPostRowHandler()
    {
        return teminationPostRowHandler;
    }

    public void setTeminationPostRowHandler(TeminationPostRowHandler teminationPostRowHandler)
    {
        this.teminationPostRowHandler = teminationPostRowHandler;
    }

    public List<MultiCellValidator> getMultiCellValidators() {
        return multiCellValidators;
    }

    @Override
    public void before() {
        if (StringUtils.isNotBlank(this.multiCellValidatorsExpression)) {
            String[] expressions = this.multiCellValidatorsExpression.split("\\|");
            multiCellValidators.clear();
            for (String expression : expressions) {
                String[] params = ValidatorExpressionUtils.parseValidatorExpression(expression);
                ValidatorFactory vFactory = ValidatorFactoryRegister.singleton().getValidatorFactory(params[0]);
                Validator validator = vFactory.newValidator(params);
                multiCellValidators.add((MultiCellValidator) validator);
            }
        }
        this.toParseHead.getColumnHeads().stream().forEach(item -> {
            item.before();
        });
    }
}
