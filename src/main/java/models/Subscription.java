package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import config.DbConfig;

public class Subscription {
    public void addSubscription (SubscriptionData sData) {
        String sql = "INSERT subscription (creator_id, subscriber_id, creator_name, subscriber_name, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConfig.connect();
            PreparedStatement query = conn.prepareStatement(sql)) {
            query.setInt(1, sData.getCreatorId());
            query.setInt(2, sData.getSubscriberId());
            query.setString(3, sData.getCreatorName());
            query.setString(4, sData.getSubscriberName());
            query.setString(5, sData.getStatus());
            query.execute();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR: unable to insert subscription", e);
        }
    }

    public void updateStatus (SubscriptionData sData) {
        String sql = "UPDATE subscription SET status = ? WHERE creator_id = ? AND subscriber_id = ?";
        try (Connection conn = DbConfig.connect();
            PreparedStatement query = conn.prepareStatement(sql)) {
            query.setString(1, sData.getStatus());
            query.setInt(2, sData.getCreatorId());
            query.setInt(3, sData.getSubscriberId());
            query.execute();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR: unable to update subscription", e);
        }
    }

    public List<SubscriptionData> getListSubscription () {
        String sql = "SELECT * FROM subscription";
        try (Connection conn = DbConfig.connect();
            PreparedStatement query = conn.prepareStatement(sql)) {
            ResultSet res = query.executeQuery();

            List<SubscriptionData> listSubs = new ArrayList<>();
            while (res.next()) {
                SubscriptionData sData = new SubscriptionData();
                sData.setCreatorId(res.getInt("creator_id"));
                sData.setSubscriberId(res.getInt("subscriber_id"));
                sData.setCreatorName(res.getString("creator_name"));
                sData.setSubscriberName(res.getString("subscriber_name"));
                sData.setStatus(res.getString("status"));
                listSubs.add(sData);
            }
            return listSubs;
        } catch (SQLException e) {
            throw new RuntimeException("ERROR: unable to get list subscription", e);
        }
    }

    public String checkStatus (SubscriptionData sData) {
        String sql = "SELECT status FROM subscription WHERE creator_id = ? AND subscriber_id = ?";
        try (Connection conn = DbConfig.connect();
            PreparedStatement query = conn.prepareStatement(sql)) {
            query.setInt(1, sData.getCreatorId());
            query.setInt(2, sData.getSubscriberId());
            ResultSet res = query.executeQuery();

            String status = "";
            if (res.next()) {
                status = res.getString("status");
            }
            return status;
        } catch (SQLException e) {
            throw new RuntimeException("ERROR: unable to get subscription status", e);
        }
    }

    public List<SubscriptionData> getSubscriptionBySubsId (Integer subscriberId) {
      String sql = "SELECT * FROM subscription WHERE subscriber_id = ? AND STATUS='APPROVE'";
      try (Connection conn = DbConfig.connect(); PreparedStatement query = conn.prepareStatement(sql)) {
        query.setInt(1, subscriberId);
        ResultSet res = query.executeQuery();

        List<SubscriptionData> listSubs = new ArrayList<>();
        while (res.next()) {
            SubscriptionData sData = new SubscriptionData();
            sData.setCreatorId(res.getInt("creator_id"));
            sData.setSubscriberId(res.getInt("subscriber_id"));
            sData.setCreatorName(res.getString("creator_name"));
            sData.setSubscriberName(res.getString("subscriber_name"));
            sData.setStatus(res.getString("status"));
            listSubs.add(sData);
        }
        return listSubs;
      } catch (SQLException e) {
        throw new RuntimeException("ERROR: unable to get subscription data", e);
      }
    }

}
