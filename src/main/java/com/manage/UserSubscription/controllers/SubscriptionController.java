package com.manage.UserSubscription.controllers;


import com.manage.UserSubscription.ApiResponse;
import com.manage.UserSubscription.dto.SubscriptionDto;
import com.manage.UserSubscription.entities.Subscription;
import com.manage.UserSubscription.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping()
    public ResponseEntity<ApiResponse<Subscription>> createSubscription(@RequestBody SubscriptionDto dto) {
        Subscription sub = subscriptionService.createSubscription(dto);
        if (sub == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(
                            false,
                            sub,
                            "SUBSCRIPTION COULD NOT BE CREATED",
                            HttpStatus.INTERNAL_SERVER_ERROR
                    ));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                true,
                sub,
                "SUBSCRIPTION WAS CREATED",
                HttpStatus.CREATED
                ));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Subscription>> fetchActiveSubscription (@PathVariable Long userId) {
        Subscription sub = subscriptionService.getCurrentActiveSubscription(userId);
        if (sub == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            false,
                            sub,
                            "NO ACTIVE SUBSCRIPTION WAS FOUND",
                            HttpStatus.NOT_FOUND
                    ));

        else
            return ResponseEntity.status(HttpStatus.FOUND).body(
                    new ApiResponse<>(
                            true,
                            sub,
                            "ACTIVE SUBSCRIPTION FETCHED",
                            HttpStatus.FOUND
                    ));
    }

    @GetMapping("/{userId}/all")
    public ResponseEntity<ApiResponse<List<Subscription>>> fetchAllSubscriptionForUser (@PathVariable Long userId) {
        List<Subscription> sub = subscriptionService.getAllSubscriptionsForUser(userId);
        if (sub.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            false,
                            sub,
                            "NO SUBSCRIPTIONS WERE FOUND",
                            HttpStatus.NOT_FOUND
                    ));

        else
            return ResponseEntity.status(HttpStatus.FOUND).body(
                    new ApiResponse<>(
                            true,
                            sub,
                            "ALL SUBSCRIPTION FETCHED",
                            HttpStatus.FOUND
                    ));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Subscription>> DeleteActiveSubscription(@PathVariable Long userId) {
        Subscription cancelSub = subscriptionService.cancelSubscription(userId);
        if (cancelSub == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            false,
                            cancelSub,
                            "NO ACTIVE SUBSCRIPTION WAS FOUND TO CANCEL",
                            HttpStatus.NOT_FOUND
                    ));
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        cancelSub,
                        "ACTIVE SUBSCRIPTION CANCELLED",
                        HttpStatus.FOUND
                ));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<Subscription>> updateSubscription(@RequestBody SubscriptionDto dto, @RequestParam Long id) {
        Subscription sub = subscriptionService.updateSubscription(id, dto);
        if (sub == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            false,
                            sub,
                            "ERROR ENCOUNTERED WHILE UPDATING SUBSCRIPTION",
                            HttpStatus.NOT_FOUND
                    ));
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        sub,
                        "SUBSCRIPTION UPDATED",
                        HttpStatus.FOUND
                ));
    }

}

