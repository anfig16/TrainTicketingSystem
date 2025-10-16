package com.group3.ticketing.project.Controller;

import com.group3.ticketing.project.Model.Train;
import com.group3.ticketing.project.Repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class that handles HTTP requests related to Train entities.
 * Provides endpoints for creating, reading, updating, and deleting trains.
 */
@RestController
@RequestMapping("/trains")
public class TrainController {

    @Autowired
    private TrainRepository trainRepository;

    /**
     * Retrieves all trains from the database.
     *
     * @return a list of all Train objects
     */
    @GetMapping("/getAll")
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    /**
     * Retrieves a specific train by its ID.
     *
     * @param id the ID of the train
     * @return an Optional containing the Train if found, or empty if not found
     */
    @GetMapping("/{id}")
    public Optional<Train> getTrainById(@PathVariable int id) {
        return trainRepository.findById(id);
    }

    /**
     * Creates a new Train in the database.
     *
     * @param train the Train object to be created
     * @return the saved Train object
     */
    @PostMapping("/createTrain")
    public Train createTrain(@RequestBody Train train) {
        return trainRepository.save(train);
    }

    /**
     * Updates an existing train with new details.
     * Steps:
     * 1. Fetch the existing train by ID.
     * 2. Delete the old train entry.
     * 3. Create a new train object with updated fields.
     * 4. Save the new train to the database.
     *
     * @param id the ID of the train to update
     * @param trainDetails the new details of the train
     * @return the updated Train object
     */
    @PutMapping("/{id}")
    public Train updateTrain(@PathVariable int id, @RequestBody Train trainDetails) {
        // Step 1: Fetch the old train
        Train oldTrain = trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        // Step 2: Delete the old train
        trainRepository.delete(oldTrain);

        // Step 3: Create a new train instance
        Train newTrain = new Train();

        // Set TrainID from request if provided, else use old TrainID
        newTrain.setTrainID(trainDetails.getTrainID() != 0 ? trainDetails.getTrainID() : oldTrain.getTrainID());

        // Example: Add other fields if Train has them
        // newTrain.setName(trainDetails.getName() != null ? trainDetails.getName() : oldTrain.getName());

        // Step 4: Save and return the new train
        return trainRepository.save(newTrain);
    }

    /**
     * Deletes a train by its ID.
     *
     * @param id the ID of the train to delete
     * @return a confirmation message with the deleted train's ID
     */
    @DeleteMapping("/{id}")
    public String deleteTrain(@PathVariable int id) {
        trainRepository.deleteById(id);
        return "Train deleted: " + id;
    }
}
