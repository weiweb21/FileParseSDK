package com.yanbenjun.file.test.model;

import java.util.List;

public class Person
{
    private Integer priority;
    
    private String name;
    
    private String description;
    
    private Integer age;
    
    private Double silary;
    
    private List<Integer> list;
    
    public Person(Integer i, String name)
    {
        this.priority = i;
        this.name = name;
    }
    
    public String toString()
    {
        return "{priority:" + this.priority +",name:" +this.name+"}";
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public double getSilary()
    {
        return silary;
    }

    public void setSilary(double silary)
    {
        this.silary = silary;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
        {
            return false;
        }
        if(obj instanceof Person)
        {
            Person p = (Person) obj;
            return this.priority.equals(p.getPriority()) && this.name.equals(p.getName());
        }
        return false;
    }

    public List<Integer> getList()
    {
        return list;
    }

    public void setList(List<Integer> list)
    {
        this.list = list;
    }
}
