import controllers.*;
import jakarta.xml.ws.Endpoint;

public class App {
    public static void main(String[] args) {
        Endpoint.publish(
            "http://localhost:8080/subscription",
            new SubscriptionController()
        );
    }
}
