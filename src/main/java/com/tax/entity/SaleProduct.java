package com.tax.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Builder
public class SaleProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private int quantity;

	@Size(min = 1, message = "Insert the product, please")
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Sale sale;

}
