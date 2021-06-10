
package projekti;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImageController {
    
    @GetMapping("/images")
    public String searchPage() {
        return "images";
    }
    
}
