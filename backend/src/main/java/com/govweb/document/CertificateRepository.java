package com.govweb.document;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CertificateRepository extends JpaRepository<Certificate, Long> { Optional<Certificate> findByApplicationId(Long applicationId); }
