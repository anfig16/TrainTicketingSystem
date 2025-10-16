package com.group3.ticketing.project.Controller;

import com.group3.ticketing.project.Model.*;
import com.group3.ticketing.project.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling ticket-related requests.
 * Provides endpoints for creating, retrieving, updating, and deleting tickets.
 */
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private SeatsRepository seatsRepository;
    @Autowired
    private RouteRepository routeRepository;

    /**
     * Retrieve all tickets.
     *
     * @return List of all tickets.
     */
    @GetMapping("/getAll")
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    /**
     * Retrieve a ticket by its ID.
     *
     * @param id The ticket ID.
     * @return Optional containing the ticket if found, otherwise empty.
     */
    @GetMapping("/{id}")
    public Optional<Ticket> getTicketById(@PathVariable int id) {
        return ticketRepository.findById(id);
    }

    /**
     * Create a new ticket.
     *
     * @param ticket The ticket object to create.
     * @return The saved ticket object.
     */
    @PostMapping("/createTicket")
    public Ticket createTicket(@RequestBody Ticket ticket) {
    	//Check that passenger, seat, and route exist before storing ticket
    	Passenger passenger = passengerRepository.findById(ticket.getPassenger().getpId())
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        Seats seat = seatsRepository.findById((long) ticket.getSeat().getSeatNo())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        Route route = routeRepository.findById(ticket.getRoute().getRouteName())
                .orElseThrow(() -> new RuntimeException("Route not found"));
        
        //Mark seat as taken
        seat.setSeatAvailable(false);
        seatsRepository.save(seat);
        
        //Create and save new ticket
        Ticket newTicket = new Ticket();
        newTicket.setDate(ticket.getDate());
        newTicket.setPrice(ticket.getPrice());
        newTicket.setPassenger(passenger);
        newTicket.setSeat(seat);
        newTicket.setRoute(route);
        
        return ticketRepository.save(newTicket);
    }

    /**
     * Update an existing ticket.
     * Uses PATCH since updates may only involve certain fields.
     *
     * @param id The ID of the ticket to update.
     * @param ticketDetails The ticket object containing updated details.
     * @return The updated ticket.
     */
    @PatchMapping("/{id}")
    public Ticket updateTicket(@PathVariable int id, @RequestBody Ticket ticketDetails) {
        // Find the existing ticket or throw an error if not found
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // Update fields only if they are provided (non-null)
        if (ticketDetails.getDate() != null) {
            ticket.setDate(ticketDetails.getDate());
        }

        if ((Double)ticketDetails.getPrice() != null) {
            ticket.setPrice(ticketDetails.getPrice());
        }

        if (ticketDetails.getPassenger() != null) {
            ticket.setPassenger(ticketDetails.getPassenger());
        }

        if (ticketDetails.getSeat() != null) {
            ticket.setSeat(ticketDetails.getSeat());
        }

        // Save and return the updated ticket
        return ticketRepository.save(ticket);
    }

    /**
     * Delete a ticket by its ID.
     *
     * @param id The ticket ID.
     * @return Confirmation message after deletion.
     */
    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable int id) {
    	Optional<Ticket> ticketOpt = ticketRepository.findById(id);
    	Ticket ticket = ticketOpt.get();
    	Seats seat = ticket.getSeat();
        if (seat != null) {
            seat.setSeatAvailable(true);
            seatsRepository.save(seat);
        }
        ticketRepository.deleteById(id);
        return "Ticket deleted: " + id;
    }
    
    /*Get all tickets for a specific passenger*/
    @GetMapping("/passenger/{pId}")
    public List<Ticket> getTicketsByPassenger(@PathVariable int pId) {
        return ticketRepository.findByPassenger_pId(pId);
    }
}
