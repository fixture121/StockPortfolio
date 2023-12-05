/* HomeController:
 * -Unsecured index
 * -Navigation to secure/index
 * -Navigation to viewMarket
 * -Permission-denied
 * -Login
 * -Registration
 */

package ca.sheridancollege.charanit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ca.sheridancollege.charanit.database.DatabaseAccess;

@Controller
public class HomeController {

    @Autowired
    @Lazy
    private DatabaseAccess da;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/secure")
    public String secureIndex(Model model) {
        return "/secure/index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/permission-denied")
    public String permissionDenied(Model model) {
        return "/error/permission-denied";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

//    @PostMapping("/register")
//    public String postRegister(@RequestParam String username, @RequestParam String password) {
//        da.addUser(username, password);
//        Long userId = da.findUserAccount(username).getUserId();
//        da.addRole(userId, Long.valueOf(1));
//        return "redirect:/";
//    }

}
