package hr.omega.imenik.repository;

import hr.omega.imenik.object.Kontakt;
import org.springframework.data.repository.CrudRepository;

public interface KontaktRepo extends CrudRepository<Kontakt, Long>{

    Iterable<Kontakt> findByOsobaId(Long Id);
    void delete(Iterable<? extends Kontakt> kontakt);

}