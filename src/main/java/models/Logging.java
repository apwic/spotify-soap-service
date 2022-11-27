package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import config.DbConfig;

public class Logging {
    public void addLog(LoggingData lData) {
        String sql = "INSERT logging (description, ip, endpoint, requested_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbConfig.connect();
            PreparedStatement query = conn.prepareStatement(sql)) {
            query.setString(1, lData.getDescription());
            query.setString(2, lData.getIp());
            query.setString(3, lData.getEndpoint());
            query.setTimestamp(4, lData.getRequestedAt());
            query.execute();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR: unable to insert logging", e);
        }
    }
}
