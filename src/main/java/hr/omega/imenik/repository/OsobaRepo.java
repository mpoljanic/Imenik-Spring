package hr.omega.imenik.repository;

import hr.omega.imenik.object.Osoba;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OsobaRepo extends CrudRepository<Osoba, Long>{

    Page<Osoba> findAll(Pageable pageable);
    List<Osoba> findByImeOrPrezimeOrKontaktBroj(String Ime, String Prezime, String Broj);

    Osoba findById(Long Id);
    Osoba save(Osoba osoba);

    void delete(Long Id);

}