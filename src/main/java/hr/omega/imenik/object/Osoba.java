package hr.omega.imenik.object;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "osoba")
public class Osoba {

	private Long Id;
	private String Ime;
	private String Prezime;
    private String Grad;
    private String Opis;
    private String Slika;
    private List<Kontakt> kontakt;

	public Osoba() {}

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "OSOBA_ID", unique = true)
	public Long getId() {
		return this.Id;
	}

	public void setId(Long Id) {
		this.Id = Id;
	}

    @Column(name = "OSOBA_IME", nullable = false, length = 20)
	public String getIme() {
		return this.Ime;
	}

	public void setIme(String Ime) {
		this.Ime = Ime;
	}

    @Column(name = "OSOBA_PREZIME", nullable = false, length = 20)
    public String getPrezime() {
        return this.Prezime;
    }

    public void setPrezime(String Prezime) {
        this.Prezime = Prezime;
    }

    @Column(name = "OSOBA_GRAD", nullable = false, length = 20)
    public String getGrad() {
        return this.Grad;
    }

    public void setGrad(String Grad) {
        this.Grad = Grad;
    }

    @Column(name = "OSOBA_OPIS", nullable = false, length = 20)
    public String getOpis() {
        return this.Opis;
    }

    public void setOpis(String Opis) {
        this.Opis = Opis;
    }

    @Column(name = "OSOBA_SLIKA", nullable = false, length = 60)
    public String getSlika() {
        return this.Slika;
    }

    public void setSlika(String Slika) {
        this.Slika = Slika;
    }

    @OneToMany( mappedBy = "osoba")
    public List<Kontakt> getKontakt() {
        return this.kontakt;
    }

    public void setKontakt(List<Kontakt> kontakt) {
        this.kontakt = kontakt;
    }

}
