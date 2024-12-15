package com.manage.UserSubscription.services;

import com.manage.UserSubscription.entities.Plan;

import java.util.List;

public interface PlanService {
    Plan getPlanById(Long id);

    List<Plan> getAllPlans();

    Plan createPlan(Plan plan);

    Plan deletePLanById(Long id);

    Plan updatePlanById(Long id, Plan plan);
}
