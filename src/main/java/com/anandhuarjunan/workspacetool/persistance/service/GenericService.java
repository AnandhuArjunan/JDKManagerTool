package com.anandhuarjunan.workspacetool.persistance.service;


import java.io.Serializable;
import java.util.List;

public interface GenericService<T, K>
{
    T find(Class<T> clazz,K id);
    List<T> findAll(Class<T> clazz);
    List<T> find(String queryName, String [] paramNames, Object [] bindValues);

    K save(T instance);
    void update(T instance);
    void delete(T instance);
}
