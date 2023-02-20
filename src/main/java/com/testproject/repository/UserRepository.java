package com.testproject.repository;

import com.testproject.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM users u  WHERE u.email = ?1")
    User findUserByEmail(String email);
    Page<User> findAll(Pageable pageable);
}
