package szitt.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import szitt.dto.LoginDTO;
import szitt.dto.RegisterDTO;
import szitt.enums.RoleEnum;
import szitt.model.Attendant;
import szitt.model.Instructor;
import szitt.model.User;
import szitt.repository.AttendantRepository;
import szitt.repository.InstructorRepository;
import szitt.repository.UserRepository;

import java.math.BigDecimal;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final AttendantRepository attendantRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder encoder, InstructorRepository instructorRepository, AttendantRepository attendantRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = encoder;
        this.instructorRepository = instructorRepository;
        this.attendantRepository = attendantRepository;
    }

    public void register(RegisterDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email već postoji");
        }

        User user = new User();
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        userRepository.save(user);

        if(dto.getRole().equals(RoleEnum.INSTRUKTOR)) {
            Instructor instructor = new Instructor();
            instructor.setUser(user);
            instructor.setDescription("");
            instructor.setPricePerHour(BigDecimal.valueOf(0));
            instructorRepository.save(instructor);
        } else if(dto.getRole().equals(RoleEnum.POLAZNIK)) {
            Attendant attendant = new Attendant();
            attendant.setUser(user);
            attendant.setNumOfReservations(0);
            attendantRepository.save(attendant);
        }
    }

    public User login(LoginDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Korisnik ne postoji"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Pogrešna lozinka");
        }

        return user;
    }
}
