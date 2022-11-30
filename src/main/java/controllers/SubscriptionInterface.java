package controllers;

import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import models.SubscriptionData;

@WebService
public interface SubscriptionInterface {
    @WebMethod
    void addSubscription(@WebParam SubscriptionData sData);

    @WebMethod
    void updateStatus(@WebParam Integer creatorId, Integer subscriberId, String status);
    
    @WebMethod
    List<SubscriptionData> getListSubcription();

    @WebMethod
    boolean getStatus(@WebParam Integer creatorId, Integer subscriberId);

    @WebMethod
    List<SubscriptionData> getSubscriptionBySubsId(@WebParam Integer subscriberId);
}