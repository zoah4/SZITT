package szitt.service;

import org.springframework.stereotype.Service;
import szitt.dto.NotificationDTO;
import szitt.dto.ReservationDTO;
import szitt.enums.StatusEnum;
import szitt.model.*;
import szitt.repository.AttendantRepository;
import szitt.repository.InstructorRepository;
import szitt.repository.ReservationRepository;
import szitt.repository.SubjectRepository;

import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReviewService reviewService;
    private final NotificationService notificationService;
    private final AttendantRepository attendantRepository;
    private final InstructorRepository instructorRepository;
    private final SubjectRepository subjectRepository;
    public ReservationService(ReservationRepository reservationRepository, ReviewService reviewService, NotificationService notificationService, AttendantRepository attendantRepository, InstructorRepository instructorRepository, SubjectRepository subjectRepository) {
        this.reservationRepository = reservationRepository;
        this.reviewService = reviewService;
        this.notificationService = notificationService;
        this.attendantRepository = attendantRepository;
        this.instructorRepository = instructorRepository;
        this.subjectRepository = subjectRepository;
    }

    public Iterable<Reservation> getAllReservations() {
        return this.reservationRepository.findAll();
    }

    public Optional<Reservation> getReservation(Long id) {
        return this.reservationRepository.findById(id);
    }

    public void confirm(Long id) {
        Optional<Reservation> reservation = this.reservationRepository.findById(id);
        reservation.get().setStatus(StatusEnum.POTVRDENO);
        reservationRepository.save(reservation.get());

        /*NotificationDTO notification = new NotificationDTO();
        notification.setType();
        notification.setUser(reservation.get().get);
        notification.setContent();
        this.notificationService.sendNotification(notification);*/
    }

    public void reject(Long id) {
        Optional<Reservation> reservation = this.reservationRepository.findById(id);
        reservation.get().setStatus(StatusEnum.OTKAZANO);
        reservationRepository.save(reservation.get());
    }

    public void cancel(Long id) {
        Optional<Reservation> reservation = this.reservationRepository.findById(id);
        reservation.get().setStatus(StatusEnum.ODBIJENO);
        reservationRepository.save(reservation.get());
    }

    public void markAsCompleted(Long id) {
        Optional<Reservation> reservation = this.reservationRepository.findById(id);
        reservation.get().setStatus(StatusEnum.ODRADENO);
        reservationRepository.save(reservation.get());
    }

    public void onWait(Long id) {
        Optional<Reservation> reservation = this.reservationRepository.findById(id);
        reservation.get().setStatus(StatusEnum.NA_CEKANJU);
        reservationRepository.save(reservation.get());
    }

    public Optional<ReservationReview> getReservationWithReview(Long reservationId) {
        Reservation reservation = this.reservationRepository.findById(reservationId).get();
        Review review = this.reviewService.getByReservation(reservationId).get();
        ReservationReview reservationReview = new ReservationReview(reservation, review);
        return Optional.of(reservationReview);
    }

    public void createReservation(Long attendantId, Long instructorId, ReservationDTO dto) {
        Attendant attendant = this.attendantRepository.findById(attendantId).get();
        Instructor instructor = this.instructorRepository.findById(instructorId).get();
        Subject subject = this.subjectRepository.findById(dto.getSubjectId()).get();

        Reservation reservation = new Reservation();
        reservation.setAttendant(attendant);
        reservation.setInstructor(instructor);
        reservation.setSubject(subject);
        reservation.setDateFrom(dto.getDateFrom());
        reservation.setDateTo(dto.getDateTo());
        reservation.setDuration(dto.getDuration()); //možda će ić izmjena na dto.getDateTo() - dto.getDateFrom()
        reservation.setLocation(dto.getLocation());
        reservation.setStatus(StatusEnum.NA_CEKANJU);
        reservation.setCreated(reservation.getCreated());

        this.reservationRepository.save(reservation);

        attendant.setNumOfReservations(attendant.getNumOfReservations() + 1);
        this.attendantRepository.save(attendant);

    }
}
