package com.govweb.notification;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface NotificationRepository extends JpaRepository<Notification, Long> { List<Notification> findByRecipientIdOrderByCreatedAtDesc(Long recipientId); }
