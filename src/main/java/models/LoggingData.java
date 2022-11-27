package models;

import lombok.Data;

@Data
public class LoggingData {
    private Integer id;
    private String description;
    private String ip;
    private String endpoint;
    private java.sql.Timestamp requestedAt;
}
