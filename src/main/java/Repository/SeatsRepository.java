package com.group3.ticketing.project.Repository;

import com.group3.ticketing.project.Model.Seats;
import com.group3.ticketing.project.Model.Train;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for the Seats entity.
 *
 * Extends JpaRepository to provide built-in CRUD operations
 * and query execution for Seats objects.
 *
 * The primary key type for Seats is Long (seat number).
 */
@Repository
public interface SeatsRepository extends JpaRepository<Seats, Long> {
	List<Seats> findByTrain(Train trainId);
}
