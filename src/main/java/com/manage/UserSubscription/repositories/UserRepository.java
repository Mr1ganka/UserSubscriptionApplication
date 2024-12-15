package com.manage.UserSubscription.repositories;

import com.manage.UserSubscription.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long>  {
    User findByuserName(String userName);
}
