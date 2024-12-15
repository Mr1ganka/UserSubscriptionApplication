package com.manage.UserSubscription.dto;

import java.time.LocalDateTime;

public class SubscriptionDto {
    private Long userId;

    private Long planId;

    private String status; //need to remove

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public SubscriptionDto() {
    }

    public SubscriptionDto(Long userId, Long planId, String status, LocalDateTime startDate, LocalDateTime endDate) {
        this.userId = userId;
        this.planId = planId;
        this.status = status; //need to remove
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
