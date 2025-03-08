package com.group3.hotelsystem.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.group3.hotelsystem.model.Room;

public class RoomRepositoryCustomImpl implements RoomRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Room> findRoomsWithFilters(Map<String, Object> filters) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Room> query = cb.createQuery(Room.class);
		Root<Room> room = query.from(Room.class);

		List<Predicate> predicates = new ArrayList<>();

		if (filters.containsKey("minPrice")) {
			predicates.add(cb.greaterThanOrEqualTo(room.get("actualPrice"), (BigDecimal) filters.get("minPrice")));
		}

		if (filters.containsKey("maxPrice")) {
			predicates.add(cb.lessThanOrEqualTo(room.get("actualPrice"), (BigDecimal) filters.get("maxPrice")));
		}

		if (filters.containsKey("maxAdults")) {
			predicates.add(cb.greaterThanOrEqualTo(room.get("maxAdults"), (Integer) filters.get("maxAdults")));
		}

		if (filters.containsKey("features")) {
			String features = (String) filters.get("features");
			predicates.add(cb.like(room.get("features"), "%" + features + "%"));
		}

		query.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(query).getResultList();
	}
}