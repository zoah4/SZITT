package szitt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import szitt.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
    Iterable<Notification> findAllByUserId(Long userId);
}
