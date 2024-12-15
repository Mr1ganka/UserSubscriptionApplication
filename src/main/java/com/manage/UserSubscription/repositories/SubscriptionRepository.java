package com.manage.UserSubscription.repositories;

import com.manage.UserSubscription.entities.Subscription;
import com.manage.UserSubscription.entities.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUserId(Long userId);

    Subscription findFirstByUserIdAndStatus(Long userId, SubscriptionStatus status);

}
