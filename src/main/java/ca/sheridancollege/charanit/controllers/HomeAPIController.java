/* HomeAPIController:
 * -Home API
 */

package ca.sheridancollege.charanit.controllers;

import ca.sheridancollege.charanit.beans.ApiStock;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeAPIController {

    private static final String REST_URL = "http://localhost:8080/api/v1/apiStocks";

    @GetMapping("/viewMarket")
    public String viewMarket(Model model, RestTemplate restTemplate) {
        ResponseEntity<ApiStock[]> responseEntity = restTemplate.getForEntity(REST_URL, ApiStock[].class);
        model.addAttribute("apiStockList", responseEntity.getBody());
        return "/secure/viewMarket";
    }

    @GetMapping(value = "/getStock/{ticker}", produces = "application/json")
    @ResponseBody
    public ApiStock getApiStock(@PathVariable String ticker, RestTemplate restTemplate) {
        ResponseEntity<ApiStock> responseEntity = restTemplate.getForEntity(REST_URL + "/" + ticker, ApiStock.class);
        return responseEntity.getBody();
    }

}
