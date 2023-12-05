/* PortfolioController:
 * -Navigation to managePortfolio
 * -insertStock
 * -deleteStock
 * -editStock
 */

package ca.sheridancollege.charanit.controllers;

import ca.sheridancollege.charanit.beans.Stock;
import ca.sheridancollege.charanit.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class PortfolioController {

    List<Stock> stockList = new CopyOnWriteArrayList<>();

    @Autowired
    @Lazy
    private DatabaseAccess da;

    @GetMapping("/portfolio")
    public String managePortfolio(Model model) {
        model.addAttribute("stockList", da.getAllStocksList());
        model.addAttribute("stock", new Stock());
        return "secure/portfolio";
    }

    @PostMapping("/insertStock")
    public String insertStock(Model model, @ModelAttribute Stock stock) {
        da.insertStock(stock);
        model.addAttribute("stock", new Stock());
        model.addAttribute("stockList", da.getAllStocksList());
        return "secure/portfolio";
    }

    @GetMapping("/updateStock/{ticker}")
    public String updateStock(Model model, @PathVariable String ticker) {
        Stock stock = da.getStockByTicker(ticker).get(0);
        model.addAttribute("stock", stock);
//        da.deleteStock(ticker);
        model.addAttribute("stockList", da.getAllStocksList());
        return "secure/portfolio";
    }

    @GetMapping("/deleteStock/{ticker}")
    public String deleteStock(Model model, @PathVariable String ticker) {
        model.addAttribute("stockList", da.getAllStocksList());
        model.addAttribute("stock", new Stock());
        da.deleteStock(ticker);
        return "secure/portfolio";
    }

}
