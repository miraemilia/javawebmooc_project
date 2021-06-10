package projekti;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/search")
    public String userSearch(Model model, @RequestParam(value="searched") String searched) {
    
        model.addAttribute("results", userRepository.findByUsernameStartingWith(searched));        
        return "/search";
    }
    
    @GetMapping("/search")
    public String searchPage() {
        return "search";
    }
    
}