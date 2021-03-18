package com.example.boot.service.mapper;

public interface ModelMapper<M, D> {
    M getModelFromDto(D requestDto);
}
