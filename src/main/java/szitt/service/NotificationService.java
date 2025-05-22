package szitt.service;

import org.springframework.stereotype.Service;
import szitt.dto.NotificationDTO;
import szitt.model.User;
import szitt.repository.NotificationRepository;
import szitt.model.Notification;
import szitt.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public Iterable<Notification> getAllNotifications() {
        return this.notificationRepository.findAll();
    }

    public Optional<Notification> getNotification(Long id) {
        return this.notificationRepository.findById(id);
    }

    public Iterable<Notification> getAllByUser(Long userId) {
        return this.notificationRepository.findAllByUserId(userId);
    }

    public void sendNotification(NotificationDTO dto) {
        User user = userRepository.findByEmail(dto.getUser().getEmail()).get();
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setContent(dto.getContent());
        notification.setType(dto.getType());
        notification.setCreated(LocalDateTime.now());

        notificationRepository.save(notification);
    }
}
