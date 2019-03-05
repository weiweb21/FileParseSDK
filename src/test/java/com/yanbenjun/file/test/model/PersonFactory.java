package com.yanbenjun.file.test.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PersonFactory
{
    public static List<Person> randomPersons(int size)
    {
        List<Person> persons = new ArrayList<Person>();
        Random rand = new Random();
        for(int i=0; i<size; i++)
        {
            Person p = new Person(i,"yan" + rand.nextInt(10000));
            persons.add(p);
        }
        return persons;
    }
}
