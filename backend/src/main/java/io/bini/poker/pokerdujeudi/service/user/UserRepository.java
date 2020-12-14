package io.bini.poker.pokerdujeudi.service.user;

import io.bini.poker.pokerdujeudi.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
}
