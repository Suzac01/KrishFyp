package com.govweb.service;
import jakarta.persistence.*; import jakarta.validation.constraints.*; import lombok.*;
@Entity @Getter @Setter @NoArgsConstructor public class Service { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @NotBlank private String name; private String nepaliName; @Column(length=2000) private String description; private Integer fee; private Integer processingDays; private Boolean active=true; }
