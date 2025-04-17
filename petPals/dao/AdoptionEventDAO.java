package hexaware.petPals.dao;

import java.sql.SQLException;

public interface AdoptionEventDAO {
    void registerParticipant(String participantName, int eventId) throws SQLException;
    void listEvents() throws SQLException;
    void createEvent(String eventName, java.sql.Date eventDate) throws SQLException;
    void listParticipantsByEvent() throws SQLException;
}
