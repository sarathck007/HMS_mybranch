package com.wk6.assignment4.repository;


import java.util.List;
import java.util.Map;

import com.wk6.assignment4.model.Room;

public interface RoomRepositoryCustom {
    List<Room> findRoomsWithFilters(Map<String, Object> filters);
}