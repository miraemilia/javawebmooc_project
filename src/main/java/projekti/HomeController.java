package projekti;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    
    @Autowired 
    private UserRepository userRepository;
    
    @Autowired
    private ImageRepository imageRepository;
    
    @Autowired
    private FollowRepository followRepository;
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired 
    private UserService userService;
    
    @GetMapping("/home")
    public String home(Model model) {
        
        Pageable latestMessages = PageRequest.of(0, 25, Sort.by("sendDate").descending());
        
        User user = userService.getUser();
        model.addAttribute("user", user);
        
        
        List<Follow> follows = user.getFollowerTo();
        List<User> selfAndFriends = new ArrayList();
        for (Follow follow : follows) {
            selfAndFriends.add(follow.getFollowed());
        }
        selfAndFriends.add(user);
        model.addAttribute("messages", messageRepository.findByWriterIn(selfAndFriends, latestMessages));
        
        return "home";
    }
    
    @PostMapping("/home/profilepic/{id}")
    public String changeProfilepic(@PathVariable("id") long id) {
        User user = userService.getUser();
        user.setProfilepic(imageRepository.getOne(id));
        userRepository.save(user);
   
        return "redirect:/home";
    }
    
    @PostMapping("/home/follow/{id}")
    public String follow(@PathVariable("id") long id) {
        
        User followed = userRepository.getOne(id);
        String abbr = followed.getAbbr();
        User follower = userService.getUser();
        
        if (follower != followed & followRepository.findByFollowedAndFollower(followed, follower) == null) {
        
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowed(followed);
            follow.setFollowTime(LocalDateTime.now());
        
            followRepository.save(follow);
            
        }
        
        return "redirect:/profile/" +abbr;
    }
    
    @PostMapping("/home/unfollow/{id}")
    public String unfollow(@PathVariable("id") Long id) {
        User user = userService.getUser();
        User followed = userRepository.getOne(id);
        String abbr = followed.getAbbr();
        Follow follow = followRepository.findByFollowedAndFollower(followed, user);
        followRepository.delete(follow);
        
        return "redirect:/profile/" +abbr;
    }
    
    @PostMapping("/home/reject/{id}")
    public String reject(@PathVariable("id") Long id) {
        User user = userService.getUser();
        User follower = userRepository.getOne(id);
        Follow follow = followRepository.findByFollowedAndFollower(user, follower);
        followRepository.delete(follow);
        return "redirect:/home";
    }
        
}