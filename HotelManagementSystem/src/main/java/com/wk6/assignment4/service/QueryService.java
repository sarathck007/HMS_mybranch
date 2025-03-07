package com.wk6.assignment4.service;


import java.util.List;

import com.wk6.assignment4.dto.request.QueryRequest;
import com.wk6.assignment4.dto.response.QueryResponse;

public interface QueryService {
	 QueryResponse submitQuery(QueryRequest request, String userId);
	    List<QueryResponse> findUserQueries(String userId);
}