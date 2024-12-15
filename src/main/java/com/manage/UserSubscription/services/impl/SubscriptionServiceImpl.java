package com.manage.UserSubscription.services.impl;

import com.manage.UserSubscription.dto.SubscriptionDto;
import com.manage.UserSubscription.entities.Plan;
import com.manage.UserSubscription.entities.Subscription;
import com.manage.UserSubscription.entities.SubscriptionStatus;
import com.manage.UserSubscription.entities.User;
import com.manage.UserSubscription.repositories.SubscriptionRepository;
import com.manage.UserSubscription.services.PlanService;
import com.manage.UserSubscription.services.SubscriptionService;
import com.manage.UserSubscription.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private PlanService planService;

    @Autowired
    private UserService userService;

    @Override
    public Subscription getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id).orElse(null);
    }

    @Override
    public Subscription createSubscription(SubscriptionDto sub) {
        Subscription activeSub = getCurrentActiveSubscription((sub.getUserId()));
        User user = userService.getUserById(sub.getUserId());
        Plan plan = planService.getPlanById(sub.getPlanId());
        SubscriptionStatus status = SubscriptionStatus.fromString(sub.getStatus());

        if(activeSub != null || user == null || plan == null)
            return null;

        LocalDateTime date = LocalDateTime.now();

        Subscription subscription = new Subscription();
        subscription.setPlan(plan);
        subscription.setUser(user);
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setStartDate(date);
        subscription.setEndDate(date.plusDays(plan.getDuration()));

        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getCurrentActiveSubscription(Long userId) {
        return subscriptionRepository.findFirstByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE);
    }

    @Override
    public List<Subscription> getAllSubscriptionsForUser(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    @Override
    public Subscription updateSubscription(Long subId, SubscriptionDto subDto) {
        Subscription updateSub = getCurrentActiveSubscription(subId);
        Plan plan = planService.getPlanById(subDto.getPlanId());
        if (updateSub == null || plan == null)
            return updateSub;

        updateSub.setEndDate(updateSub.getEndDate().plusDays(plan.getDuration()));

        return subscriptionRepository.save(updateSub);

    }

    @Override
    public Subscription updateSubscriptionStatus(Long subId, SubscriptionDto subDto) {
        Subscription updateSub = getSubscriptionById(subId);

        if (subDto.getStatus() == null)
            return null;

        if (SubscriptionStatus.fromString(subDto.getStatus()) != null)
            updateSub.setStatus(SubscriptionStatus.fromString(subDto.getStatus()));

        return subscriptionRepository.save(updateSub);
    }

//    @Override
//    public Subscription updateSubscription(Long subId, SubscriptionDto subDto) {
//        Subscription updateSub = getSubscriptionById(subId);
//        if (updateSub == null)
//            return updateSub;
//
//        if (subDto.getEndDate() != null)
//            updateSub.setEndDate(subDto.getEndDate());
//
//        if (subDto.getStartDate() != null)
//            updateSub.setStartDate(subDto.getStartDate());
//
//        if (subDto.getStatus() != null && SubscriptionStatus.fromString(subDto.getStatus()) != null)
//            updateSub.setStatus(SubscriptionStatus.fromString(subDto.getStatus()));
//
//        return subscriptionRepository.save(updateSub);
//
//    }

    @Override
    public Subscription cancelSubscription(Long userId) {
        Subscription sub = getCurrentActiveSubscription(userId);
        if (sub == null)
            return null;

        sub.setStatus(SubscriptionStatus.CANCELLED);
        return subscriptionRepository.save(sub);
    }

}
