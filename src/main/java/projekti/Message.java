package projekti;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class Message extends AbstractPersistable<Long> {

    @ManyToOne
    private User writer;
    private LocalDateTime sendDate;
    private String content;
    @ManyToMany
    private List<User> likers;
    @OneToMany(mappedBy="message")
    private List<Comment> comments;
    
}
