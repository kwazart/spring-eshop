package com.polozov.springeshop.dao;

import com.polozov.springeshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findFirstByName(String name);
}
