package controllers;

import jakarta.jws.WebService;
import models.Logging;
import models.LoggingData;

@WebService(endpointInterface = "controllers.LoggingInterface")
public class LoggingController implements LoggingInterface {
    private Logging logging = new Logging();

    @Override
    public void addLog(LoggingData lData) {
        logging.addLog(lData);
    }
}
