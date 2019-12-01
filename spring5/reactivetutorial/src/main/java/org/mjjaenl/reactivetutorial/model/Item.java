package org.mjjaenl.reactivetutorial.model;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document //Equivalent to @Entity for SQL database
@Data //Getter + Setter + RequiredArgsConstructor + ToString + EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	@Id
	private String id;
	@NotEmpty(message="description required")
	@Size(min = 1, max = 100)
	private String description;
	@DecimalMin("0.01")
	private BigDecimal price;
}