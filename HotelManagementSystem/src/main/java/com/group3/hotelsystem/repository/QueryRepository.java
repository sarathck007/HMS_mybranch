package com.group3.hotelsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group3.hotelsystem.model.Query;

import java.util.List;

@Repository
public interface QueryRepository extends JpaRepository<Query, Long> {
    List<Query> findByUser_UserId(String userId);
    List<Query> findByUser_UserIdOrderByCreatedAtDesc(String userId);
}
