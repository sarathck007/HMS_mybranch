package com.wk6.assignment4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wk6.assignment4.model.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String>, RoomRepositoryCustom {

	@Query("""
		    SELECT DISTINCT r FROM Room r
		    WHERE r.startDate <= :checkOutDate 
		    AND r.endDate >= :checkInDate
		    AND r.maxAdults >= :adults 
		    AND r.maxChildren >= :children
		    AND r NOT IN (
		        SELECT b.room FROM Booking b 
		        WHERE b.status NOT IN ('CANCELLED', 'CHECKED_OUT')
		        AND NOT (b.checkOutDate <= :checkInDate OR b.checkInDate >= :checkOutDate)
		    )
		""")
		List<Room> findAvailableRooms(LocalDateTime checkInDate, LocalDateTime checkOutDate, int adults, int children);

	List<Room> findByAdmin_AdminId(String adminId);

	@Query("SELECT r FROM Room r WHERE r.discountedPrice IS NOT NULL AND r.discountedPrice > 0")
	List<Room> findRoomsWithDiscount();
}