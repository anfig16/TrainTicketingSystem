package com.group3.ticketing.project.Controller;

import com.group3.ticketing.project.Model.Seats;
import com.group3.ticketing.project.Model.Train;
import com.group3.ticketing.project.Repository.SeatsRepository;
import com.group3.ticketing.project.Repository.TrainRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling seat-related requests.
 * Provides endpoints for creating, retrieving, updating, and deleting seats.
 */
@RestController
@RequestMapping("/seats")
public class SeatsController {

	@Autowired
	private SeatsRepository seatsRepository;
	@Autowired
	private TrainRepository trainRepository;

	/**
	 * Retrieve all seats.
	 * 
	 * @return List of all seats.
	 */
	@GetMapping("/getAll")
	public List<Seats> getAllSeats() {
		return seatsRepository.findAll();
	}

	/**
	 * Retrieve a seat by its ID.
	 *
	 * @param id The seat ID.
	 * @return Optional containing the seat if found, otherwise empty.
	 */
	@GetMapping("/{id}")
	public Optional<Seats> getSeatById(@PathVariable Long id) {
		return seatsRepository.findById(id);
	}

	/**
	 * Create a new seat.
	 *
	 * @param seat The seat object to create.
	 * @return The saved seat object.
	 */
	@PostMapping("/createSeat")
	public Seats createSeat(@RequestBody Seats seat) {
		return seatsRepository.save(seat);
	}

	/**
	 * Update an existing seat.
	 *
	 * @param seatNo The ID of the seat to update.
	 * @param updatedSeat The updated seat details.
	 * @return The updated seat.
	 */
	@PutMapping("/{seatNo}")
	public Seats updateSeat(@PathVariable Long seatNo, @RequestBody Seats updatedSeat) {

		// 1. Fetch old seat
		Seats oldSeat = seatsRepository.findById(seatNo).orElseThrow(() -> new RuntimeException("Seat not found"));

		// 2. Delete old seat
		seatsRepository.delete(oldSeat);

		// 3. Create new seat
		Seats newSeat = new Seats();

		// Update seatNo only if provided, else keep old
		newSeat.setSeatNo((Integer)updatedSeat.getSeatNo() != null ? updatedSeat.getSeatNo() : oldSeat.getSeatNo());

		// Update train if provided
		newSeat.setTrain(updatedSeat.getTrain() != null ? updatedSeat.getTrain() : oldSeat.getTrain());

		// Update seatAvailable if provided
		newSeat.setSeatAvailable((Boolean)
				updatedSeat.isSeatAvailable() != null ? updatedSeat.isSeatAvailable() : oldSeat.isSeatAvailable());

		// 4. Save new seat
		return seatsRepository.save(newSeat);
	}

	/**
	 * Delete a seat by its ID.
	 *
	 * @param id The seat ID.
	 * @return Confirmation message.
	 */
	@DeleteMapping("/{id}")
	public String deleteSeat(@PathVariable Long id) {
		seatsRepository.deleteById(id);
		return "Seat deleted: " + id;
	}
	
	/*Retrieve seats for certain trainId*/
	@GetMapping("/train/{trainId}")
    public List<Seats> getAvailableSeatsByTrain(@PathVariable int trainId) {
		Train train = trainRepository.findById(trainId)
	            .orElseThrow(() -> new RuntimeException("Train not found"));
        return seatsRepository.findByTrain(train);
    }
}
