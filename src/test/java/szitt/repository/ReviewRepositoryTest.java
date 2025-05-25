package szitt.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import szitt.enums.LocationEnum;
import szitt.enums.RoleEnum;
import szitt.enums.StatusEnum;
import szitt.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private AttendantRepository attendantRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private Instructor testInstructor;
    private Attendant testAttendant;
    private Subject testSubject;
    private Reservation testReservation1;
    private Reservation testReservation2;
    private Review testReview;

    @BeforeEach
    void setUp() {
        User instructorUser = new User();
        instructorUser.setFirstname("Instructor");
        instructorUser.setLastname("Test");
        instructorUser.setEmail("instructor@test.com");
        instructorUser.setPassword("password");
        instructorUser.setRole(RoleEnum.INSTRUKTOR);
        userRepository.save(instructorUser);

        testInstructor = new Instructor();
        testInstructor.setUser(instructorUser);
        testInstructor.setDescription("Test instructor");
        testInstructor.setPricePerHour(BigDecimal.valueOf(50));
        instructorRepository.save(testInstructor);

        User attendantUser = new User();
        attendantUser.setFirstname("Attendant");
        attendantUser.setLastname("Test");
        attendantUser.setEmail("attendant@test.com");
        attendantUser.setPassword("password");
        attendantUser.setRole(RoleEnum.POLAZNIK);
        userRepository.save(attendantUser);

        testAttendant = new Attendant();
        testAttendant.setUser(attendantUser);
        attendantRepository.save(testAttendant);

        testSubject = new Subject();
        testSubject.setName("Test Subject");
        subjectRepository.save(testSubject);

        testReservation1 = new Reservation();
        testReservation1.setInstructor(testInstructor);
        testReservation1.setAttendant(testAttendant);
        testReservation1.setSubject(testSubject);
        testReservation1.setDateFrom(LocalDateTime.now().plusDays(1));
        testReservation1.setDateTo(LocalDateTime.now().plusDays(1).plusHours(1));
        testReservation1.setDuration(60);
        testReservation1.setLocation(LocationEnum.ONLINE);
        testReservation1.setStatus(StatusEnum.POTVRDENO);
        reservationRepository.save(testReservation1);

        testReservation2 = new Reservation();
        testReservation2.setInstructor(testInstructor);
        testReservation2.setAttendant(testAttendant);
        testReservation2.setSubject(testSubject);
        testReservation2.setDateFrom(LocalDateTime.now().plusDays(2));
        testReservation2.setDateTo(LocalDateTime.now().plusDays(2).plusHours(1));
        testReservation2.setDuration(60);
        testReservation2.setLocation(LocationEnum.ONLINE);
        testReservation2.setStatus(StatusEnum.POTVRDENO);
        reservationRepository.save(testReservation2);

        testReview = new Review();
        testReview.setInstructor(testInstructor);
        testReview.setAttendant(testAttendant);
        testReview.setReservation(testReservation1);
        testReview.setRating(5);
        testReview.setComment("Excellent instructor");
        testReview.setDate(LocalDateTime.now());
        reviewRepository.save(testReview);
    }

    @Test
    void shouldFindAllByInstructorId() {
        Review anotherReview = new Review();
        anotherReview.setInstructor(testInstructor);
        anotherReview.setAttendant(testAttendant);
        anotherReview.setReservation(testReservation2);
        anotherReview.setRating(4);
        anotherReview.setComment("Very good");
        anotherReview.setDate(LocalDateTime.now());
        reviewRepository.save(anotherReview);

        List<Review> results = (List<Review>) reviewRepository.findAllByInstructorId(testInstructor.getId());

        assertThat(results).hasSize(2);
        assertThat(results).allMatch(review -> review.getInstructor().getId().equals(testInstructor.getId()));
    }

    @Test
    void shouldFindAllByAttendantId() {
        Review anotherReview = new Review();
        anotherReview.setInstructor(testInstructor);
        anotherReview.setAttendant(testAttendant);
        anotherReview.setReservation(testReservation2);
        anotherReview.setRating(3);
        anotherReview.setComment("Good");
        anotherReview.setDate(LocalDateTime.now());
        reviewRepository.save(anotherReview);

        List<Review> results = (List<Review>) reviewRepository.findAllByAttendantId(testAttendant.getId());

        assertThat(results).hasSize(2);
        assertThat(results).allMatch(review -> review.getAttendant().getId().equals(testAttendant.getId()));
    }

    @Test
    void shouldFindByReservationId() {
        Optional<Review> result = reviewRepository.findByReservationId(testReservation1.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getReservation().getId()).isEqualTo(testReservation1.getId());
        assertThat(result.get().getComment()).isEqualTo("Excellent instructor");
    }

    @Test
    void shouldReturnEmptyWhenReservationNotFound() {
        Optional<Review> result = reviewRepository.findByReservationId(999L);
        assertThat(result).isEmpty();
    }
}