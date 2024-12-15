package com.manage.UserSubscription.scheduling;

import com.manage.UserSubscription.entities.Subscription;
import com.manage.UserSubscription.entities.SubscriptionStatus;
import com.manage.UserSubscription.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SubscriptionScheduler {

    @Autowired
    private SubscriptionService subscriptionService;

    @Scheduled(cron = "0 */5 * * * ?") // This runs every 5 mins
    public void updateExpiredSubscriptions() {
        List<Subscription> activeSubscriptions = subscriptionService.getAllSubscriptions();
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Running CRON JOB "+now );

        for (Subscription subscription : activeSubscriptions) {
            if (subscription.getEndDate().isBefore(now) && subscription.getStatus() == SubscriptionStatus.ACTIVE) {
                subscription.setStatus(SubscriptionStatus.EXPIRED);
                subscriptionService.updateSubStatus(subscription);
                System.out.println("UPDATED JOB: "+subscription);
            }
        }
    }
}