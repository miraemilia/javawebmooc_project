
package projekti;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class User extends AbstractPersistable<Long> {
    
    private String name;
    private String username;
    private String password;
    private String abbr;
    @ManyToMany
    private List<User> following;
    @OneToMany
    private List<Image> images;
            
}
