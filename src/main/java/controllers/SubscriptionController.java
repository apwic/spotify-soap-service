package controllers;

import java.util.List;

import jakarta.jws.WebService;
import models.Subscription;
import models.SubscriptionData;

@WebService(endpointInterface = "controllers.SubscriptionInterface")
public class SubscriptionController implements SubscriptionInterface {
    private Subscription subscription = new Subscription();

    @Override
    public void addSubscription(SubscriptionData sData) {
        subscription.addSubscription(sData);
    }

    @Override
    public void updateStatus(Integer creatorId, Integer subscriberId, String status) {
        SubscriptionData sData = new SubscriptionData();
        sData.setCreatorId(creatorId);
        sData.setSubscriberId(subscriberId);
        sData.setStatus(status);
        subscription.updateStatus(sData);
    }

    @Override
    public List<SubscriptionData> getListSubcription() {
        return subscription.getListSubscription();
    }

    @Override
    public boolean getStatus(Integer creatorId, Integer subscriberId) {
        SubscriptionData sData = new SubscriptionData();
        sData.setCreatorId(creatorId);
        sData.setSubscriberId(subscriberId);
        return (subscription.getStatus(sData)).equals("ACCEPTED");
    }
}
