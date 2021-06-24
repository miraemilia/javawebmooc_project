package projekti;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private UserService userService;
    
    
    @GetMapping("/message/{id}")
    public String showMessage(Model model, @PathVariable("id") Long id) {
        
        Message message = messageRepository.getOne(id);
        Pageable latestComments = PageRequest.of(0, 10, Sort.by("sendDate").descending());
        List<Comment> comments = commentRepository.findByMessage(message, latestComments);
        
        model.addAttribute("user", userService.getUser());
        model.addAttribute("message", message);
        model.addAttribute("comments", comments);
        
        return "message";
    }
    
    @PostMapping("/message")
    public String sendMessage(@RequestParam("content") String content) {
        Message message = new Message();
        
        User user = userService.getUser();
        message.setWriter(user);

        message.setSendDate(LocalDateTime.now());
        
        message.setContent(content);
        
        messageRepository.save(message);
        
        return "redirect:/home";
    }
    
    @PostMapping("/message/like/{id}")
    public String like(@PathVariable("id") Long id) {
        
        Message message = messageRepository.getOne(id);
        User user = userService.getUser();
        
        if (!message.getLikers().contains(user)) {
            
            message.getLikers().add(user);
            messageRepository.save(message);
        }

        return "redirect:/message/" + id;
    }
    
    @PostMapping("/message/comment/{id}")
    public String comment(@PathVariable("id") Long id, @RequestParam String content) {
      
        Comment comment = new Comment();
        
        User user = userService.getUser();
        comment.setWriter(user);
        
        comment.setSendDate(LocalDateTime.now());
        
        comment.setContent(content);
        
        Message message = messageRepository.getOne(id);
        comment.setMessage(message);

        commentRepository.save(comment);

        return "redirect:/message/" + id;
    }
}
