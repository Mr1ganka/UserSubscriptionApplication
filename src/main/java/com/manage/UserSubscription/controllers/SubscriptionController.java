package com.manage.UserSubscription.controllers;


import com.manage.UserSubscription.ApiResponse;
import com.manage.UserSubscription.dto.SubscriptionDto;
import com.manage.UserSubscription.entities.Subscription;
import com.manage.UserSubscription.services.SubscriptionService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<ApiResponse<Subscription>> createSubscription(@RequestBody SubscriptionDto dto, HttpServletRequest request) {
        String token = request.getAttribute("userId").toString();
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "JWT TOKEN NOT FOUND", HttpStatus.FORBIDDEN));

        if (!token.equals(String.valueOf(dto.getUserId()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "USER ID DOES NOT MATCH WITH THE TOKEN", HttpStatus.FORBIDDEN));
        }

        try {
            Subscription sub = subscriptionService.createSubscription(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, sub, "SUBSCRIPTION WAS CREATED", HttpStatus.CREATED));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, null, "SUBSCRIPTION COULD NOT BE CREATED :: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Subscription>> fetchActiveSubscription (@PathVariable Long userId, HttpServletRequest request) {
        String token = request.getAttribute("userId").toString();
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "JWT TOKEN NOT FOUND", HttpStatus.FORBIDDEN));

        if (!token.equals(String.valueOf(userId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "USER ID DOES NOT MATCH WITH THE TOKEN", HttpStatus.FORBIDDEN));
        }

        try {
            Subscription sub = subscriptionService.getCurrentActiveSubscription(userId);
            return ResponseEntity.status(HttpStatus.FOUND).body(
                    new ApiResponse<>(true, sub, "ACTIVE SUBSCRIPTION FETCHED", HttpStatus.FOUND));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, null, "COULDN'T FETCH :: "+e.getMessage() , HttpStatus.NOT_FOUND));
        }

    }

    @GetMapping("/{userId}/all")
    public ResponseEntity<ApiResponse<List<Subscription>>> fetchAllSubscriptionForUser (@PathVariable Long userId, HttpServletRequest request) {
        String token = request.getAttribute("userId").toString();
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "JWT TOKEN NOT FOUND", HttpStatus.FORBIDDEN));

        if (!token.equals(String.valueOf(userId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "USER ID DOES NOT MATCH WITH THE TOKEN", HttpStatus.FORBIDDEN));
        }

        List<Subscription> sub = subscriptionService.getAllSubscriptionsForUser(userId);
        if (sub.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, sub, "NO SUBSCRIPTIONS WERE FOUND", HttpStatus.NOT_FOUND));

        else
            return ResponseEntity.status(HttpStatus.FOUND).body(
                    new ApiResponse<>(true, sub, "ALL SUBSCRIPTION FETCHED", HttpStatus.FOUND));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Subscription>> DeleteActiveSubscription(@PathVariable Long userId, HttpServletRequest request) {
        String token = request.getAttribute("userId").toString();
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "JWT TOKEN NOT FOUND", HttpStatus.FORBIDDEN));

        if (!token.equals(String.valueOf(userId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "USER ID DOES NOT MATCH WITH THE TOKEN", HttpStatus.FORBIDDEN));
        }
        try{
            Subscription cancelSub = subscriptionService.cancelSubscription(userId);
            return ResponseEntity.ok(new ApiResponse<>(true, cancelSub, "ACTIVE SUBSCRIPTION CANCELLED", HttpStatus.FOUND));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, null, "ERROR ENCOUNTERED WHILE DELETING :: "+e.getMessage(), HttpStatus.NOT_FOUND));
        }

    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<Subscription>> updateSubscription(@PathVariable Long userId, @RequestBody SubscriptionDto dto, HttpServletRequest request) {
        String token = request.getAttribute("userId").toString();
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "JWT TOKEN NOT FOUND", HttpStatus.FORBIDDEN));

        if (!token.equals(String.valueOf(userId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "USER ID DOES NOT MATCH WITH THE TOKEN", HttpStatus.FORBIDDEN));
        }


        try {
            Subscription sub = subscriptionService.updateSubscription(userId, dto);
            return ResponseEntity.ok(new ApiResponse<>(true, sub, "SUBSCRIPTION UPDATED", HttpStatus.FOUND));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, null, "ERROR ENCOUNTERED WHILE UPDATING SUBSCRIPTION :: "+e.getMessage(), HttpStatus.NOT_FOUND));
        }
    }


}

