Excel导入框架使用说明

| 责任人 | 修改日期  | 版本变更记录                                             |
| ------ | --------- | -------------------------------------------------------- |
| 颜本军 | 2019/5/17 | 初始化                                                   |

## 概要

  框架命名Eiuse，后续文档称呼为Eiuse。框架定位是简化Java语言对Excel文件、xml文件导入编程操作，提供Java SDK包和Springboot Starter 两种jar包直接集成到我们需要的项目中。



### 一、引入工具包或者源码

1、非SpringBoot项目，POM 引入 Java SDK  

```xml
<groupId>com.cmiot</groupId>
<artifactId>FileParseSDK</artifactId>
<version>0.0.1-SNAPSHOT</version>
```

2、SpringBoot项目，POM引入SpringBoot Starter

```xml
<groupId>com.cmiot</groupId>
<artifactId>spring-boot-FileParse-starter</artifactId>
<version>0.0.1-SNAPSHOT</version>
```

3、将源码集成到Lion框架中（使用最新版的spring-boot-mng-starter）

### 二、简单的示例

模板配置通过xml文件配置，默认使用类路径下的 *fileParseContext.xml*  作为模板配置文件。

示例，假如在你的系统中需要提供一个人员信息导入接口，导入如下图的人员信息Excel文件

![待导入Excel](C:\Users\yanbenjun\AppData\Roaming\Typora\typora-user-images\1557220026366.png)

1、配置模板，需要导入的列

```xml
<?xml version="1.0" encoding="UTF-8"?>

<fileParse xmlns="http://www.yanbenjun.com" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.yanbenjun.com fileParse.xsd">
    <!-- 
        *解析系统，集成到业务系统中，让业务系统能够方便的让开发整合excel、csv、xml数据解析功能到业务系统；
        *可以灵活的配置不同的模板、或者竟可能多的包络系统已知属性，形成一个通用模板；
        *具体用什么model装数据、存储到什么数据库、可以配置到xml里写死（适合固定模板的解析）；也可以不用配置，等用户上传
        *完文件后，让用户确认文件类型或者将类型写入到Excel自动识别（和业务有关），类型可以知道数据存储的java模型和具体入库
        *数据库。
     -->
	<parseSystem name="powerMng">
		<!-- 通用列配置 -->
		<columns>
		</columns>

		<!-- 通用sheet模板配置 -->
		<templates>
		</templates>

		<!-- 通用解析文件配置 -->
		<files>
		</files>

        <!-- 解析点，具体的业务系统，哪里需要解析文件，只需要在请求中传入该解析点ID，就可以自动根据解析点的配置，自动解析入库 -->
		<parsePoint id="20190507"> <!-- 唯一ID -->
			<toParseFile name="test" mode="single">
				<toParseTemplate name="test" sheetIndex="0" handler="com.cmiot.mng.file.test.MyRowHandler">
	                <columnHead titleName="姓名" fieldName="name" required="true"
	                    type="string" validator="notnull"/>
	                <columnHead titleName="年龄" fieldName="age" required="true"
	                    type="integer" />
	                <columnHead titleName="性别" fieldName="sex" required="true"
	                    type="string" validator="notnull"/>
	                <columnHead titleName="所属岗位" fieldName="job" required="true"
	                    type="string" validator="notnull"/>
	                <columnHead titleName="测试字段1" fieldName="testField1" required="false"
	                    type="string"/>
                </toParseTemplate>
			</toParseFile>
		</parsePoint>
	</parseSystem>
</fileParse>
```

2、写自己的行处理器，完成自己的业务（如写入数据库、缓存、或者输出到控制台）

```java
public class MyRowHandler extends TeminationPostRowHandler//继承框架提供的基础行处理器
{

    /**
     * 实现自己的业务逻辑
     */
    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException
    {
        if (parseContext.getCurRowMsg().isHasError()) {//处理当前行的校验错误
            System.out.println(parsedRow.getCells());
            System.out.println(parseContext.getCurRowMsg().getCellParseMsgs());
        } else {//处理没有行校验错误的数据
            System.out.println(parsedRow.getModelRow());//正常解析的数据以Map形式提供
            // OR INSERT DB
            System.out.println(parseContext.getCurRowMsg().getCellParseMsgs());//处理该列错误
        }
    }

}

```

