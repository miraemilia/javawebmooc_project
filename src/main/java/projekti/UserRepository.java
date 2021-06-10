package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    
    User findByAbbr(String abbr);
    User findByUsername(String username);
    List<User> findByUsernameStartingWith(String key);
    
}
