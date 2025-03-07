package com.wk6.assignment4.mapper;

import org.springframework.data.domain.Page;
import java.util.List;
import java.util.stream.Collectors;

public interface EntityMapper<D, E> {
    E toEntity(D dto);
    D toDto(E entity);
    
    default List<E> toEntity(List<D> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
    
    default List<D> toDto(List<E> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    default Page<D> toDto(Page<E> entityPage) {
        return entityPage.map(this::toDto);
    }
}
