package szitt.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import szitt.dto.InstructorDTO;
import szitt.model.User;
import szitt.repository.InstructorRepository;
import szitt.model.Instructor;
import szitt.repository.ReviewRepository;

import java.util.Optional;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;

    public InstructorService(InstructorRepository instructorRepository, ReviewRepository reviewRepository, PasswordEncoder passwordEncoder) {
        this.instructorRepository = instructorRepository;
        this.reviewRepository = reviewRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Iterable<Instructor> getAllInstructors() {
        return this.instructorRepository.findAll();
    }

    public Optional<Instructor> getInstructor(Long id) {
        return this.instructorRepository.findById(id);
    }

    public void editProfile(Long id, InstructorDTO dto) {
        Instructor instructor = this.instructorRepository.findById(id).get();

        User user = instructor.getUser();
        user.setFirstname(dto.getInstructor().getFirstname());
        user.setLastname(dto.getInstructor().getLastname());

        instructor.setUser(user);
        instructor.setDescription(dto.getDescription());
        instructor.setPricePerHour(dto.getPricePerHour());

        this.instructorRepository.save(instructor);
    }

}
