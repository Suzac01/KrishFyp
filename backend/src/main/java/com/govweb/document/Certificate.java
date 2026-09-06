package com.govweb.document;

import com.govweb.application.GovApplication;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
public class Certificate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(unique = true, nullable = false) private String certificateNumber;
    @OneToOne(optional = false) private GovApplication application;
    private LocalDateTime issuedAt = LocalDateTime.now();
}
