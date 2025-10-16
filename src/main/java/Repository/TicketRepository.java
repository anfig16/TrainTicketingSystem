package com.group3.ticketing.project.Repository;

import com.group3.ticketing.project.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for the Ticket entity.
 *
 * Extends JpaRepository to provide standard CRUD operations
 * and query support for Ticket objects stored in the database.
 *
 * The primary key type for Ticket is Integer (ticket number).
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	List<Ticket> findByPassenger_pId(int pId);
}
