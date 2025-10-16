package com.group3.ticketing.project.Controller;

import com.group3.ticketing.project.Model.Station;
import com.group3.ticketing.project.Repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling station-related requests.
 * Provides endpoints for creating, retrieving, updating, and deleting stations.
 */
@RestController
@RequestMapping("/stations")
public class StationController {

    @Autowired
    private StationRepository stationRepository;

    /**
     * Retrieve all stations.
     *
     * @return List of all stations.
     */
    @GetMapping("/getAll")
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    /**
     * Retrieve a station by its ID.
     *
     * @param id The station ID.
     * @return Optional containing the station if found, otherwise empty.
     */
    @GetMapping("/{id}")
    public Optional<Station> getStationById(@PathVariable String id) {
        return stationRepository.findById(id);
    }

    /**
     * Create a new station.
     *
     * @param station The station object to create.
     * @return The saved station object.
     */
    @PostMapping("/createStation")
    public Station createStation(@RequestBody Station station) {
        return stationRepository.save(station);
    }

    /**
     * Update an existing station.
     *
     * @param oldStationName The ID of the station to update.
     * @param updatedStation The updated station details.
     * @return The updated station object.
     */
    @PutMapping("/{oldStationName}")
    public Station updateStation(@PathVariable String oldStationName, @RequestBody Station updatedStation) {

        // 1. Fetch the old station (or throw error if not found)
        Station oldStation = stationRepository.findById(oldStationName)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        // 2. Delete the old station before replacing it
        stationRepository.delete(oldStation);

        // 3. Create a new station object
        Station newStation = new Station();

        // Use updated station name if provided, else keep old name
        newStation.setStationName(
                updatedStation.getStationName() != null ? updatedStation.getStationName() : oldStation.getStationName()
        );

        // 4. Save the new station into the repository
        return stationRepository.save(newStation);
    }

    /**
     * Delete a station by its ID.
     *
     * @param id The station ID.
     * @return Confirmation message after deletion.
     */
    @DeleteMapping("/{id}")
    public String deleteStation(@PathVariable String id) {
        stationRepository.deleteById(id);
        return "Station deleted: " + id;
    }
}
