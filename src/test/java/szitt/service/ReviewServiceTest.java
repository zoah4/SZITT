package szitt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import szitt.model.Review;
import szitt.repository.ReviewRepository;
import szitt.repository.ReservationRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReviewById() {
        Review review = new Review();
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        Optional<Review> result = reviewService.getReview(1L);
        assert(result.isPresent());
    }
}
