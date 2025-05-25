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

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private AttendantRepository attendantRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    private Instructor testInstructor;
    private Attendant testAttendant;
    private Subject testSubject;
    private Reservation testReservation;

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

        testReservation = new Reservation();
        testReservation.setInstructor(testInstructor);
        testReservation.setAttendant(testAttendant);
        testReservation.setSubject(testSubject);
        testReservation.setDateFrom(LocalDateTime.now().plusDays(1));
        testReservation.setDateTo(LocalDateTime.now().plusDays(1).plusHours(1));
        testReservation.setDuration(60);
        testReservation.setLocation(LocationEnum.ONLINE);
        testReservation.setStatus(StatusEnum.NA_CEKANJU);
        reservationRepository.save(testReservation);
    }

    @Test
    void shouldFindByInstructorIdAndStatus() {
        Reservation anotherReservation = new Reservation();
        anotherReservation.setInstructor(testInstructor);
        anotherReservation.setAttendant(testAttendant);
        anotherReservation.setSubject(testSubject);
        anotherReservation.setDateFrom(LocalDateTime.now().plusDays(2));
        anotherReservation.setDateTo(LocalDateTime.now().plusDays(2).plusHours(1));
        anotherReservation.setDuration(60);
        anotherReservation.setLocation(LocationEnum.KOD_INSTRUKTORA);
        anotherReservation.setStatus(StatusEnum.POTVRDENO);
        reservationRepository.save(anotherReservation);

        Iterable<Reservation> results = reservationRepository
                .findByInstructorIdAndStatus(testInstructor.getId(), StatusEnum.NA_CEKANJU);

        assertThat(results).hasSize(1);
        assertThat(results.iterator().next().getStatus()).isEqualTo(StatusEnum.NA_CEKANJU);
        assertThat(results.iterator().next().getInstructor().getId()).isEqualTo(testInstructor.getId());
    }

    @Test
    void shouldFindByAttendantIdAndStatus() {
        Reservation anotherReservation = new Reservation();
        anotherReservation.setInstructor(testInstructor);
        anotherReservation.setAttendant(testAttendant);
        anotherReservation.setSubject(testSubject);
        anotherReservation.setDateFrom(LocalDateTime.now().plusDays(3));
        anotherReservation.setDateTo(LocalDateTime.now().plusDays(3).plusHours(1));
        anotherReservation.setDuration(60);
        anotherReservation.setLocation(LocationEnum.KOD_POLAZNIKA);
        anotherReservation.setStatus(StatusEnum.OTKAZANO);
        reservationRepository.save(anotherReservation);

        Iterable<Reservation> results = reservationRepository
                .findByAttendantIdAndStatus(testAttendant.getId(), StatusEnum.NA_CEKANJU);

        assertThat(results).hasSize(1);
        assertThat(results.iterator().next().getStatus()).isEqualTo(StatusEnum.NA_CEKANJU);
        assertThat(results.iterator().next().getAttendant().getId()).isEqualTo(testAttendant.getId());
    }

    @Test
    void shouldFindByCriteriaWithAllParams() {
        Iterable<Reservation> results = reservationRepository.findByCriteria(
                testInstructor.getId(),
                testAttendant.getId(),
                testSubject.getId());

        assertThat(results).hasSize(1);
        assertThat(results.iterator().next().getId()).isEqualTo(testReservation.getId());
    }

    @Test
    void shouldFindByCriteriaWithInstructorOnly() {
        Iterable<Reservation> results = reservationRepository.findByCriteria(
                testInstructor.getId(),
                null,
                null);

        assertThat(results).hasSize(1);
        assertThat(results.iterator().next().getInstructor().getId()).isEqualTo(testInstructor.getId());
    }

    @Test
    void shouldFindByCriteriaWithAttendantOnly() {
        Iterable<Reservation> results = reservationRepository.findByCriteria(
                null,
                testAttendant.getId(),
                null);

        assertThat(results).hasSize(1);
        assertThat(results.iterator().next().getAttendant().getId()).isEqualTo(testAttendant.getId());
    }

    @Test
    void shouldFindByCriteriaWithSubjectOnly() {
        Iterable<Reservation> results = reservationRepository.findByCriteria(
                null,
                null,
                testSubject.getId());

        assertThat(results).hasSize(1);
        assertThat(results.iterator().next().getSubject().getId()).isEqualTo(testSubject.getId());
    }

    @Test
    void shouldFindByCriteriaWithNoParams() {
        Iterable<Reservation> results = reservationRepository.findByCriteria(null, null, null);
        assertThat(results).hasSize(1);
    }

    @Test
    void shouldReturnEmptyWhenNoMatches() {
        Iterable<Reservation> results = reservationRepository.findByCriteria(999L, 999L, 999L);
        assertThat(results).isEmpty();
    }
}