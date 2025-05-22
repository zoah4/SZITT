package szitt.controller;

import org.springframework.web.bind.annotation.*;
import szitt.dto.NotificationDTO;
import szitt.service.NotificationService;
import szitt.model.Notification;

import java.util.Optional;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService service;
    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Iterable<Notification> getAllNotifications() {
        return this.service.getAllNotifications();
    }

    @GetMapping("/{id}")
    public Optional<Notification> getNotification(@PathVariable Long id) {
        return this.service.getNotification(id);
    }

    @GetMapping("/user/{userId}")
    public Iterable<Notification> getAllByUser(@PathVariable Long userId) {
        return this.service.getAllByUser(userId);
    }

    @PostMapping("/send")
    public void sendNotification(@RequestBody NotificationDTO dto) {
        this.service.sendNotification(dto);
    }
}
