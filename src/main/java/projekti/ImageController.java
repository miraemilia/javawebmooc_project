
package projekti;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {
    
    @Autowired
    private ImageRepository imageRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/images")
    public String showImages(Model model) {
            User user = userService.getUser();
            model.addAttribute("images", imageRepository.findByOwner(user));
    
        return "images";
    }
    
    
    @GetMapping("/profile/{abbr}/album")
    public String showAlbum(Model model, @PathVariable("abbr") String abbr) {
            User other = userRepository.findByAbbr(abbr);
            model.addAttribute("other", other);
            model.addAttribute("images", imageRepository.findByOwner(other));
    
        return "album";
    }
    
    @PostMapping("/images")
    public String addImage(@RequestParam("file") MultipartFile file, @RequestParam("description") String description) throws IOException {
        
        List<Image> images = imageRepository.findByOwner(userService.getUser());
        if (images.size() < 10) {
        
            Image image = new Image();
            
            User user = userService.getUser();
            image.setOwner(user);
        
            image.setDescription(description);
        
            image.setContent(file.getBytes());
        
            imageRepository.save(image);
        }

        return "redirect:/images";
    }
    
    @GetMapping(path = "/images/{id}/content", produces = "image/jpg")
    @ResponseBody
    public byte[] showGif(@PathVariable Long id) {
        return imageRepository.getOne(id).getContent();
    }
    
    @GetMapping("/images/{id}")
    public String showImage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUser());
        Image image = imageRepository.getOne(id);
        Pageable latestComments = PageRequest.of(0, 10, Sort.by("sendDate").descending());
        model.addAttribute("image", image);
        model.addAttribute("comments", commentRepository.findByImage(image, latestComments));
        return "image";
    }
    
    @PostMapping("/images/remove/{id}")
    public String deleteImage(@PathVariable("id") Long id) {
        User user = userService.getUser();
        Image image = imageRepository.getOne(id);
        List<Comment> comments = commentRepository.findByImage(image);
        for (Comment comment : comments) {
            commentRepository.delete(comment);
        }
        if (user.getProfilepic() == image) {
            user.setProfilepic(null);
            userRepository.save(user);
        }
        imageRepository.delete(image);
        return "redirect:/images";
    }
    
    @PostMapping("/images/like/{id}")
    public String like(@PathVariable(value="id") Long id) {
        
        Image image = imageRepository.getOne(id);
        User user = userService.getUser();
        
        if (!image.getLikers().contains(user)) {
            
            image.getLikers().add(user);
            imageRepository.save(image);
        }

        return "redirect:/images/" + id;
    }
    
    @PostMapping("/images/comment/{id}")
    public String comment(@PathVariable("id") Long id, @RequestParam String content) {
      
        Comment comment = new Comment();
        
        User user = userService.getUser();
        comment.setWriter(user);
        
        comment.setSendDate(LocalDateTime.now());
        
        comment.setContent(content);
        
        Image image = imageRepository.getOne(id);
        comment.setImage(image);

        commentRepository.save(comment);

        return "redirect:/images/" + id;
    }
    
}
