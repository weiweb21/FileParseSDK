package com.yanbenjun.file.model.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yanbenjun.file.config.element.ToParseTemplate;

/**
 * 解析的行结果
 * @author Administrator
 *
 */
public class ParsedRow
{
    /**
     * 当前结果对应的sheet页（Excel用）
     */
    private int sheetIndex;
    /** 当前正在解析行对应的模板 */
    private ToParseTemplate curTemplate;
    
    /**
     * 当前行数据对应原始数据源中的行序号
     */
    private int rowIndex;
    
    /**
     * 具体数据（一个属性一个ColumnEntry）
     */
    private List<ColumnEntry> cells = new ArrayList<>();
    
    /**
     * 用于postHandle存储转换成Java类型的属性数据
     */
    private Map<String,Object> modelRow;
    
    /**
     * 是否最后一行数据
     */
    private boolean lastRow;
    
    public ParsedRow()
    {
        
    }
    
    public ParsedRow(ToParseTemplate curTemplate, int rowIndex)
    {
        this.curTemplate = curTemplate;
        this.sheetIndex = curTemplate.getSheetIndex();
        this.rowIndex = rowIndex;
    }
    
    public boolean isEmpty()
    {
        return this.cells.isEmpty();
    }

    public int getRowIndex()
    {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex)
    {
        this.rowIndex = rowIndex;
    }

    public List<ColumnEntry> getCells()
    {
        return cells;
    }

    public void setCells(List<ColumnEntry> cells)
    {
        this.cells = cells;
    }
    
    public String toString()
    {
        return "{row:"+this.rowIndex+",data"+this.cells+"}";
    }

    public int getSheetIndex()
    {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex)
    {
        this.sheetIndex = sheetIndex;
    }

    public Map<String,Object> getModelRow()
    {
        return modelRow;
    }

    public void setModelRow(Map<String,Object> modelRow)
    {
        this.modelRow = modelRow;
    }

    public boolean isLastRow()
    {
        return lastRow;
    }

    public void setLastRow(boolean lastRow)
    {
        this.lastRow = lastRow;
    }

    public ToParseTemplate getCurTemplate()
    {
        return curTemplate;
    }

    public void setCurTemplate(ToParseTemplate curTemplate)
    {
        this.curTemplate = curTemplate;
    }


}