3、提供Restful接口

```java
//@Restful
public void import(String filename, InputStream ins) {
    try {
        //获取该导入入口对应的解析点parser
        FileParser fp = FileParserFactory.getFileParser(filename, ins, 20190507L);
    	ParseContext context = fp.getContext();//解析上下文，可以通过此上下文把自己的参数传递到你自己的Handler，也可以获取到Handler中返回的信息，如解析错误信息
    	context.putAll(packet.formData);
    	fp.parse();
    } catch (Exception e) {
        //TODO
    }
}

```

  这样通过以上三步，我们的一个基本的人员信息导入接口就算完成了。

## 模板配置
### 一、标签说明
#### 1、 parseSystem

parseSystem对应我们的应用系统，一个应用系统下有一个唯一的xml配置文件和parseSystem，可以给parseSystem命名。parseSystem下的属性和节电如下列表

| 属性or子节点 | 描述 | 类型 | 限制条件 |
| ------ | --------- | ---- | --------------- |
| name | 系统名称 | 属性 | string |
| columns | 公共列配置 | 子节点 | multi |
| templates | 公共Sheet模板配置 | 子节点 | multi |
| files | 公共文件模板配置 | 子节点 | multi |
| parsePoint | 解析点 | 子节点 | multi |

#### 2、 parsePoint

parsePoint对应我们的应用系统中的导入接口，对应前台的一个导入http请求。

| 属性or子节点 | 描述 | 类型 | 限制条件 | 备注 |
| ------ | --------- | ---- | --------------- | --- |
| id | 解析点唯一标识 | 属性 | Long | must |
| toParseFile | 文件模板 | 子节点 | multi | 与refFile二者必须至少有一个 |
| refFile | 引用公共的toParseFile | 子节点 | multi | 与toParseFile二者必须至少有一个 |
说明：
1）、当parsePoint下有多个toParseFile，代表该导入接口支持多文件模板解析，解析系统能够自动识别用户上传的文件，根据文件里的label（对应toParseFile的属性name）自动识别该上传文件解析使用的toParseFile文件模板。
2）、当parsePoint下只有一个toParseFile，代表该导入接口支持toParseFile匹配文件模板的解析，用户上传的文件无需要label，解析系统会自动识别所有上传的文件为toParseFile文件模板类型的导入文件。

#### 3、toParseFile
toParseFile对应我们需要解析的文件模板，注意这里是文件模板，不是文件本身。
| 属性or子节点 | 描述 | 类型 | 限制条件 | 备注 |
| ------ | --------- | ---- | --------------- | --- |
| name | 文件模板名称 | 属性 | String | must |
| mode | 文件模板Sheet页数量 | 子节点 | enum | 文件模板下sheet模板的数量 |
| toParseTemplate | Sheet模板 | 子节点 | multi | 与refTemplate二者必须至少有一个 |
| refTemplate | 引用公共的toParseTemplate | 子节点 | multi | 与toParseTemplate二者必须至少有一个 |

#### 4、toParseTemplate
toParseTemplate对应我们真正解析的文件内容模板（如Excel的sheet页模板）。
| 属性or子节点 | 描述 | 类型 | 限制条件 | 备注 |
| ------ | --------- | ---- | --------------- | --- |
| name | sheet模板名称 | 属性 | String | optional |
| sheetIndex | 需要解析的sheet页序号 | 属性 | number | must（default=0） |
| headRow | 表头所在行 | 属性 | number | must（default=0） |
| contentStartRow | 内容开始行 | 属性 | number | must（default=1） |
| handler | 用户自己的行处理器 | 属性 | string | must（用户自定义处理框架解析的行记录，需要继承框架提供的基类TeminationPostRowHandler） |
| validator | （sheet）模型校验器 | 属性 | string（validatorExpressions） | 框架内置了联合主键校验器 |
| columnHead | 列表头 | 子节点 | multi | 与refColumn二者必须至少有一个 |
| refColumn | （sheet）模型校验器 | 子节点 | multi | 与columnHead二者必须至少有一个 |

