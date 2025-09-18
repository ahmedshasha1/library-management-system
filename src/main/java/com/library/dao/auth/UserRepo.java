package com.library.dao.auth;

import com.library.enumration.Role;
import com.library.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);

    @Query("SELECT s FROM User s " +
            "WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "   OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String name);
}
