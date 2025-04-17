package hexaware.petPals.dao;

import hexaware.petPals.DBConnUtil;

import java.sql.*;

public class AdoptionEventDAOImpl implements AdoptionEventDAO {
    private Connection conn;

    public AdoptionEventDAOImpl() {
        try {
            conn = DBConnUtil.getConnection("db.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerParticipant(String participantName, int eventId) throws SQLException {
        String sql = "INSERT INTO participants (event_id, participant_name) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, eventId);
        ps.setString(2, participantName);
        ps.executeUpdate();
        System.out.println("Participant registered successfully.");
    }

    @Override
    public void listEvents() throws SQLException {
        String sql = "SELECT * FROM adoption_events";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("Upcoming Adoption Events:");
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("event_name");
            Date date = rs.getDate("event_date");
            System.out.println("Event ID: " + id + ", Name: " + name + ", Date: " + date);
        }
    }
    @Override
    public void createEvent(String eventName, java.sql.Date eventDate) throws SQLException {
        String sql = "INSERT INTO adoption_events (event_name, event_date) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, eventName);
            ps.setDate(2, eventDate);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Event created successfully.");
            } else {
                throw new SQLException("Failed to create the event.");
            }
        }
    }
    @Override
    public void listParticipantsByEvent() throws SQLException {
        String sql = "SELECT ae.id, ae.event_name, p.participant_name " +
                "FROM adoption_events ae LEFT JOIN participants p ON ae.id = p.event_id " +
                "ORDER BY ae.id";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        int currentEventId = -1;
        while (rs.next()) {
            int eventId = rs.getInt("id");
            String eventName = rs.getString("event_name");
            String participantName = rs.getString("participant_name");

            if (eventId != currentEventId) {
                System.out.println("\nEvent ID: " + eventId + ", Name: " + eventName);
                System.out.println("Participants:");
                currentEventId = eventId;
            }

            if (participantName != null) {
                System.out.println(" - " + participantName);
            } else {
                System.out.println(" (No participants registered)");
            }
        }
    }

}