#### 5、columnHead
columnHead对应我们sheet页表头属性。
| 属性or子节点 | 描述 | 类型 | 限制条件 | 备注 |
| ---- | --------------- | ---- | --- | ----- |
| id | 列ID | 属性 | number | optional，在公共columns下使用时必填 |
| titleName | 模板表头列名称（Excel表头列） | 属性 | string | must |
| fieldName | 列属性对应的Java域名 | 属性 | string | must |
| required | 列是否必须包含 | 属性 | boolean | must（default=false）（true=必须包含，false=可选列，有则解析，没则不解析） |
| type | 列属性类型 | 属性 | string | 支持string,int,double,boolean（default=string） |
| validator | 列属性校验器 | 属性 | string（validatorExpressions） | 框架内置了多种校验器 |
| horizontalMerger | 属性垂直合并器 | 属性 | string | 垂直合并器向系统注册的key，通过key获取java垂直合并器，功能待完善，不建议使用 |
| xmlExtendInfo | xml数据提取使用，xml列位置信息 | 属性 | string | 功能待完善，不建议使用 |

#### 6、其它
files、templates、columns分别作为toParseFile、toParseTemplate、columnHead的公共配置容器，公共配置可以重复使用。在可以使用的地方通过refFile、refTemplate、refColumn标签引用公共容器里已经配置好的对象。

### 二、进阶使用
#### 1、传递解析参数和解析结果
ParseContext，解析上下文可以帮助我们在解析开始前设置自己需要向后续解析处理时需要用到的参数，另一方面，也可以将解析的结果传递到解析完毕后，例如解析格式错误信息，需要在解析完毕后传递给解析调用方。

```java
//@Restful
public List<String> import(String filename, InputStream ins) {
    try {
        //获取该导入入口对应的解析点parser
        FileParser fp = FileParserFactory.getFileParser(filename, ins, 20190507L);
        ParseContext context = fp.getContext();//解析开始前，通过FileParser获取到ParseContext对象
        Object params = "ybj"//你需要传递的参数;
        context.put("user", params);//添加你需要传递的参数
        fp.parse();//开始解析
        List<String> errorMsgs = (List<String>)parseContext.get("errorMsgs");// 解析完毕获取解析中的错误信息
        return errorMsgs;//错误信息返回给前台
    } catch (Exception e) {
        //TODO
    }
}
```

```java
public class MyRowHandler extends TeminationPostRowHandler//继承框架提供的基础行处理器
{
    private List<String> errorMsgs = new ArrayList<>();
    /**
     * 实现自己的业务逻辑
     */
    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException
    {
        //TODO 你自己的业务处理逻辑
        errorMsgs.add(getErrorMsg(parsedRow, parseContext))
        if(parsedRow.isLastRow()){
            //TODO 你自己的业务处理逻辑
            //放入所有解析异常信息，可以在解析完毕后通过key获取你传递的值
            parseContext.put("errorMsgs",errorMsgs);
        }
    }
    
    private String getErrorMsg(ParsedRow parsedRow, ParseContext parseContext) {
        //TODO 获取解析异常信息
        return "获取解析异常信息";
    }

}

```

#### 2、最终后置行数据处理器-TeminationPostRowHandler
##### a、xml中配置的位置
用户继承实现自己的TeminationPostRowHandler，在xml中的toParseTemplate标签的属性handler指定用户实现的类全名，如下所示
```xml
<toParseTemplate name="test" sheetIndex="0" handler="com.cmiot.mng.file.test.MyRowHandler">
    ...
</toParseTemplate>
```
##### b、实现
```java
public class MyRowHandler extends TeminationPostRowHandler {
    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException {
        //TODO 你自己的业务处理逻辑
    }

}

```
#### 3、校验器
说明：不同校验器可以用|分隔，联合使用，如notnull|integer，如下表示同时校验所属岗位列不能为空且填入的值必须为整数类型。

```xml
<columnHead titleName="所属岗位" fieldName="job" required="true"
	                    type="string" validator="notnull|integer"/>
```

