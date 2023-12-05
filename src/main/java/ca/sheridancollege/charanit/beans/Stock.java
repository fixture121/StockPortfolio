package ca.sheridancollege.charanit.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    private Long id;
    private String ticker;
    private String company;
    private double price;
    private int quantity;
    private double change;
    private double bookCost;

    public double calculateBookCost(double price, int quantity) {
        return price * quantity;
    }


}
