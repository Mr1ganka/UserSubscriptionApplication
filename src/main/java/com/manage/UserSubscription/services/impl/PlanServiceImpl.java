package com.manage.UserSubscription.services.impl;

import com.manage.UserSubscription.entities.Plan;
import com.manage.UserSubscription.entities.User;
import com.manage.UserSubscription.repositories.PlanRepository;
import com.manage.UserSubscription.services.PlanService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanServiceImpl implements PlanService {
    private PlanRepository planRepository;

    public PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }


    @Override
    public Plan getPlanById(Long id) {
        return planRepository.findById(id).orElseThrow(() -> new RuntimeException("PLAN NOT FOUND FOR ID: " + id));
    }

    @Override
    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    @Override
    public Plan createPlan(Plan plan) {
        return planRepository.save(plan);
    }

    @Override
    public Plan deletePLanById(Long id) {
        Plan plan = getPlanById(id);
        if (plan != null)
            planRepository.deleteById(id);

       return plan;
    }

    @Override
    public Plan updatePlanById(Long id, Plan plan) {
        Plan fetchedPlan = getPlanById(id);

        if (fetchedPlan != null) {
            fetchedPlan.setDuration(plan.getDuration());
            fetchedPlan.setFeature(plan.getFeature());
            fetchedPlan.setName(plan.getName());
            plan.setPrice(plan.getPrice());
            planRepository.save(plan);
        }

        return fetchedPlan;
    }

}
