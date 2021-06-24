package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    @EntityGraph(attributePaths = {"profilepic"})
    User findByAbbr(String abbr);
    @EntityGraph(attributePaths = {"profilepic"})
    User findByUsername(String username);
    @EntityGraph(attributePaths = {"profilepic"})
    List<User> findByNameStartingWithIgnoreCase(String key);
    
}
