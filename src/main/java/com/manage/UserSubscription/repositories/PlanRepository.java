package com.manage.UserSubscription.repositories;

import com.manage.UserSubscription.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository <Plan, Long> {
}
