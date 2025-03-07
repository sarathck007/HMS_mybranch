package com.wk6.assignment4.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wk6.assignment4.dto.request.QueryRequest;
import com.wk6.assignment4.dto.response.QueryResponse;
import com.wk6.assignment4.exception.ResourceNotFoundException;
import com.wk6.assignment4.mapper.QueryMapper;
import com.wk6.assignment4.model.Query;
import com.wk6.assignment4.model.User;
import com.wk6.assignment4.repository.QueryRepository;
import com.wk6.assignment4.repository.UserRepository;
import com.wk6.assignment4.service.QueryService;
import com.wk6.assignment4.util.Constants;
import com.wk6.assignment4.util.StringUtil;
import com.wk6.assignment4.util.ValidationUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional

public class QueryServiceImpl implements QueryService {
	private final QueryRepository queryRepository;
	private final UserRepository userRepository;
	private final QueryMapper queryMapper;

	@Autowired
	public QueryServiceImpl(QueryRepository queryRepository, UserRepository userRepository, QueryMapper queryMapper) {
		this.queryRepository = queryRepository;
		this.userRepository = userRepository;
		this.queryMapper = queryMapper;
	}

	@Override
	public QueryResponse submitQuery(QueryRequest request, String userId) {
		if (!ValidationUtil.isValidEmail(request.getEmail())) {
			throw new IllegalArgumentException("Invalid email format");
		}

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));

		Query query = queryMapper.toEntity(request);
		query.setUser(user);
		query.setEmail(request.getEmail().toLowerCase());
		query.setMessage(StringUtil.sanitizeString(request.getMessage()));

		return queryMapper.toResponse(queryRepository.save(query));
	}

	@Override
	public List<QueryResponse> findUserQueries(String userId) {
		if (!userRepository.existsById(userId)) {
			throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
		}

		return queryRepository.findByUser_UserIdOrderByCreatedAtDesc(userId).stream().map(queryMapper::toResponse)
				.collect(Collectors.toList());
	}
}