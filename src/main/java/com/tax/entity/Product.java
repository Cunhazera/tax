package com.tax.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@NotNull(message = "Name cant be null")
	@Size(min = 1, message = "Name must have at least one char!")
	private String name;

	@NotNull(message = "Price cant be null")
	private double price;

	@NotNull(message = "Impost null")
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Impost impost;

}
