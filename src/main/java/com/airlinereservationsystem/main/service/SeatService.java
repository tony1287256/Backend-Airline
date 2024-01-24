package com.airlinereservationsystem.main.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.airlinereservationsystem.main.model.Seat;
import com.airlinereservationsystem.main.repository.SeatRepository;

@Service
public class SeatService {

	
	@Autowired
	private SeatRepository seatRepository;

	public Seat insert(Seat seat) {
		
		return seatRepository.save(seat);
	}

//	public List<Seat> getseat(String seatNumber, int fid) {
//		// return seatRepository.getseat(seatNumber,fid);
//		return seatRepository.getSeats(seatNumber,fid);
		
//	}
	
	
//	public Seat getseat(String seatNumber, int fid) {
//        List<Seat> seats = seatRepository.getSeats(seatNumber, fid);
//        if (!seats.isEmpty()) {
//            return seats.get(0);
//        }
//        // Handle the case when no seat is found
//        return null;
//    }
	public Seat getseat(String seatNumber, int fid) {
	    List<Seat> seats = seatRepository.getSeats(seatNumber, fid);
	    System.out.println("Seats found: " + seats);
	    if (!seats.isEmpty()) {
	        return seats.get(0);
	    }
	    // Handle the case when no seat is found
	    System.out.println("No seat found for seatNumber: " + seatNumber + " and flightId: " + fid);
	    return null;
	}

	
	
	
	
    @Transactional
	public void updateseats(int id) {
		seatRepository.updateseat(id);
		
	}

	public List<?> getall(int id) {
		 
		return seatRepository.findAllbytotal(id);
	}

	public List<?> getseats(int id) {
		
		return seatRepository.findavaliable(id);
		
	}
	
	public int getavaliableseats(int id) {
		return seatRepository.countAvailableSeats(id);
	}
}