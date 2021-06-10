package projekti;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class Comment extends AbstractPersistable<Long> {
    
    @ManyToOne
    private User owner;
    private String content;
    private LocalDateTime sendDate;
    @ManyToOne
    private Message target;
    
}
