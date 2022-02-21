package com.Driver;

public interface IManagable  {
    
    public void add(Object obj);

    public Object get(String name);

    public void remove(String name);

    public void sort();

    public void getTotal();
}