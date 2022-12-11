package com.cloud.auth.repository;

import com.cloud.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserId(@Param("userId") String UserId);

    List<User> findByName(@Param("name") String name);

    List<User> findByMailAddr(@Param("mailAddr") String mailAddr);
}
