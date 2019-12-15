package org.mjjaenl.reactivetutorial.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Document //Equivalent to @Entity for SQL database
@Data //Getter + Setter + RequiredArgsConstructor + ToString + EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ItemCapped {
    @Id
    private String id;
    @NotEmpty(message="description required")
    @Size(min = 1, max = 100)
    private String description;
    @DecimalMin("0.01")
    @NotNull
    private BigDecimal price;
}