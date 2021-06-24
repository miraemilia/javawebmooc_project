package projekti;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class Image extends AbstractPersistable<Long> {

    @ManyToOne
    private User owner;
    private String description;
    @Lob
    private byte[] content;
    @ManyToMany
    private List<User> likers;
}
