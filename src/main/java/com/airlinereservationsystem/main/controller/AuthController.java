package com.airlinereservationsystem.main.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airlinereservationsystem.main.model.Airline;
import com.airlinereservationsystem.main.model.Customer;
import com.airlinereservationsystem.main.model.Executive;
import com.airlinereservationsystem.main.model.User;
import com.airlinereservationsystem.main.service.AirlineService;
import com.airlinereservationsystem.main.service.CustomerFlightService;
import com.airlinereservationsystem.main.service.CustomerService;
import com.airlinereservationsystem.main.service.ExecutiveService;
import com.airlinereservationsystem.main.service.UserService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerFlightService customerFlightService;
	
	@Autowired
	private ExecutiveService executiveService;

	@Autowired
	private AirlineService airlineService;

	@PostMapping("/user/login")
	public User login(Principal  principal) {  
		String username = principal.getName();
		User user = (User)userService.loadUserByUsername(username);
		System.out.println("msg");
		return user; 

}
	
	
//	
//	@PostMapping("/user/login")
//	public ResponseEntity<?> userLogin(Principal principal) {
//	    String username = principal.getName();
//	    User user = (User) userService.loadUserByUsername(username);
//
//	    if (user != null) {
//	        switch (user.getRole()) {
//	            case CUSTOMER:
//	                // Handle login for CUSTOMER role
//	                Optional<Customer> customer = customerService.getByUserId(user.getId());
//	                return ResponseEntity.ok(customer);
//	            case AIRLINE:
//	                // Handle login for AIRLINE role
//	                Optional<Airline> airline = airlineService.getByUserId(user.getId());
//	                return ResponseEntity.ok(airline);
//	            case EXECUTIVE:
//	                // Handle login for EXECUTIVE role
//	                Optional<Executive> executive = executiveService.getByUserId(user.getId());
//	                return ResponseEntity.ok(executive);
//	            default:
//	                // Handle other roles or provide an error response
//	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unknown user role");
//	        }
//	    }
//
//	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
//	}
	
	/* Display all the passengers that have booked ticket in on a given date in any airline. 
take date as path variable */
	
	
//	@GetMapping("customer/{date}")
//	public List<?> getCustomersByDate(@PathVariable("date")LocalDate date) {
//		 List<?> customerFlight = customerFlightService.getCusomersByDate(date);
//		return customerFlight;
//	}
}