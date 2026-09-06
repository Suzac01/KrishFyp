package com.govweb.notification;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/notifications") public class NotificationController { private final NotificationRepository repo; public NotificationController(NotificationRepository repo){this.repo=repo;} @GetMapping public java.util.List<Notification> all(@RequestParam Long citizenId){return repo.findByRecipientIdOrderByCreatedAtDesc(citizenId);} @PutMapping("/{id}/read") public Notification read(@PathVariable Long id){var n=repo.findById(id).orElseThrow();n.setRead(true);return repo.save(n);} }
