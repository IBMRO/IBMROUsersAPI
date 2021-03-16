package com.ibm.ro.users.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.ro.users.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByName(String name);

	Optional<User> findByEmail(String email);

}
