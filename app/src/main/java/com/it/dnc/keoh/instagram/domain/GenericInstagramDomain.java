package com.it.dnc.keoh.instagram.domain;

public class GenericInstagramDomain<T> extends BaseDomain {
    
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}