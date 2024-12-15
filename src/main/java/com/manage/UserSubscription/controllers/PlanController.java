package com.manage.UserSubscription.controllers;

import com.manage.UserSubscription.ApiResponse;
import com.manage.UserSubscription.entities.Plan;
import com.manage.UserSubscription.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private PlanService planService;

    @GetMapping()
    public ResponseEntity <ApiResponse<List<Plan>>> getAllPlans() {
        List<Plan> plans =  planService.getAllPlans();

        if(plans.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, plans, "NO PLANS TO DISPLAY", HttpStatus.NOT_FOUND));

        return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse<>(true, plans, "FETCHED", HttpStatus.FOUND));
    }

    @PostMapping()
    public ResponseEntity <ApiResponse<Plan>> createPlan(@RequestBody Plan plan) {
        Plan planBody = planService.createPlan(plan);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, planBody, "CREATED", HttpStatus.CREATED));
    }

    @GetMapping("/{planId}")
    public ResponseEntity <ApiResponse<Plan>> getPlanById(@PathVariable Long planId){
        Plan plan = planService.getPlanById(planId);

        if (plan == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, plan, "PLAN NOT FOUND", HttpStatus.NOT_FOUND));

        return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse<>(true, plan, "FETCHED", HttpStatus.FOUND));
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<ApiResponse<Plan>> deletePlan(@PathVariable Long planId) {
        Plan plan = planService.deletePLanById(planId);

        if (plan == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, plan, "PLAN NOT FOUND", HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(new ApiResponse<>(true, plan, "DELETED", HttpStatus.OK));
    }

    @PutMapping("/{planId}")
    public ResponseEntity <ApiResponse<Plan>> updateUser(@PathVariable Long planId, @RequestBody Plan plan) {
        Plan fetchedPlan = planService.updatePlanById(planId, plan);

        if (fetchedPlan == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, fetchedPlan, "PLAN NOT FOUND FOR ID: "+planId, HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(new ApiResponse<>(true, fetchedPlan, "PLAN UPDATED SUCCESSFULLY", HttpStatus.CREATED));
    }
}
