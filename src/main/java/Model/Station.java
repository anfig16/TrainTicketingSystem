package com.group3.ticketing.project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Station entity representing a train station.
 * Each station is uniquely identified by its name.
 */
@Entity
@Table(name = "station") // Maps this entity to the "station" table in the database
public class Station {

    @Id
    private String stationName; // Primary key - unique name of the station

    /**
     * Default constructor required by JPA.
     */
    public Station() {
    }

    /**
     * Constructor with station name.
     *
     * @param stationName the unique name of the station
     */
    public Station(String stationName) {
        this.stationName = stationName;
    }

    /** @return the station name */
    public String getStationName() {
        return stationName;
    }

    /** @param stationName sets the station name */
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
