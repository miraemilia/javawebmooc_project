package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/profile/{abbr}")
    public String showProfile(@PathVariable(value="abbr") String abbr, Model model) {
        model.addAttribute("user", userRepository.findByAbbr(abbr));
        return "profile";
    }
    
}
