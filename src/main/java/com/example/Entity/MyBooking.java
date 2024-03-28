package com.example.Entity;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.example.Enums.BookCarStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Component
@Entity
public class MyBooking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fromDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date toDate;

	private Long totalDays;

	private Long price;

	private BookCarStatus bookCarStatus;

	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;

	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Vehicle vehicle;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(Long totalDays) {
		this.totalDays = totalDays;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public BookCarStatus getBookCarStatus() {
		return bookCarStatus;
	}

	public void setBookCarStatus(BookCarStatus bookCarStatus) {
		this.bookCarStatus = bookCarStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	
}
