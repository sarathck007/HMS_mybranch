package com.wk6.assignment4.mapper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageableMapper {
    
    public Pageable toPageable(Integer page, Integer size, String sortBy, String sortDirection) {
        int pageNumber = (page != null && page > 0) ? page - 1 : 0;
        int pageSize = (size != null && size > 0) ? size : 10;
        
        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.isEmpty()) {
            sort = Sort.by(Sort.Direction.fromString(sortDirection != null ? sortDirection : "ASC"), sortBy);
        }
        
        return PageRequest.of(pageNumber, pageSize, sort);
    }
}