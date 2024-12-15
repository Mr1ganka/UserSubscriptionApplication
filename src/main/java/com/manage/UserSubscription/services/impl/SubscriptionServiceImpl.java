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
        Subscription activeSub = getCurrentActiveSub(sub.getUserId());
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

        subscriptionRepository.save(subscription);
        return hidePassword(subscription);
    }

    @Override
    public Subscription getCurrentActiveSubscription(Long userId) {
        Subscription sub = subscriptionRepository.findFirstByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE);
        return hidePassword(sub);
    }

    protected Subscription getCurrentActiveSub(Long userId) {
        return subscriptionRepository.findFirstByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE);
    }

    @Override
    public List<Subscription> getAllSubscriptionsForUser(Long userId) {
        List<Subscription> subs = subscriptionRepository.findByUserId(userId);

        for (Subscription sub : subs) {
            User user = sub.getUser();

            if (user != null) {
                user.setPassword("*");

            }
        }
        return subs;
    }

    @Override
    public Subscription updateSubscription(Long subId, SubscriptionDto subDto) {
        Subscription updateSub = getCurrentActiveSub(subId);
        Plan plan = planService.getPlanById(subDto.getPlanId());
        if (updateSub == null || plan == null)
            return updateSub;

        updateSub.setEndDate(updateSub.getEndDate().plusDays(plan.getDuration()));

        subscriptionRepository.save(updateSub);
        return hidePassword(updateSub);

    }


    @Override
    public Subscription cancelSubscription(Long userId) {
        Subscription sub = getCurrentActiveSub(userId);
        if (sub == null)
            return null;

        sub.setStatus(SubscriptionStatus.CANCELLED);
        Subscription saved = subscriptionRepository.save(sub);
        return hidePassword(saved);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription updateSubStatus(Subscription sub) {
        return subscriptionRepository.save(sub);
    }

    public Subscription hidePassword(Subscription sub){
        if (sub == null || sub.getUser() == null)
            return sub;
        sub.getUser().setPassword("*");
        return sub;
    }

}
