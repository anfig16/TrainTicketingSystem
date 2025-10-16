package com.group3.ticketing.project.Controller;

import com.group3.ticketing.project.Model.Passenger;
import com.group3.ticketing.project.Repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

/**
 * REST controller that handles CRUD operations for Passenger entities.
 * 
 * Provides endpoints to:
 * - Retrieve all passengers
 * - Retrieve a passenger by ID
 * - Create a new passenger
 * - Update passenger details
 * - Delete a passenger by ID
 */
@RestController
@RequestMapping("/passengers") // Base URL for all passenger-related endpoints
public class PassengerController {

    @Autowired
    private PassengerRepository passengerRepository; // Injects PassengerRepository to interact with the database
    
    /**
     * Retrieves all passengers from the database.
     *
     * @return A list of all Passenger objects.
     */
    @GetMapping("/getAll")
    public List<Passenger> getAllPassengers() {
        // Calls repository method to return all passengers
        return passengerRepository.findAll();
    }

    /**
     * Retrieves a passenger by their ID.
     *
     * @param id The ID of the passenger to retrieve.
     * @return An Optional containing the Passenger if found, or empty if not.
     */
    @GetMapping("/{id}")
    public Optional<Passenger> getPassengerById(@PathVariable int id) {
        // Searches for a passenger by ID
        return passengerRepository.findById(id);
    }

    /**
     * Creates a new passenger record in the database.
     *
     * @param The passenger object sent in the request body and the session info.
     * @return Whether the passenger was successfully saved to database.
     */
    @PostMapping("/createPassenger")
    public ResponseEntity<String> createPassenger(@RequestBody Passenger passenger, HttpSession session) {
        String email = passenger.getEmail();
        
        //Check if passenger already exists
        if (passengerRepository.findByEmail(email) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("An account already exists with that email.");
        }
        
    	//Saves the new passenger entity to the database
        passengerRepository.save(passenger);
        
        //Set session attributes
        session.setAttribute("passengerName", passenger.getpName());
        session.setAttribute("passengerEmail", passenger.getEmail());

        return ResponseEntity.ok("Signup successful");
    }

    /**
     * Logins the current passenger 
     */
    @PostMapping("/login")
    public ResponseEntity<String> loginPassenger(@RequestBody Passenger loginPassenger, HttpSession session) {
        String email = loginPassenger.getEmail();
        String passcode = loginPassenger.getPasscode();

        Passenger passenger = passengerRepository.findByEmail(email);
        
        //Checks if passenger is stored in database and verifies login info
        if (passenger == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Account not found. Would you like to sign up?");
        }

        if (!passenger.getPasscode().equals(passcode)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect email or password.");
        }

        //Store session info
        session.setAttribute("passengerEmail", email);
        session.setAttribute("passengerName", passenger.getpName());

        return ResponseEntity.ok("Login successful");
    }
    
    /**
     * Logouts the current passenger 
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logoutPassenger(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }
    
    /**
     * Checks if a passenger is logged-in
     */
    @GetMapping("/checkSession")
    public ResponseEntity<Map<String, Object>> checkSession(HttpSession session) {
        String email = (String) session.getAttribute("passengerEmail");
        String name = null;

        //Retrieves logged-in passenger account info
        if (email != null) {
            Passenger passenger = passengerRepository.findByEmail(email);
            if (passenger != null) {
                name = passenger.getpName();
            }
        }
        
        //Saves the retrieved logged-in passenger account info
        Map<String, Object> result = new HashMap<>();
        result.put("loggedIn", email != null);
        if (email != null) {
            result.put("email", email);
            result.put("name", name);
        }

        return ResponseEntity.ok(result);
    }
    
