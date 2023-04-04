package pki.certificates.management.service.implementations;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.stereotype.Component;
import pki.certificates.management.model.Issuer;
import pki.certificates.management.model.Subject;

import java.security.*;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CertificateExample {

    public Subject generateSubject() {
        KeyPair keyPairSubject = generateKeyPair();

        //klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);

        builder.addRDN(BCStyle.CN, "My Root CA");
        builder.addRDN(BCStyle.O, "My Organisation");
        builder.addRDN(BCStyle.OU, "My Unit");
        builder.addRDN(BCStyle.L, "Novi Sad");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "my_root_ca@mail.rs");
        //UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, "123456");

        return new Subject(keyPairSubject.getPublic(), builder.build());
    }

    public Issuer generateIssuer() {
        KeyPair kp = generateKeyPair();
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "IT sluzba");
        builder.addRDN(BCStyle.SURNAME, "sluzba");
        builder.addRDN(BCStyle.GIVENNAME, "IT");
        builder.addRDN(BCStyle.O, "UNS-FTN");
        builder.addRDN(BCStyle.OU, "Katedra za informatiku");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "itsluzba@uns.ac.rs");
        //UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, "654321");

        //Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
        // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
        // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
        return new Issuer(kp.getPrivate(), kp.getPublic(), builder.build());
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public pki.certificates.management.model.Certificate getCertificate() {

        try {
            Issuer issuer = generateIssuer();
            Subject subject = generateSubject();

            //Datumi od kad do kad vazi sertifikat
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse("2023-03-25");
            Date endDate = sdf.parse("2028-03-25");

            X509Certificate certificate = CertificateGenerator.generateCertificate(subject,
                    issuer, startDate, endDate, "1");

            return new pki.certificates.management.model.Certificate(subject, issuer,
                    "1", startDate, endDate, certificate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


}
