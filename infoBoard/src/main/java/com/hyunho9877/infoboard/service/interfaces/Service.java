package com.hyunho9877.infoboard.service.interfaces;

public interface Service<T> {
    void apply(T t, String studNo, String dept);
    void delete(T t, String writer);
    void update(T t, String writer);
}
