package com.interview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interview.entity.User;
import com.interview.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole , Long>{
    List<UserRole> findByUser(User user);
}
