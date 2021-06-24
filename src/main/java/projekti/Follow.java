package projekti;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Follow extends AbstractPersistable<Long> {
    
    @ManyToOne
    private User follower;
    @ManyToOne
    private User followed;
    private LocalDateTime followTime;
    
}
