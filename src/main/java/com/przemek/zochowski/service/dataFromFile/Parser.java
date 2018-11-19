package com.przemek.zochowski.service.dataFromFile;

public interface Parser<T> {
    T parse(String text);
}
