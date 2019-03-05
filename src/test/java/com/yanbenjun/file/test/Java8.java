package com.yanbenjun.file.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.yanbenjun.file.test.model.Person;


public class Java8
{
    public static void main(String[] args)
    {
        List<Person> persons = new ArrayList<Person>();
        /*Person p1 = new Person(1,"yan1");
        Person p2 = new Person(3,"yan2");
        Person p3 = new Person(6,"yan3");
        Person p4 = new Person(5,"yan4");
        Person p5 = new Person(4,"yan5");
        Person p6 = new Person(2,"yan6");
        Person p7 = new Person(8,"yan7");
        
        Person p8 = new Person(11,"yan7");*/
        Person p9 = new Person(9,"yan7");
        Person p10 = new Person(10,"yan6");
        Person p11 = new Person(null,null);
        
        /*persons.add(p1);
        persons.add(p2);
        persons.add(p3);
        persons.add(p4);
        persons.add(p5);
        persons.add(p6);
        persons.add(p7);
        persons.add(p8);*/
        
        List<Integer> l1 = Arrays.asList(new Integer[]{1,2,3});
        List<Integer> l2 = new ArrayList<Integer>();
        l2.addAll(Arrays.asList(new Integer[]{4,5,6}));
        p9.setList(l1);
        p10.setList(l2);
        p11.setList(l2);
        persons.add(p9);
        persons.add(p10);
        persons.add(p11);
        
        List<String> ss = persons.stream().map(p->p.getName()).collect(Collectors.toList());
        System.out.println(ss);
        
        Map<String,List<Integer>> m = test1(persons);
        System.out.println("before:" + m);
        l2.add(9);
        System.out.println("after:"+m);
        Map<String,List<Object>> 吗= addIfAb2(persons);
        System.out.println(p11.getList());
        System.out.println(吗);
    }
    
    public static Map<String,List<Integer>> test1(List<Person> cells)
    {
        Map<String,Integer> persons = cells.stream().collect(Collectors.toMap(Person::getName, Person::getPriority));
        System.out.println(persons);
        
        Map<String,List<Integer>> ss = cells.stream().collect(Collectors.toMap(Person::getName, Person::getList, mergeList(),()->{return new HashMap<>();}));
        return ss;
    }
    
    public static  void addIfAb(List<Person> cells)
    {
        Map<String,List<Object>> lastModelRowMap = new HashMap<String,List<Object>>();
        for (int i = 0; i < cells.size(); i++)
        {
            Person ce = cells.get(i);
            lastModelRowMap.computeIfAbsent(ce.getName(), k -> new ArrayList<Object>()).add(ce.getPriority());
        }
        System.out.println(lastModelRowMap);
    }
    
    public static  Map<String,List<Object>> addIfAb2(List<Person> cells)
    {
        Map<String,List<Object>> lastModelRowMap = new HashMap<String,List<Object>>();
        cells.stream().forEach(cell-> lastModelRowMap.computeIfAbsent(cell.getName(), k -> {System.out.println(k); return new ArrayList<>();}).add(cell.getPriority()));
        return lastModelRowMap;
    }
    
    public static  void addIfAb3(List<Person> cells)
    {
        Map<String,List<Integer>> lastModelRowMap = cells.stream().collect(Collectors.groupingBy(Person::getName, HashMap::new ,Collectors.mapping(Person::getPriority, Collectors.toList())));
        System.out.println(lastModelRowMap);
    }

    public static BinaryOperator<List<Integer>> mergeList()
    {
        BinaryOperator<List<Integer>> bo = new BinaryOperator<List<Integer>>(){
            @Override
            public List<Integer> apply(List<Integer> t, List<Integer> u)
            {
                return t == null ? u : u == null ? t : CollectionUtils.collate(t, u);
            }
        };
        
        
        Supplier<Map> s = new Supplier<Map>(){

            @Override
            public Map get()
            {
                return null;
            }
            
        };
        
        new Function(){
            @Override
            public Object apply(Object t)
            {
                // TODO Auto-generated method stub
                return null;
            }
            
        };
        return bo;
    }
    
}
