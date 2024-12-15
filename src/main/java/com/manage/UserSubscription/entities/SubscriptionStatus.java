package com.manage.UserSubscription.entities;

public enum SubscriptionStatus {
    ACTIVE, INACTIVE, CANCELLED, EXPIRED;

    public static SubscriptionStatus fromString(String status) {
        if (status == null) {
            return null;
        }
        try {
            return SubscriptionStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
