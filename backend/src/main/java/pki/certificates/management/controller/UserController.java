package pki.certificates.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pki.certificates.management.dto.CertificateDto;
import pki.certificates.management.model.User;
import pki.certificates.management.service.implementations.UserService;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok("Korisnik je uspešno dodat!");
    }

    @GetMapping(path = "/getUserCertificates/{userID}")
    public List<CertificateDto> getUserCertificates(@PathVariable("userID") String userID) {
        userID = "642dafe9d9e299372bb8c612";
        return userService.userCertificates(userID);
    }
}
