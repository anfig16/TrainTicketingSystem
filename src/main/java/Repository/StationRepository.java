package com.group3.ticketing.project.Repository;

import com.group3.ticketing.project.Model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Station entity.
 *
 * Extends JpaRepository to provide CRUD operations and query support
 * for Station objects stored in the database.
 *
 * The primary key type for Station is String (station name).
 */
@Repository
public interface StationRepository extends JpaRepository<Station, String> {
}
