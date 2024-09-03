package com.weilai.http.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;


public class HttpRequest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLoginRequest {
        private String username;
        private String password;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date lastLogin;
    }


    public static class ProductListRequest {
        private List<String> productIds;
        private String category;
    }


    public static class OrderCreateRequest {
        private String customerId;
        private double totalPrice;
        private boolean expressShipping;
    }


    public static class ConfigUpdateRequest {
        private Map<String, String> settings;
    }


    public static class ArticleSearchRequest {
        private String keyword;
        private int year;
    }


    public static class EventScheduleRequest {
        private String eventName;
        private LocalDate eventDate;
    }


    public static class NotificationPreferenceRequest {
        private String userId;
        private boolean emailEnabled;
        private boolean smsEnabled;
    }


    public static class FeedbackSubmitRequest {
        private String feedbackText;
        private int rating;
    }


    public static class UserUpdateRequest {
        private String userId;
        private String newName;
        private String newEmail;
    }


    public static class RoleAssignmentRequest {
        private String userId;
        private Set<String> roles;
    }
}
