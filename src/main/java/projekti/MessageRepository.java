package projekti;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    
    List<Message> findByWriter(User user, Pageable pageable);
    List<Message> findByWriterIn(List<User> user, Pageable pageable);
}
