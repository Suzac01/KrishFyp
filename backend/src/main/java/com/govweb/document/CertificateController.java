package com.govweb.document;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/documents") public class CertificateController { private final CertificateRepository repo; public CertificateController(CertificateRepository repo){this.repo=repo;} @GetMapping public java.util.List<Certificate> all(@RequestParam Long citizenId){return repo.findAll().stream().filter(c->c.getApplication().getCitizen().getId().equals(citizenId)).toList();} }
