package com.fetch.demofetch.repository;

import com.fetch.demofetch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepo extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {
}
