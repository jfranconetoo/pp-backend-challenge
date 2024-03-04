package com.financialsimplified.picpay.domain.interfaces;

public interface EmailNotifier<T, D> {
    T sendEmail(D data);
}
