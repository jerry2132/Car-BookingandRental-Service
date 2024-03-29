package com.example.service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.Entity.MyBooking;
import com.example.Entity.User;
import com.example.Entity.Vehicle;
import com.example.Enums.BookCarStatus;
import com.example.repository.BookingRepository;
import com.example.repository.UserRepository;
import com.example.repository.VehicleRepository;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private VehicleRepository vehicleReository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private MyBooking myBooking;

	@Override
	public String saveBookedVehicle(MyBooking myBooking, Long vehicleId, Long userId, Model model) {
		// TODO Auto-generated method stub

		Optional<Vehicle> vehicleOptional = vehicleReository.findById(vehicleId);

		Optional<User> userOptional = userRepository.findById(userId);

		if (userOptional.isPresent() && vehicleOptional.isPresent()) {

			Vehicle vehicle = vehicleOptional.get();

			User user = userOptional.get();

			long daysBetween = calculateDaysBetween(myBooking.getFromDate(), myBooking.getToDate());

			System.out.println(daysBetween);

			myBooking.setTotalDays(daysBetween);

			myBooking.setPrice(vehicle.getPrice() * daysBetween);

			myBooking.setVehicle(vehicle);

			myBooking.setUser(user);

			myBooking.setBookCarStatus(BookCarStatus.PENDING);

			// myBooking.setFromDate(null);

			bookingRepository.save(myBooking);

			model.addAttribute("successMessage", "Vehicle Booked successfully");

			return "user/booking";

		} else {

			model.addAttribute("errorMessage", "Error while booking");
			return "user/booking";
		}

	}

	public static long calculateDaysBetween(Date startDate, Date endDate) {
		// Convert java.util.Date to java.time.LocalDate
		java.time.LocalDate startLocalDate = startDate.toInstant().atZone(java.time.ZoneId.systemDefault())
				.toLocalDate();
		java.time.LocalDate endLocalDate = endDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

		// Calculate the number of days between two dates
		return ChronoUnit.DAYS.between(startLocalDate, endLocalDate);
	}

	@Override
	public List<MyBooking> getBookingsForCurrentUser(Long currentUserId) {
		// TODO Auto-generated method stub
		return bookingRepository.findByUserId(currentUserId);
	}

	@Override
	public List<MyBooking> getAllBookings() {
		// TODO Auto-generated method stub
		return bookingRepository.findAll();
	}

	@Override
	public String approveBooking(Long userId, Model model) {
		// TODO Auto-generated method stub

		Optional<User> userOptional = userRepository.findById(userId);

		if (userOptional.isPresent()) {

			User user = userOptional.get();

			List<MyBooking> myBookingList = bookingRepository.findByUserId(userId);

			for (MyBooking booking : myBookingList) {

				User bookingUser = booking.getUser();
				// Long id = bookingUser.getId();

				if (user.getId().equals(bookingUser.getId())) {

					booking.setBookCarStatus(BookCarStatus.APPROVED);

					bookingRepository.save(booking);

					model.addAttribute("successMessage", "success");

					return "admin/bookings";
				}

			}
		}

		model.addAttribute("errorMessage", "error while changing");

		return "admin/bookings";
	}

	@Override
	public String rejectBooking(Long userId, Model model) {
		// TODO Auto-generated method stub
		Optional<User> userOptional = userRepository.findById(userId);

		if (userOptional.isPresent()) {

			User user = userOptional.get();

			List<MyBooking> myBookingList = bookingRepository.findByUserId(userId);

			for (MyBooking booking : myBookingList) {

				User bookingUser = booking.getUser();
				// Long id = bookingUser.getId();

				if (user.getId().equals(bookingUser.getId())) {

					booking.setBookCarStatus(BookCarStatus.REJECTED);

					bookingRepository.save(booking);

					model.addAttribute("successMessage", "success");

					return "redirect:/admin/bookings";
				}

			}
		}

		model.addAttribute("errorMessage", "error while changing");

		return "admin/bookings";
	}

}
