package szitt.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import szitt.dto.AttendantDTO;
import szitt.dto.InstructorDTO;
import szitt.model.User;
import szitt.repository.AttendantRepository;
import szitt.model.Attendant;

import java.util.Optional;


@Service
public class AttendantService {
    private final PasswordEncoder passwordEncoder;
    private final AttendantRepository repository;
    public AttendantService(AttendantRepository repository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    public Iterable<Attendant> getAllAttendants() {
        return this.repository.findAll();
    }

    public Optional<Attendant> getAttendant(Long id) {
        return this.repository.findById(id);
    }

    public void editProfile(Long id, AttendantDTO dto) {
        Attendant attendant = this.repository.findById(id).get();

        User user = attendant.getUser();
        user.setFirstname(dto.getAttendant().getFirstname());
        user.setLastname(dto.getAttendant().getLastname());
        user.setEmail(dto.getAttendant().getEmail());
        user.setPassword(passwordEncoder.encode(dto.getAttendant().getPassword()));

        attendant.setUser(user);

        this.repository.save(attendant);
    }
}
