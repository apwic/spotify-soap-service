import controllers.*;
import jakarta.xml.ws.Endpoint;

public class App {
    public static void main(String[] args) {
        Endpoint.publish(
            "http://0.0.0.0:7070/subscription",
            new SubscriptionController()
        );
    }
}
