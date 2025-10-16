package com.group3.ticketing.project.Repository;

import com.group3.ticketing.project.Model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Train entity.
 *
 * Extends JpaRepository to provide CRUD operations
 * and query support for Train objects stored in the database.
 *
 * The primary key type for Train is Integer (train ID).
 */
@Repository
public interface TrainRepository extends JpaRepository<Train, Integer> {
}
