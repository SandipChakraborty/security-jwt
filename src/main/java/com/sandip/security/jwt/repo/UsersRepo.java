package com.sandip.security.jwt.repo;

import com.sandip.security.jwt.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends CrudRepository<Users, Long> {

    Users findByUsername(String username);
}
