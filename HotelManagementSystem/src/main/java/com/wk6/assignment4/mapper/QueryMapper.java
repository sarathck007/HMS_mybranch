package com.wk6.assignment4.mapper;


import org.springframework.stereotype.Component;

import com.wk6.assignment4.dto.request.QueryRequest;
import com.wk6.assignment4.dto.response.QueryResponse;
import com.wk6.assignment4.model.Query;

@Component
public class QueryMapper {
    
    public Query toEntity(QueryRequest request) {
        return Query.builder()
                .name(request.getName())
                .email(request.getEmail())
                .message(request.getMessage())
                .build();
    }
    
    public QueryResponse toResponse(Query query) {
        return QueryResponse.builder()
                .id(query.getId())
                .name(query.getName())
                .email(query.getEmail())
                .message(query.getMessage())
                .createdAt(query.getCreatedAt())
                .build();
    }
}