    /**
     * Retrieves the current logged-in passenger as an object
     */
    @GetMapping("/sessionPassenger")
    public ResponseEntity<Passenger> getPassengerFromSession(HttpSession session) {
        //Retrieves session email to check if a passenger is logged-in
    	String email = (String) session.getAttribute("passengerEmail");
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        //Creates passenger object of current logged-in object and returns it
        Passenger passenger = passengerRepository.findByEmail(email);
        if (passenger == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(passenger);
    }
    
    
    /**
     * Updates current passenger info
     */
    @PatchMapping("/updateCurrentPassenger")
    public ResponseEntity<String> updateCurrentPassenger(@RequestBody Passenger updatedPassenger, HttpSession session) {
        //Retrieves current logged-in passenger
    	String email = (String) session.getAttribute("passengerEmail");
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        Passenger passenger = passengerRepository.findByEmail(email);
        if (passenger == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Passenger not found");
        }
        
        //Updates passenger columns if needed
        if (updatedPassenger.getpName() != null && !updatedPassenger.getpName().isEmpty())
            passenger.setpName(updatedPassenger.getpName());
        if (updatedPassenger.getEmail() != null && !updatedPassenger.getEmail().isEmpty())
            passenger.setEmail(updatedPassenger.getEmail());
        if (updatedPassenger.getPhone() != null && !updatedPassenger.getPhone().isEmpty())
            passenger.setPhone(updatedPassenger.getPhone());
        if (updatedPassenger.getPasscode() != null && !updatedPassenger.getPasscode().isEmpty())
            passenger.setPasscode(updatedPassenger.getPasscode());

        //Save updated passenger in database
        passengerRepository.save(passenger);

        //Update session email and name if it was changed
        if (updatedPassenger.getEmail() != null) {
            session.setAttribute("passengerEmail", updatedPassenger.getEmail());
        }
        if (updatedPassenger.getpName() != null) {
            session.setAttribute("passengerName", updatedPassenger.getpName());
        }

        return ResponseEntity.ok("Passenger updated successfully");
    }
    
    
    /**
     * Deletes current passenger from database and session
     */
    
    @DeleteMapping("/deleteCurrentPassenger")
    public ResponseEntity<String> deleteCurrentPassenger(HttpSession session) {
    	//Retrieves current logged-in passenger
        String email = (String) session.getAttribute("passengerEmail");
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        Passenger passenger = passengerRepository.findByEmail(email);
        if (passenger == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Passenger not found");
        }
        
        //Deletes passenger from database and session
        passengerRepository.delete(passenger);
        session.invalidate();
        return ResponseEntity.ok("Passenger deleted successfully");
    }
    
    
    
    
    
    /**
     * Updates an existing passenger’s details.
     *
     * @param id The ID of the passenger to update.
     * @param passengerDetails The updated passenger information sent in the request body.
     * @return The updated Passenger object after saving changes.
     */
    @PutMapping("/{id}")
    public Passenger updatePassenger(@PathVariable int id, @RequestBody Passenger passengerDetails) {
        // Retrieve passenger or throw an exception if not found
        Passenger passenger = passengerRepository.findById(id).orElseThrow();

        // Update passenger name if provided
        if (passengerDetails.getpName() != null && !passengerDetails.getpName().isEmpty()) {
            passenger.setpName(passengerDetails.getpName());
        }

        // Update passenger email if provided
        if (passengerDetails.getEmail() != null && !passengerDetails.getEmail().isEmpty()) {
            passenger.setEmail(passengerDetails.getEmail());
        }
        
        // Update passenger passcode if provided
        if (passengerDetails.getPasscode() != null && !passengerDetails.getPasscode().isEmpty()) {
            passenger.setPasscode(passengerDetails.getPasscode());
        }

        // Update passenger phone if provided
        if (passengerDetails.getPhone() != null && !passengerDetails.getPhone().isEmpty()) {
            passenger.setPhone(passengerDetails.getPhone());
        }

        // Save and return the updated passenger
        return passengerRepository.save(passenger);
    }

    /**
     * Deletes a passenger by their ID.
     *
     * @param id The ID of the passenger to delete.
     * @return A confirmation message indicating the passenger was deleted.
     */
    @DeleteMapping("/{id}")
    public String deletePassenger(@PathVariable int id) {
        // Deletes passenger record by ID
        passengerRepository.deleteById(id);
        return "Passenger deleted: " + id;
    }
}
