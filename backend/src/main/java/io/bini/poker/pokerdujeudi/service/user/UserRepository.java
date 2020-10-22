package io.bini.poker.pokerdujeudi.service.user;

import io.bini.poker.pokerdujeudi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
