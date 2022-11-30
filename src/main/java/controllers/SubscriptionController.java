package controllers;

import java.sql.Timestamp;
import java.util.List;

import com.sun.xml.ws.developer.JAXWSProperties;

import jakarta.annotation.Resource;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import com.sun.net.httpserver.HttpExchange;
import models.Subscription;
import models.SubscriptionData;
import models.Logging;
import models.LoggingData;

@WebService(endpointInterface = "controllers.SubscriptionInterface")
public class SubscriptionController implements SubscriptionInterface {
    @Resource
    private WebServiceContext webService;

    private Subscription subscription = new Subscription();
    private Logging logging = new Logging();

    @WebMethod
    public void addSubscription(SubscriptionData sData) {
        subscription.addSubscription(sData);
        
        LoggingData lData = new LoggingData();
        MessageContext mc = webService.getMessageContext();
        HttpExchange req = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
        lData.setDescription("Subscription with creatorId " + sData.getCreatorId() + " and subscriberId " + sData.getSubscriberId() + " added");
        lData.setIp((req.getRemoteAddress()).toString());
        lData.setEndpoint("/subscription");
        lData.setRequestedAt(new Timestamp(System.currentTimeMillis()));
        logging.addLog(lData);
    }
    
    @WebMethod
    public void updateStatus(Integer creatorId, Integer subscriberId, String status) {
        SubscriptionData sData = new SubscriptionData();
        sData.setCreatorId(creatorId);
        sData.setSubscriberId(subscriberId);
        sData.setStatus(status);
        subscription.updateStatus(sData);

        LoggingData lData = new LoggingData();
        MessageContext mc = webService.getMessageContext();
        HttpExchange req = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
        lData.setDescription("Subscription updated in " + subscriberId + " to " + status);
        lData.setIp((req.getRemoteAddress()).toString());
        lData.setEndpoint("/subscription");
        lData.setRequestedAt(new Timestamp(System.currentTimeMillis()));
        logging.addLog(lData);
    }

    @WebMethod
    public List<SubscriptionData> getListSubcription() {
        LoggingData lData = new LoggingData();
        MessageContext mc = webService.getMessageContext();
        HttpExchange req = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
        lData.setDescription("Get list subscription");
        lData.setIp(req.getRemoteAddress().toString());
        lData.setEndpoint("/subscription");
        lData.setRequestedAt(new Timestamp(System.currentTimeMillis()));
        logging.addLog(lData);

        return subscription.getListSubscription();
    }

    @WebMethod
    public boolean getStatus(Integer creatorId, Integer subscriberId) {
        SubscriptionData sData = new SubscriptionData();
        sData.setCreatorId(creatorId);
        sData.setSubscriberId(subscriberId);

        LoggingData lData = new LoggingData();
        MessageContext mc = webService.getMessageContext();
        HttpExchange req = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
        lData.setDescription("Get status from creatorId " + creatorId + " and subscriberId " + subscriberId);
        lData.setIp(req.getRemoteAddress().toString());
        lData.setEndpoint("/subscription");
        lData.setRequestedAt(new Timestamp(System.currentTimeMillis()));
        logging.addLog(lData);

        return (subscription.getStatus(sData)).equals("ACCEPTED");
    }
}
