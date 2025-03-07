package com.wk6.assignment4.controller;

import com.wk6.assignment4.dto.request.QueryRequest;
import com.wk6.assignment4.dto.response.ApiResponse;
import com.wk6.assignment4.dto.response.QueryResponse;
import com.wk6.assignment4.security.SecurityUtil;
import com.wk6.assignment4.service.BookingService;
import com.wk6.assignment4.service.QueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queries")
//@RequiredArgsConstructor
public class QueryController {
    private final QueryService queryService;
    private final SecurityUtil securityUtil;
    
    @Autowired
    public QueryController(QueryService queryService, SecurityUtil securityUtil) {
        this.queryService = queryService;
        this.securityUtil = securityUtil;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<QueryResponse>> submitQuery(
            @Valid @RequestBody QueryRequest request) {
        
        String userId = securityUtil.getCurrentUserId();
        QueryResponse query = queryService.submitQuery(request, userId);
        
        return ResponseEntity.ok(
            ApiResponse.<QueryResponse>builder()
                .success(true)
                .message("Query submitted successfully")
                .data(query)
                .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<QueryResponse>>> getUserQueries() {
        String userId = securityUtil.getCurrentUserId();
        List<QueryResponse> queries = queryService.findUserQueries(userId);
        
        return ResponseEntity.ok(
            ApiResponse.<List<QueryResponse>>builder()
                .success(true)
                .message("User queries retrieved successfully")
                .data(queries)
                .build()
        );
    }
}