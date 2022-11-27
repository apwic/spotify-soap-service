package controllers;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import models.LoggingData;

@WebService
public interface LoggingInterface {
    @WebMethod
    void addLog(@WebParam LoggingData lData);
}
