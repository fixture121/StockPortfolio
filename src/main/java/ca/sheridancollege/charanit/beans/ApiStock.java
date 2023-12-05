package ca.sheridancollege.charanit.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiStock {
    private Long id;
    private String ticker;
    private String company;
    private double price;
    private double change;
}
