package com.hritvik.CarbonCellAssignment.repository;

import com.hritvik.CarbonCellAssignment.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,String> {
    Optional<Users> findByEmail(String email);
}
