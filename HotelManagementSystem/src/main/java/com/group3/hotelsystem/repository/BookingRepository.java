package com.group3.hotelsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.group3.hotelsystem.model.Booking;
import com.group3.hotelsystem.model.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

	List<Booking> findByUser_UserId(String userId);

	List<Booking> findByRoom_RoomId(String roomId);

	List<Booking> findByStatus(BookingStatus status);

	@Query("""
			    SELECT b FROM Booking b
			    WHERE b.checkInDate >= :startDate
			    AND b.checkInDate <= :endDate
			""")
	List<Booking> findBookingsInDateRange(LocalDateTime startDate, LocalDateTime endDate);

	@Query("""
	        SELECT new map(
	            FUNCTION('DATE_FORMAT', b.checkInDate, '%Y-%m') as month, 
	            SUM(b.totalPrice) as income
	        ) 
	        FROM Booking b 
	        WHERE b.status NOT IN ('CANCELLED')
	        GROUP BY FUNCTION('DATE_FORMAT', b.checkInDate, '%Y-%m')
	        ORDER BY month
	    """)
	    List<Map<String, Object>> getMonthlyIncome();

	@Query("""
			    SELECT COUNT(b)
			    FROM Booking b
			    WHERE b.status = 'CONFIRMED'
			    AND b.room.roomId = :roomId
			    AND ((b.checkInDate BETWEEN :startDate AND :endDate)
			    OR (b.checkOutDate BETWEEN :startDate AND :endDate))
			""")
	long countOverlappingBookings(String roomId, LocalDateTime startDate, LocalDateTime endDate);
}