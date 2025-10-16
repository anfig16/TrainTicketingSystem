package com.group3.ticketing.project.Repository;

import com.group3.ticketing.project.Model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Route entity.
 *
 * Extends JpaRepository to provide built-in CRUD operations
 * and query execution for Route objects.
 *
 * The primary key type for Route is String (routeName).
 */
@Repository
public interface RouteRepository extends JpaRepository<Route, String> {
}
