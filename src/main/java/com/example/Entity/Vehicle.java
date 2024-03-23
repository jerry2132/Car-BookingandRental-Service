package com.example.Entity;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

//import com.example.dto.VehicleDto;
//import com.example.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Component
@Entity
@Table(name = "vehicle")
public class Vehicle {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String brand;
	
	private String name;
	
	private String price;
	
	private String color;
	
	private String transmission;
	
	private String type;
	
	private String year;
	
	@Transient
	private MultipartFile imageUrl;
	
	private String image;

	private String description;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private User user;
	
	
//	public VehicleDto getVehicleDto() {
//		
//		VehicleDto vehicleDto = new VehicleDto();
//		vehicleDto.setId(id);
//		vehicleDto.setBrand(brand);
//		vehicleDto.setColor(color);
//		vehicleDto.setDescription(description);
//		vehicleDto.setName(name);
//		vehicleDto.setPrice(price);
//		vehicleDto.setTransmission(transmission);
//		vehicleDto.setType(type);
//		vehicleDto.setYear(year);
//		vehicleDto.setImage(image);
//		return vehicleDto;
//	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public MultipartFile getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(MultipartFile imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	
	
	
	
}
