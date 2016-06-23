package com.tax.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@NotNull(message = "Add products")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "SALEPRODUCTENTITY_ID")
	private List<SaleProduct> products;

	@NotNull(message = "Sale needs some value!")
	private BigDecimal saleValue;

	private Date creationDate = new Date();

}
