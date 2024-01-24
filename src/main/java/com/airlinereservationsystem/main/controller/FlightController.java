package com.airlinereservationsystem.main.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airlinereservationsystem.main.dto.FlightDto;
import com.airlinereservationsystem.main.exception.InvalidIDException;
import com.airlinereservationsystem.main.model.Airline;
import com.airlinereservationsystem.main.model.Flight;
import com.airlinereservationsystem.main.model.Route;
import com.airlinereservationsystem.main.repository.FlightRepository;
import com.airlinereservationsystem.main.service.AirlineService;
import com.airlinereservationsystem.main.service.FlightService;
import com.airlinereservationsystem.main.service.RouteService;

@RestController
@RequestMapping("/flight")
@CrossOrigin(origins = {"http://localhost:3000"})
public class FlightController {
	@Autowired
	private FlightService flightService;
	@Autowired
	private AirlineService airlineService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private FlightRepository flightRepository; 

	
	/*
	 * {
    "arrivalDate":"2023-11-19",
    "availableSeats":30,
    "businessClassPrice":22000,
    "code":"UK",
    "departureDate":"2023-11-19",
    "departureTime":"12:00",
    "economyClassPrice":15000,
    "firstClassPrice":19000,
    "source":"Pune",
    "destination":"Lucknow"
}*/
	// localhost:8081/flight/add/18
	@PostMapping("/add/{aid}")
	public ResponseEntity<?> addFlight(@PathVariable("aid") int aid, @RequestBody FlightDto flightDto) {

		try {
			Flight flight = new Flight();
			Airline airline = airlineService.getAirline(aid);
			
			flight.setAirline(airline);
			String source = flightDto.getSource();
			String destination = flightDto.getDestination();
			Route route = routeService.getidbySD(source, destination);
			flight.setRoute(route);
			flight.setCode(flightDto.getCode());
			flight.setDepartureTime(flightDto.getDepartureTime());
			flight.setDepartureDate(flightDto.getDepartureDate());
			flight.setArrivalDate(flightDto.getArrivalDate());
			flight.setEconomyClassPrice(flightDto.getEconomyClassPrice());
			flight.setFirstClassPrice(flightDto.getFirstClassPrice());
			flight.setBusinessClassPrice(flightDto.getBusinessClassPrice());
			flight.setAvailableSeats(flightDto.getAvailableSeats());
			flight = flightService.insert(flight);
			return ResponseEntity.ok().body(flight);
		} catch (InvalidIDException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/getbyairline/{aid}") // get flights by airline id
	public ResponseEntity<?> getFlight(@PathVariable("aid") int aid) {
		try {
			Airline airline = airlineService.getAirline(aid);
			List<Flight> list = flightService.getFlightsByAirline(aid);
			return ResponseEntity.ok().body(list);
		} catch (InvalidIDException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/all") // get all flights
	public List<Flight> getAllFlights(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "1000000") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return flightService.getAllFlights(pageable);
	}

//	 localhost:8081/flight/Bhopal/Chandigarh/2023-11-24
//	 [
//	    {
//	        "id": 81,
//	        "code": "I5",
//	        "departureTime": "16:00:00",
//	        "departureDate": "2023-11-24",
//	        "arrivalDate": "2023-11-24",
//	        "availableSeats": 29,
//	        "economyClassPrice": 8000.0,
//	        "firstClassPrice": 4000.0,
//	        "businessClassPrice": 12000.0,
//	        "route": {
//	            "id": 38,
//	            "departureCity": "Bhopal",
//	            "arrivalCity": "Chandigarh",
//	            "distance": 550.0,
//	            "duration": 1.8
//	        },
//	        "airline": {
//	            "id": 77,
//	            "name": "AirAsia India",
//	            "code": "I5",
//	            "user": {
//	                "id": 76,
//	                "password": "$2a$10$9Jz9tPW7P996.YjMAHqp/e15zfM30sEWWg1ii3BzWD/TIBTZ9hSB.",
//	                "username": "airasiaadmin",
//	                "role": "AIRLINE",
//	                "enabled": true,
//	                "accountNonExpired": true,
//	                "credentialsNonExpired": true,
//	                "accountNonLocked": true,
//	                "authorities": [
//	                    {
//	                        "authority": "AIRLINE"
//	                    }
//	                ]
//	            }
//	        }
//	    }
	
	@GetMapping("/{source}/{destination}/{date}") // get flights by source and destination
	public List<Flight> getFlightsbysdd(@PathVariable("source") String source,
			@PathVariable("destination") String destination,
			@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		List<Flight> list = flightService.findby(source, destination, date);
		return list;
	}
//	@GetMapping("/{source}/{destination}/{date}")
//	public List<Flight> getFlightsbysdd(
//	    @RequestParam("source") String source,
//	    @RequestParam("destination") String destination,
//	    @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//	    
//	    // Call the flightService to find flights
//	    List<Flight> list = flightService.findby(source, destination, date);
//	    
//	    // Return the list of flights
//	    return list;
//	}

		// localhost:8081/flight/2023-12-01
		@GetMapping("/{date}") // find flights by date
		public List<Flight> getByDate(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
	
			return flightService.getByDate(date);
	
		}

	@DeleteMapping("/delete/{fid}") // delete flight by flight id
	public ResponseEntity<?> deleteflight(@PathVariable("fid") int fid) {
		try {
			Flight flight = flightService.getById(fid);
			flightService.deleteFlight(fid);
			return ResponseEntity.ok().body("flight deleted successfully");
		} catch (InvalidIDException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
    // localhost:8081/flight/getbyairline/2023-11-19/18
	@GetMapping("/getbyairline/{date}/{aid}")
	public ResponseEntity<?> getflight(@PathVariable("aid") int aid,
			@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		try {
			Airline airline = airlineService.getAirline(aid);

			List<Flight> list = flightService.findBy(aid, date);

			if (list.isEmpty()) {
				return ResponseEntity.ok().body("");
			}
			return ResponseEntity.ok().body(list);
		} catch (InvalidIDException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}
    // localhost:8081/flight/update/42
	@PutMapping("/update/{id}") // update flight
	public ResponseEntity<?> updateFlight(@PathVariable("id") int id, @RequestBody Flight flight) {
		try {
			Flight flight1 = flightService.getById(id);
			if (flight.getCode() != null)
				flight1.setCode(flight.getCode());
			if (flight.getDepartureTime() != null)
				flight1.setDepartureTime(flight.getDepartureTime());
			if (flight.getDepartureDate() != null)
				flight1.setDepartureDate(flight.getDepartureDate());
			if (flight.getArrivalDate() != null)
				flight1.setArrivalDate(flight.getArrivalDate());
			if (flight.getAvailableSeats() != 0)
				flight1.setAvailableSeats(flight.getAvailableSeats());
			if (flight.getBusinessClassPrice() != 0)
				flight1.setBusinessClassPrice(flight.getBusinessClassPrice());
			if (flight.getEconomyClassPrice() != 0)
				flight1.setEconomyClassPrice(flight.getEconomyClassPrice());
			if (flight.getFirstClassPrice() != 0)
				flight1.setFirstClassPrice(flight.getFirstClassPrice());
			// Save the updated flight to the service
			flight1 = flightService.insert(flight1);

			return ResponseEntity.ok().body(flight1);
		} catch (InvalidIDException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	
	//localhost:8081/flight/get/filter?airline=Indigo
	 @GetMapping("/get/filter")
	    public List<Flight> getflightsWithFilters(@RequestParam(required = false) String airline) {
	        return flightRepository.findAll().stream()
	        		.filter(flight -> airline == null || (flight.getAirline() != null && flight.getAirline().getName().equals(airline)))
	                .collect(Collectors.toList());
	    }
	 
	 
	 
	
	
	
	
	
	
	
	
	
	
	
}
