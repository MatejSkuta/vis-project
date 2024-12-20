package com.example.spring_app;

import com.example.spring_app.dtos.UserDTO;
import com.example.spring_app.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @ModelAttribute("currentUser")
    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getName())) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = new User();
            user.setUsername(userDetails.getUsername());
            user.setRole(userDetails.getAuthorities().iterator().next().getAuthority());
            return user;
        }
        return null;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "home"; // Název šablony home.html
    }
    @GetMapping("/leagues")
    public String leagues(Model model) {
        return "leagues";
    }

    @GetMapping("/teams")
    public String teams(Model model) {
        return "teams";
    }

    @GetMapping("/players")
    public String players(Model model) {
        return "players";
    }

    @GetMapping("/{id}/details")
    public String showMatchDetails(@PathVariable Long id, Model model) {
        model.addAttribute("matchId", id);
        return "matchDetail"; // Název šablony
    }

    @GetMapping("/addMatch")
    public String showAddMatch( Model model) {
        return "addMatch"; // Název šablony
    }
}