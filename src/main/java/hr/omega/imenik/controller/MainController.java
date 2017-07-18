package hr.omega.imenik.controller;


import com.cloudinary.Cloudinary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import hr.omega.imenik.object.Kontakt;
import hr.omega.imenik.object.Osoba;
import hr.omega.imenik.repository.KontaktRepo;
import hr.omega.imenik.repository.OsobaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/imenik")
public class MainController {

    @Autowired
    private OsobaRepo repoOsoba;

    @Autowired
    private KontaktRepo kontaktRepo;


    /**
     * /imenik/ metoda = GET
     * Dohvaća sve osobe u bazi.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page dohvatiSveOsobe(Pageable p) {
        return this.repoOsoba.findAll(p);
    }


    /**
     * /imenik/trazi/{kriterij} metoda = GET
     * Dohvaća sve osobe u bazi prema zadanom kriteriju (ime, prezime, kontakt_broj).
     */
    @RequestMapping(value = "/trazi/{kriterij}", method = RequestMethod.GET)
    public List<Osoba> dohvatiOsobu(@PathVariable("kriterij") String ImePrezimeBroj) {
        return repoOsoba.findByImeOrPrezimeOrKontaktBroj(ImePrezimeBroj, ImePrezimeBroj, ImePrezimeBroj);
    }


    /**
     * /imenik/osoba/{id} metoda = GET
     * Dohvaća osobu po zadanom kriteriju (id).
     */
    @RequestMapping(value = "/osoba/{id}", method = RequestMethod.GET)
    public Osoba dohvatiOsobuPoId(@PathVariable("id") String Id) {
        return repoOsoba.findById(Long.valueOf(Id).longValue());
    }


    /**
     * /imenik/obrisi/{id} metoda = POST
     * Brisanje osobe po zadanom kriteriju (id).
     */
    @RequestMapping(value = "/obrisi/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> obrisiOsobuPoId(@PathVariable("id") String Id) {

        repoOsoba.delete(Long.valueOf(Id).longValue());

        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }


    /**
     * /imenik/dodaj/ metoda = POST
     * Dodavanje osobe u bazu.
     */
    @RequestMapping(value="/dodaj" , method = RequestMethod.POST)
    public ResponseEntity<String> dodajOsobu(@RequestBody Osoba osoba) throws IOException, URISyntaxException {

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodedBytes = decoder.decodeBuffer(osoba.getSlika());

        Map uploadResult = getCloudinary().uploader().upload(decodedBytes, Cloudinary.emptyMap());

        String idSlika = (String) uploadResult.get("public_id");

        osoba.setSlika(idSlika);

        repoOsoba.save(osoba);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonKontakti = ow.writeValueAsString(osoba.getKontakt());

        ObjectMapper mapper = new ObjectMapper();

        Kontakt[] kontaktObjekti = mapper.readValue(jsonKontakti, Kontakt[].class);


        for (int i=0; i<kontaktObjekti.length; i++){
            kontaktObjekti[i].setOsoba(osoba);
            osoba.getKontakt().add(kontaktObjekti[i]);
            kontaktRepo.save(kontaktObjekti[i]);
        }

        return new ResponseEntity<String>(ow.writeValueAsString(osoba), HttpStatus.OK);
    }


    /**
     * /imenik/update/ metoda = POST
     * Update postojeće osobe.
     */
    @RequestMapping(value="/update" , method = RequestMethod.POST)
    public ResponseEntity<String> updateDetalji(@RequestBody Osoba osoba) throws IOException, URISyntaxException {

        Iterable<Kontakt> kontaktiZaObrisati = kontaktRepo.findByOsobaId(osoba.getId());

        kontaktRepo.delete(kontaktiZaObrisati);

        if (osoba.getSlika().length() > 25){
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] decodedSlika = decoder.decodeBuffer(osoba.getSlika());

            Map uploadResult = getCloudinary().uploader().upload(decodedSlika, Cloudinary.emptyMap());

            osoba.setSlika((String) uploadResult.get("public_id"));

            repoOsoba.save(osoba);

            System.out.println("test");

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            ObjectMapper mapper = new ObjectMapper();

            Kontakt[] kontaktObjekti = mapper.readValue(ow.writeValueAsString(osoba.getKontakt()), Kontakt[].class);


            for (int i=0; i<kontaktObjekti.length; i++){
                kontaktObjekti[i].setOsoba(osoba);
                osoba.getKontakt().add(kontaktObjekti[i]);
                kontaktRepo.save(kontaktObjekti[i]);
            }

            String jsonResponse = ow.writeValueAsString(osoba);

            return new ResponseEntity<String>(jsonResponse, HttpStatus.OK);
        }

        repoOsoba.save(osoba);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(osoba.getKontakt());

        ObjectMapper mapper = new ObjectMapper();

        Kontakt[] kontaktObjekti = mapper.readValue(json, Kontakt[].class);


        for (int i=0; i<kontaktObjekti.length; i++){
            kontaktObjekti[i].setOsoba(osoba);
            osoba.getKontakt().add(kontaktObjekti[i]);
            kontaktRepo.save(kontaktObjekti[i]);
        }

        String jsonResponse = ow.writeValueAsString(osoba);

        return new ResponseEntity<String>(jsonResponse, HttpStatus.OK);
    }


    private static Cloudinary getCloudinary() throws URISyntaxException {

        //String cloudinaryUri = System.getenv("CLOUDINARY_URL");               // Za Heroku

        String cloudinaryUri = "cloudinary://231752335958625:E_gpcJ-1k5Y65Tq_Iq7c1hM5VBo@hkq9ylmon"; // Za lokalnu upotrebu

        return new Cloudinary(cloudinaryUri);
    }


}
