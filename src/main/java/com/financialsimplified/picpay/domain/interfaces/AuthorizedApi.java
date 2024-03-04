package com.financialsimplified.picpay.domain.interfaces;
public interface AuthorizedApi<T> {
    public T get(String url);
}
