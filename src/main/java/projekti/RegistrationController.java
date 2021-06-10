
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    
    @Autowired
    private UserRepository userRepository;
    
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @GetMapping("/register")
    public String showRegistered(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "register";
    }
    
    @PostMapping("/register")
    public String register() {
        String base = "käyttäjä";
        for (int i = 1; i<5; i++) {
            String ordinal = String.valueOf(i);
            String username = base + ordinal;
            User u = userRepository.findByUsername(username);
            if (u == null) {
                u = new User();
                u.setUsername(username);
                u.setPassword(passwordEncoder.encode("salasana"));
                u.setAbbr("k" + ordinal);
                userRepository.save(u);
                break;
            }
        }
        return "redirect:/register";
    }
    
}
