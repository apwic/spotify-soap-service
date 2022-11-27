package models;

import lombok.Data;

enum Status {
    PENDING,
    ACCEPTED,
    REJECTED
}

@Data
public class SubscriptionData {
    private Integer creatorId;
    private Integer subscriberId;
    private String creatorName;
    private String subscriberName;
    private String status;
}
