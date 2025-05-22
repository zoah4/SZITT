package szitt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import szitt.service.ReviewService;
import szitt.model.Review;

import java.util.Optional;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService service;
    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Iterable<Review> getAllReviews() {
        return this.service.getAllReviews();
    }

    @GetMapping("/{id}")
    public Optional<Review> getReview(@PathVariable Long id) {
        return this.service.getReview(id);
    }

    @GetMapping("/instructor/{instructorId}")
    public Iterable<Review> getAllByInstructor(@PathVariable Long instructorId) {
        return this.service.getAllByInstructor(instructorId);
    }

    @GetMapping("/attendant/{attendantId}")
    public Iterable<Review> getAllByAttendant(@PathVariable Long attendantId) {
        return this.service.getAllByAttendant(attendantId);
    }
}
