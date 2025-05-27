package com.example.bankcards.util.mapper;

public interface Mapper<From, To> {
    To map(From from);

    default To map(From fromObject, To toObject) {
        return toObject;
    }
}
