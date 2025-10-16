package com.group3.ticketing.project.Controller;

import com.group3.ticketing.project.Model.Route;
import com.group3.ticketing.project.Repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for handling REST API requests related to Routes.
 * 
 * Provides CRUD operations such as retrieving all routes,
 * retrieving a route by ID, creating new routes, updating existing routes,
 * and deleting routes.
 */
@RestController
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    private RouteRepository routeRepository;

    /**
     * Retrieve all available routes.
     *
     * @return List of all routes in the system.
     */
    @GetMapping("/getAll")
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    /**
     * Retrieve a route by its ID.
     *
     * @param id The unique ID (route name) of the route.
     * @return An Optional containing the route if found, or empty if not found.
     */
    @GetMapping("/{id}")
    public Optional<Route> getRouteById(@PathVariable String id) {
        return routeRepository.findById(id);
    }

    /**
     * Create a new route in the system.
     *
     * @param route The route object to be created.
     * @return The newly saved route object.
     */
    @PostMapping("/createRoute")
    public Route createRoute(@RequestBody Route route) {
        return routeRepository.save(route);
    }

    /**
     * Update an existing route.
     *
     * The old route (identified by its name) is first deleted,
     * then a new route is created using updated details.
     *
     * @param oldRouteName The ID (name) of the route to update.
     * @param updatedRoute The route object containing updated details.
     * @return The newly updated route.
     */
    @PutMapping("/{oldRouteName}")
    public Route updateRoute(@PathVariable String oldRouteName, @RequestBody Route updatedRoute) {

        // 1. Fetch the old route (or throw error if not found)
        Route oldRoute = routeRepository.findById(oldRouteName)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        // 2. Delete old route before replacing it
        routeRepository.delete(oldRoute);

        // 3. Create a new Route object to replace the old one
        Route newRoute = new Route();

        // Copy fields from updatedRoute if provided, otherwise keep old values
        newRoute.setRouteName(
                updatedRoute.getRouteName() != null ? updatedRoute.getRouteName() : oldRoute.getRouteName()
        );
        newRoute.setStartDest(
                updatedRoute.getStartDest() != null ? updatedRoute.getStartDest() : oldRoute.getStartDest()
        );
        newRoute.setEndDest(
                updatedRoute.getEndDest() != null ? updatedRoute.getEndDest() : oldRoute.getEndDest()
        );
        newRoute.setDate(
                updatedRoute.getDate() != null ? updatedRoute.getDate() : oldRoute.getDate()
        );
        newRoute.setTrain(
                updatedRoute.getTrain() != null ? updatedRoute.getTrain() : oldRoute.getTrain()
        );

        // 4. Save the new route into the repository
        return routeRepository.save(newRoute);
    }

    /**
     * Delete a route by its ID.
     *
     * @param id The unique ID (route name) of the route to delete.
     * @return A confirmation message after deletion.
     */
    @DeleteMapping("/{id}")
    public String deleteRoute(@PathVariable String id) {
        routeRepository.deleteById(id);
        return "Route deleted: " + id;
    }
}
