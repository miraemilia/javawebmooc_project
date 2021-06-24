package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByFollowedAndFollower(User followed, User follower);
}
