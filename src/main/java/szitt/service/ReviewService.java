package szitt.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import szitt.dto.ReviewDTO;
import szitt.model.Reservation;
import szitt.repository.ReservationRepository;
import szitt.repository.ReviewRepository;
import szitt.model.Review;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    public ReviewService(ReviewRepository reviewRepository, ReservationRepository reservationRepository) {
        this.reviewRepository = reviewRepository;
        this.reservationRepository = reservationRepository;
    }

    public Iterable<Review> getAllReviews() {
        return this.reviewRepository.findAll();
    }

    public Optional<Review> getReview(Long id) {
        return this.reviewRepository.findById(id);
    }

    public Iterable<Review> getAllByInstructor(Long instructorId) {
        return this.reviewRepository.findAllByInstructorId(instructorId);
    }

    public Iterable<Review> getAllByAttendant(Long attendantId) {
        return this.reviewRepository.findAllByAttendantId(attendantId);
    }

    public Optional<Review> getByReservation(Long reviewId) {
        return this.reviewRepository.findByReservationId(reviewId);
    }

    public void writeReview(Long attendantId, Long reservationId, ReviewDTO dto) {
        Reservation reservation = reservationRepository.findById(reservationId).get();

        Review review = new Review();
        review.setReservation(reservation);
        review.setAttendant(reservation.getAttendant());
        review.setInstructor(reservation.getInstructor());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setDate(dto.getDate());

        reviewRepository.save(review);
    }
}
