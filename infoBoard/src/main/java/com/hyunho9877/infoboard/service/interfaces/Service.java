package com.hyunho9877.infoboard.service.interfaces;

public interface Service<T> {
    void apply(T t, String studNo, String name);
    void delete(T t);
    void update(T t);
}
