package controllers;

import models.Logging;
import models.LoggingData;

public class LoggingController implements LoggingInterface {
    private Logging logging = new Logging();

    @Override
    public void addLog(LoggingData lData) {
        logging.addLog(lData);
    }
}
