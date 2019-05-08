package com.ybj.file.parse.core.xml;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * XML 路径
 * 
 * @author Administrator
 *
 */
public class XmlPath
{
    private List<String> path = new ArrayList<>();
    
    public void addLast(String tagName)
    {
        path.add(tagName);
    }
    
    public void addLast(XmlPath anotherPath)
    {
        path.addAll(anotherPath.getPath());
    }

    public void removeLast()
    {
        if (path.isEmpty())
        {
            return;
        }
        path.remove(path.size() - 1);
    }

    public String getCurTagName()
    {
        if (path.isEmpty())
        {
            return null;
        }
        return path.get(path.size() - 1);
    }

    public int getDeepth()
    {
        return path.size();
    }

    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }

        if (obj instanceof XmlPath)
        {
            XmlPath xp = (XmlPath) obj;
            if (this.path.size() == xp.path.size())
            {
                int i = 0;
                for (String tag : path)
                {
                    if (!StringUtils.equalsIgnoreCase(tag, xp.path.get(i++)))
                    {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public List<String> getPath()
    {
        return path;
    }

    public void setPath(List<String> path)
    {
        this.path = path;
    }
    
    @Override
    public String toString()
    {
        return path.toString();
    }
}
