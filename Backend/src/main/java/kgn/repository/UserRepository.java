package kgn.repository;

import kgn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface for data access operations on the User entity.
 * It extends {@link JpaRepository} to provide standard CRUD operations and includes
 * custom methods for user-specific queries.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