##### NoneValidator（无校验）
| 关键字 | 校验位置 | 作用 | 使用说明 |
| ---- | --------------- | ---- | --- |
| none | 列 | 不校验 | 无 |

##### NotNullValidator (非空校验器)
| 关键字 | 校验位置 | 作用 | 使用说明 |
| ---- | --------------- | ---- | --- |
| notnull | 列 | 属性非空校验 | 无 |


##### BooleanValidator (布尔校验器)
| 关键字 | 校验位置 | 作用 | 使用说明 |
| ---- | --------------- | ---- | --- |
| boolean | 列 | 布尔校验 | 无 |

##### IntegerValidator (整数校验器)
| 关键字 | 校验位置 | 作用 | 使用说明 |
| ---- | --------------- | ---- | --- |
| integer | 列 | 整数校验 | 无 |

##### DoubleValidator (小数校验器)
| 关键字 | 校验位置 | 作用 | 使用说明 |
| ---- | --------------- | ---- | --- |
| double | 列 | 小数校验 | 无 |

##### EnumValidator (枚举校验器)
| 关键字 | 校验位置 | 作用 | 使用说明 |
| ---- | --------------- | ---- | --- |
| enum(expression,[key1,value1,key2,value2,...]) | 列 | 枚举校验 | 当第一个参数=expression时，直接将key,value依次写入表达式 |
| enum(enumclass,[classOrBean]) | 列 | 枚举校验 | 当第一个参数=enumclass时，classOrBean表示：实现了接口EnumEntry的枚举类全名 |
| enum(query,[classOrBean]) | 列 | 枚举校验 | 当第一个参数=query时，classOrBean表示：用户自己实现继承了DbEnumDataSource抽象类的自定义数据库枚举源 |
###### A. expression
```xml
<columnHead titleName="所属岗位" fieldName="job" required="true"
	                    type="string" validator="enum(expression, Java,0,JavaScript,1)"/>
```

###### B. enumclass
```xml
<columnHead titleName="所属岗位" fieldName="job" required="true"
	                    type="string" validator="enum(enumclass, com.ybj.file.test.MyEnumType)"/>
```
```java
public enum MyEnumType implements EnumEntry {
    JAVA("Java", 0), JAVASCRIPT("JavaScript", 1);
    private String fdKey;

    private Object fdValue;

    private MyEnumType(String fdKey, Object fdValue) {
        this.fdKey = fdKey;
        this.fdValue = fdValue;
    }

    @Override
    public String getFdKey() {
        // TODO Auto-generated method stub
        return this.fdKey;
    }

    @Override
    public Object getFdValue() {
        // TODO Auto-generated method stub
        return this.fdValue;
    }

}
```

###### C. query
```xml
<columnHead titleName="所属岗位" fieldName="job" required="true"
	                    type="string" validator="enum(query, com.ybj.file.test.MyEnumDataSource)"/>
```
```java
public class RoomNameEnumDataSource extends DbEnumDataSource {

    /**
     * 获取形如[{"fdKey":enumKey,"fdValue":enumValue},{...}]的list
     * 
     * @param args
     * @return
     */
    @Override
    public List<Map<String, Object>> queryDataList(String... args) {

        // TODO 查询数据
        return new ArrayList<>();
    }

}
```
##### UniqueValidator (唯一行校验器)
| 关键字 | 校验位置 | 作用 | 使用说明 |
| ---- | --------------- | ---- | ----- |
| unique([classOrBean],[key1,key2,...]) | sheet | 主键or联合主键唯一性校验 | classOrBean表示：用户自己继承UniqueValidator实现的唯一校验器；key1，key2等表示需要联合校验的列属性 |

使用示例：
```xml
<toParseTemplate name="airConditioner" sheetIndex="1" handler="com.ybj.file.test.MyRowHandler" validator="unique(com.ybj.file.test.MyUniqueValidator, ac_brand)">
...
</toParseTemplate>
```
```java

public class MyUniqueValidator extends CacheUniqueValidator {

    public MyUniqueValidator() {
        super();
    }

    @Override
    public List<Map<String, Object>> queryExistValues() {
        //TODO QUERY FROM DATABASE
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("ac_brand", "美的");
        return list;
    }

}
```