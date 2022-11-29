package controllers;

import java.sql.Timestamp;
import java.util.List;

import jakarta.jws.WebService;
import models.Subscription;
import models.SubscriptionData;
import models.Logging;
import models.LoggingData;

@WebService(endpointInterface = "controllers.SubscriptionInterface")
public class SubscriptionController implements SubscriptionInterface {
    private Subscription subscription = new Subscription();
    private Logging logging = new Logging();

    @Override
    public void addSubscription(SubscriptionData sData) {
      subscription.addSubscription(sData);

      LoggingData lData = new LoggingData();
      lData.setDescription("Subscription with creatorId " + sData.getCreatorId() + " and subscriberId " + sData.getSubscriberId() + " added");
      lData.setIp("0.0.0.0");
      lData.setEndpoint("/subscription");
      lData.setRequestedAt(new Timestamp(System.currentTimeMillis()));
      logging.addLog(lData);
    }
    
    @Override
    public void updateStatus(Integer creatorId, Integer subscriberId, String status) {
        SubscriptionData sData = new SubscriptionData();
        sData.setCreatorId(creatorId);
        sData.setSubscriberId(subscriberId);
        sData.setStatus(status);
        subscription.updateStatus(sData);

        LoggingData lData = new LoggingData();
        lData.setDescription("Subscription updated in " + subscriberId + " to " + status);
        lData.setIp("0.0.0.0");
        lData.setEndpoint("/subscription");
        lData.setRequestedAt(new Timestamp(System.currentTimeMillis()));
        logging.addLog(lData);
    }

    @Override
    public List<SubscriptionData> getListSubcription() {
        LoggingData lData = new LoggingData();
        lData.setDescription("Get list subscription");
        lData.setIp("0.0.0.0");
        lData.setEndpoint("/subscription");
        lData.setRequestedAt(new Timestamp(System.currentTimeMillis()));
        logging.addLog(lData);

        return subscription.getListSubscription();
    }

    @Override
    public boolean getStatus(Integer creatorId, Integer subscriberId) {
        SubscriptionData sData = new SubscriptionData();
        sData.setCreatorId(creatorId);
        sData.setSubscriberId(subscriberId);

        LoggingData lData = new LoggingData();
        lData.setDescription("Get status from creatorId " + creatorId + " and subscriberId " + subscriberId);
        lData.setIp("0.0.0.0");
        lData.setEndpoint("/subscription");
        lData.setRequestedAt(new Timestamp(System.currentTimeMillis()));
        logging.addLog(lData);

        return (subscription.getStatus(sData)).equals("ACCEPTED");
    }
}
