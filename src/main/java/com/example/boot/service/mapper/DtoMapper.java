package com.example.boot.service.mapper;

public interface DtoMapper<M, D> {
    D getDtoFromModel(M entity);
}
