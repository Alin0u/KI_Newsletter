package kgn.service;

import kgn.model.User;
import kgn.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

/**
 * A service that provides user details specific to the application's requirements.
 * It implements the {@link UserDetailsService} interface required by Spring Security for user authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Constructs a new CustomUserDetailsService with a specified UserRepository.
     *
     * @param userRepository The UserRepository to be used for user data access.
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        System.out.println("CustomUserDetailsService");
        this.userRepository = userRepository;
    }

    /**
     * Loads the user details by username.
     *
     * @param username The username of the user whose details are to be loaded.
     * @return UserDetails containing user's data necessary for authentication.
     * @throws UsernameNotFoundException if the user with the specified username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

}
