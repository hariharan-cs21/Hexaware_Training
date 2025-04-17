package hexaware.case_study.service.interfaces;


import hexaware.case_study.entity.Reservation;
import java.util.List;

public interface IReservationService {
    Reservation getReservationById(int reservationId);
    List<Reservation> getReservationsByCustomerId(int customerId);
    void createReservation(Reservation reservation);
    void updateReservation(Reservation reservation);
    void cancelReservation(int reservationId);
}

