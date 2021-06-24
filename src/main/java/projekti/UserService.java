package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = new User();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
        String username = auth.getName();
        user = userRepository.findByUsername(username);
        }
        return user;
    }
    
}
