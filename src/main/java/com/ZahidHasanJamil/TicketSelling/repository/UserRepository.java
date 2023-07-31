package com.ZahidHasanJamil.TicketSelling.repository;

import com.ZahidHasanJamil.TicketSelling.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
