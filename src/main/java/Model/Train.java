package com.group3.ticketing.project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Train entity representing a train in the system.
 * Each train is uniquely identified by its train ID.
 */
@Entity
@Table(name = "train") // Maps this entity to the "train" table in the database
public class Train {

    @Id
    private int trainID; // Primary key - unique identifier for the train

    /**
     * Default constructor required by JPA.
     */
    public Train() {
    }

    /**
     * Constructor with train ID.
     *
     * @param trainID the unique ID of the train
     */
    public Train(int trainID) {
        this.trainID = trainID;
    }

    /** @return the train ID */
    public int getTrainID() {
        return trainID;
    }

    /** @param trainID sets the train ID */
    public void setTrainID(int trainID) {
        this.trainID = trainID;
    }
}
