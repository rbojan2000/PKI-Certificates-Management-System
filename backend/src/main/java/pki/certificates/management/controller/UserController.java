package pki.certificates.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pki.certificates.management.dto.CertificateDTO;
import pki.certificates.management.service.implementations.UserService;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestParam("name") String name, @RequestParam("email") String email) {

        return ResponseEntity.ok("Korisnik " + name + " sa email adresom " + email + " je uspešno dodat!");
    }

    @GetMapping(path = "/getUserCertificates/{userID}")
    public List<CertificateDTO> getUserCertificates(@PathVariable("userID") String userID) {

        return userService.userCertificates(userID);
    }
}
