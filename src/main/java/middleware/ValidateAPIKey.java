package middleware;

import jakarta.annotation.Resource;
import jakarta.jws.WebMethod;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import com.sun.net.httpserver.HttpExchange;

public class ValidateAPIKey {
    @Resource
    private WebServiceContext webService;

    @WebMethod
    public String validateApiKey(MessageContext mc) {
        HttpExchange req = (HttpExchange) mc.get("com.sun.xml.ws.http.exchange");
        String apikey = req.getRequestHeaders().getFirst("authorization");
        if (apikey == null) {
            return "INVALID";
        }
        String appapikey = System.getenv("APIKEY_SEPOTIPAYI_APP");
        String restapikey = System.getenv("APIKEY_SEPOTIPAYI_REST");

        if (apikey.equals(restapikey)) {
            return "REST";
        } else if (apikey.equals(appapikey)) {
            return "APP";
        } else {
            return "INVALID";
        }
    }
}
