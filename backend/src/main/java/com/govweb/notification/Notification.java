package com.govweb.notification;

import com.govweb.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Getter @Setter @NoArgsConstructor
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) private User recipient;
    @Column(nullable = false) private String title;
    @Column(length = 1000, nullable = false) private String message;
    private String link = "/documents";
    private boolean read;
    private LocalDateTime createdAt = LocalDateTime.now();
}
