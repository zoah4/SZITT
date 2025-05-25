package szitt.controller;

import org.springframework.web.bind.annotation.*;
import szitt.service.ReviewService;
import szitt.model.Review;
import szitt.dto.ReviewDTO;

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

    @PutMapping("/{id}")
    public void editReview(@PathVariable Long id, @RequestBody ReviewDTO dto) {
        this.service.editReview(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        this.service.deleteReview(id);
    }
}
