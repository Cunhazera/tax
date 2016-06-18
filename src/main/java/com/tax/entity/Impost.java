package com.tax.entity;

import static javax.persistence.GenerationType.AUTO;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Impost {

	@Id
	@GeneratedValue(strategy = AUTO)
	private long id;

	@NotNull(message = "The impost has a name!")
	@Size(min = 1)
	private String name;

	@NotNull(message = "The impost percent is not null!")
	private BigDecimal percent;

}
