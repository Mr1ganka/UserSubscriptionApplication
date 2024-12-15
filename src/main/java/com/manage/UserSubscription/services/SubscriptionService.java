package com.manage.UserSubscription.services;

import com.manage.UserSubscription.dto.SubscriptionDto;
import com.manage.UserSubscription.entities.Subscription;

import java.util.List;

public interface SubscriptionService {

    public Subscription getSubscriptionById(Long id);

    Subscription createSubscription(SubscriptionDto sub);

    Subscription getCurrentActiveSubscription(Long userId);

    List<Subscription> getAllSubscriptionsForUser(Long userId);


    Subscription updateSubscription(Long userId, SubscriptionDto updatedSub);

    Subscription updateSubscriptionStatus(Long subId, SubscriptionDto dto);

    Subscription cancelSubscription(Long userId);

}
