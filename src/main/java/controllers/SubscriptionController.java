package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // post/put request to PHP
    private void postRequest(HttpURLConnection conn, Map<String, String> formData) {
        try {
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            boolean start = true;
            String data = "";
            try {
                for (Map.Entry<String, String> entry : formData.entrySet()) {
                    if (start) { start = false; }
                    else { data += "&"; }
                    data += URLEncoder.encode(entry.getKey(), "UTF-8") + "=" +  URLEncoder.encode(entry.getValue(), "UTF-8");
                }
            } catch (UnsupportedEncodingException e) {
                System.out.println("Failed to form POST data");
            }
            wr.write(data);
            wr.flush();
            wr.close();
        } catch (IOException e) {
            System.out.println("Failed to post form data");
        }
    }

    // handle response from PHP
    private void handleResponse(HttpURLConnection conn) {
        try {
            try(BufferedReader br = new BufferedReader(
            new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }
        } catch (IOException e) {
            System.out.println("Failed to handle response");
        }
    }

    @WebMethod
    public void addSubscription(SubscriptionData sData) {
        // add subscription
        subscription.addSubscription(sData);
        
        // request logging
        LoggingData lData = new LoggingData();
        MessageContext mc = webService.getMessageContext();
        HttpExchange req = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
        lData.setDescription("Subscription with creatorId " + sData.getCreatorId() + " and subscriberId " + sData.getSubscriberId() + " added");
        lData.setIp((req.getRemoteAddress()).toString());
        lData.setEndpoint("/subscription");
        lData.setRequestedAt(new Timestamp(System.currentTimeMillis()));
        logging.addLog(lData);

        // sync addition with sepotipayi app
        try {
            URL appUrl = new URL("http://localhost:80/api/subs/addsubs.php");
            HttpURLConnection conn = (HttpURLConnection)appUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            Map<String, String> formData = new HashMap<String, String>();
            formData.put("creator_id", sData.getCreatorId().toString());
            formData.put("subscriber_id", sData.getSubscriberId().toString());
            formData.put("creator_name", sData.getCreatorName());
            formData.put("subscriber_name", sData.getSubscriberName());
            formData.put("status", sData.getStatus());
            postRequest(conn, formData);
            handleResponse(conn);
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Unable to sync subscription (add)");
        }
    }
    
    @WebMethod
    public void updateStatus(Integer creatorId, Integer subscriberId, String status) {
        // update subscription
        SubscriptionData sData = new SubscriptionData();
        sData.setCreatorId(creatorId);
        sData.setSubscriberId(subscriberId);
        sData.setStatus(status);
        subscription.updateStatus(sData);

        // request logging
        LoggingData lData = new LoggingData();
        MessageContext mc = webService.getMessageContext();
        HttpExchange req = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
        lData.setDescription("Subscription updated in " + subscriberId + " to " + status);
        lData.setIp((req.getRemoteAddress()).toString());
        lData.setEndpoint("/subscription");
        lData.setRequestedAt(new Timestamp(System.currentTimeMillis()));
        logging.addLog(lData);

        // sync updates with sepotipayi app
        try {
            URL appUrl = new URL("http://localhost:80/api/subs/updatesubs.php");
            HttpURLConnection conn = (HttpURLConnection)appUrl.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            Map<String, String> formData = new HashMap<String, String>();
            formData.put("creator_id", creatorId.toString());
            formData.put("subscriber_id", subscriberId.toString());
            formData.put("status", status);
            postRequest(conn, formData);
            handleResponse(conn);
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Unable to sync subscription (update)");
        }
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

    @WebMethod
    public List<SubscriptionData> getSubscriptionBySubsId (Integer subscriberId) {
      LoggingData lData = new LoggingData();
      MessageContext mc = webService.getMessageContext();
      HttpExchange req = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
      lData.setDescription("Get subscription by subscriberId " + subscriberId);
      lData.setIp(req.getRemoteAddress().toString());
      lData.setEndpoint("/subscription");
      lData.setRequestedAt(new Timestamp(System.currentTimeMillis()));
      logging.addLog(lData);

      return subscription.getSubscriptionBySubsId(subscriberId);
    }
}
