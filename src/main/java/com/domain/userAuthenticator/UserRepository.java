package com.domain.userAuthenticator;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

public interface UserRepository extends CrudRepository<User, Long> {
	boolean existsByUsername(String username);
	List<User> findByUsername(String username);
}
 