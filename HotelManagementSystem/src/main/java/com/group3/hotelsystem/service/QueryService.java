package com.group3.hotelsystem.service;


import java.util.List;

import com.group3.hotelsystem.dto.equest.QueryRequest;
import com.group3.hotelsystem.dto.response.QueryResponse;

public interface QueryService {
	 QueryResponse submitQuery(QueryRequest request, String userId);
	    List<QueryResponse> findUserQueries(String userId);
}