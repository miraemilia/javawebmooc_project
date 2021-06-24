package projekti;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private FollowRepository followRepository;
    
    @GetMapping("/profile/{abbr}")
    public String showProfile(@PathVariable(value="abbr") String abbr, Model model) {
        
        User user = userService.getUser();
        if (user.getAbbr().equals(abbr)) {
            return "redirect:/home";
        } else {
        
        model.addAttribute("user", user);
        
        Pageable latestMessages = PageRequest.of(0, 25, Sort.by("sendDate").descending());
        
        User other = userRepository.findByAbbr(abbr);
        model.addAttribute("other", other);
        
        List<Follow> follows = other.getFollowerTo();
        List<User> selfAndFriends = new ArrayList();
        for (Follow follow : follows) {
            selfAndFriends.add(follow.getFollowed());
        }
        selfAndFriends.add(other);
        model.addAttribute("messages", messageRepository.findByWriterIn(selfAndFriends, latestMessages));
        
        if (followRepository.findByFollowedAndFollower(other, user) != null) {
            model.addAttribute("followed", true);
        } else {
            model.addAttribute("followed", false);
        }
                
        return "profile";
        }
    }
    
}
