package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.Entity.MyBooking;

@Service
public interface BookingService {

	
	public String saveBookedVehicle(MyBooking myBooking ,Long vehicleId , Long userId,Model model);
	

    public List<MyBooking> getBookingsForCurrentUser(Long currentUserId);
    
    public List<MyBooking> getAllBookings();
    
    public String approveBooking(Long userId, Model model);
    
   public String rejectBooking(Long userId, Model model);
}
