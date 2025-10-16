package com.group3.ticketing.project.Repository;

import com.group3.ticketing.project.Model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Passenger entity.
 * 
 * Extends JpaRepository to provide built-in CRUD operations
 * and query execution for Passenger objects.
 *
 * The primary key type for Passenger is Integer.
 */
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
	Passenger findByEmail(String email);
}
