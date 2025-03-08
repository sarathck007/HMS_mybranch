package com.group3.hotelsystem.repository;


import java.util.List;
import java.util.Map;

import com.group3.hotelsystem.model.Room;

public interface RoomRepositoryCustom {
    List<Room> findRoomsWithFilters(Map<String, Object> filters);
}