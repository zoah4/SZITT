package szitt.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import szitt.enums.RoleEnum;
import szitt.enums.TypeEnum;
import szitt.model.Notification;
import szitt.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindNotification() {
        User user = createTestUser("test@example.com");

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType(TypeEnum.ZAHTJEV);
        notification.setContent("Ovo je test notifikacija");
        notification.setCreated(LocalDateTime.now());

        Notification saved = notificationRepository.save(notification);

        Notification found = notificationRepository.findById(saved.getId()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getContent()).isEqualTo("Ovo je test notifikacija");
        assertThat(found.getUser().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void shouldFindAllByUserId() {
        User user1 = createTestUser("user1@example.com");
        User user2 = createTestUser("user2@example.com");

        createTestNotification(user1, "Notifikacija 1 za user1");
        createTestNotification(user1, "Notifikacija 2 za user1");
        createTestNotification(user2, "Notifikacija za user2");

        Iterable<Notification> user1Notifications = notificationRepository.findAllByUserId(user1.getId());

        assertThat(user1Notifications).hasSize(2);
        assertThat(user1Notifications).allMatch(n -> n.getUser().getId().equals(user1.getId()));
    }

    @Test
    void shouldReturnEmptyListForNonExistentUser() {
        Iterable<Notification> notifications = notificationRepository.findAllByUserId(999L);
        assertThat(notifications).isEmpty();
    }

    private User createTestUser(String email) {
        User user = new User();
        user.setFirstname("Test");
        user.setLastname("User");
        user.setEmail(email);
        user.setPassword("password");
        user.setRole(RoleEnum.POLAZNIK);
        return userRepository.save(user);
    }

    private Notification createTestNotification(User user, String content) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType(TypeEnum.OPCENITO);
        notification.setContent(content);
        notification.setCreated(LocalDateTime.now());
        return notificationRepository.save(notification);
    }
}