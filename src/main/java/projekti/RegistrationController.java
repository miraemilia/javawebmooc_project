
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    
    @Autowired
    private UserRepository userRepository;
    
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @GetMapping("/register")
    public String showRegistration() {
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@RequestParam("username") String username, 
            @RequestParam("password") String password, 
            @RequestParam("name") String name, 
            @RequestParam("abbr") String abbr) {

            User u = userRepository.findByUsername(username);
            if (u == null) {
                u = new User();
                u.setUsername(username);
                u.setPassword(passwordEncoder.encode(password));
                u.setAbbr(abbr);
                u.setName(name);
                userRepository.save(u);
            }
            
        return "redirect:/register";
    }
    
}
