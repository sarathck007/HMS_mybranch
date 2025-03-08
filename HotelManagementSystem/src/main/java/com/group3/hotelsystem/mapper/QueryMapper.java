package com.group3.hotelsystem.mapper;


import org.springframework.stereotype.Component;

import com.group3.hotelsystem.dto.equest.QueryRequest;
import com.group3.hotelsystem.dto.response.QueryResponse;
import com.group3.hotelsystem.model.Query;

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