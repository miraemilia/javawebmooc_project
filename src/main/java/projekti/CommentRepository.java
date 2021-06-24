package projekti;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByMessage(Message message, Pageable pageable);
    List<Comment> findByImage(Image image);
    List<Comment> findByImage(Image image, Pageable pageable);
    
    
}
