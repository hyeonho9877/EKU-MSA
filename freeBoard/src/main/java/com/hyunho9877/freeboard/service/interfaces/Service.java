package com.hyunho9877.freeboard.service.interfaces;

public interface Service<T> {
    T apply(T t, String studNo, String dept);
    void delete(T t, String writer);
    T update(T t, String writer);
}
