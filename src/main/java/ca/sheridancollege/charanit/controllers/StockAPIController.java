package ca.sheridancollege.charanit.controllers;

import ca.sheridancollege.charanit.beans.ApiStock;
import ca.sheridancollege.charanit.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/apiStocks")
public class StockAPIController {

    @Autowired
    private DatabaseAccess da;

    @GetMapping
    public List<ApiStock> getAllApiStocksList() {
        return da.getAllApiStocksList();
    }

    @GetMapping(value = "/{ticker}")
    public ApiStock getIndividualApiStock(@PathVariable String ticker) {
        return da.getIndividualApiStock(ticker);
    }

    @PostMapping(consumes = "application/json")
    public String postStock(@RequestBody ApiStock apiStock) {
        return "http://localhost:8080/api/v1/apiStocks" + da.insertApiStock(apiStock);
    }

//    @PutMapping(consumes = "application/json")
//    public String putStock(@RequestBody List<ApiStock> apiStockList) {
//        da.deleteAllApiStocks();
//        da.saveAllApiStocks(apiStockList);
//        return "Total records: " + da.count();
//    }

    @PutMapping(consumes = "application/json")
    public String putStock(@RequestBody List<ApiStock> apiStockList) {
        System.out.println("test");
        // Assuming you need to keep the existing records
        // da.deleteAllApiStocks();

        // Save or update each ApiStock
        for (ApiStock apiStock : apiStockList) {
            da.insertApiStock(apiStock);
        }

        return "Total records: " + da.count();
    }


}
