package hr.omega.imenik.object;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "kontakti")
public class Kontakt {

	private Integer kontaktId;
	private Osoba osoba;
    private String broj;
    private String opis;
    private String vrsta;

	public Kontakt() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KONTAKT_ID", unique = true, nullable = false)
	public Integer getKontaktId() {
		return this.kontaktId;
	}

	public void setKontaktId(Integer kontaktId) {
		this.kontaktId = kontaktId;
	}

    @ManyToOne
    @JoinColumn(name = "OSOBA_ID", insertable = true)
    @JsonIgnore
	public Osoba getOsoba() {
		return this.osoba;
	}

	public void setOsoba(Osoba osoba) {
		this.osoba = osoba;
	}

    @Column(name = "KONTAKT_BROJ",nullable = true, length = 20)
	public String getBroj() {
		return this.broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

    @Column(name = "KONTAKT_OPIS",nullable = false, length = 20)
	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

    @Column(name = "KONTAKT_VRSTA",nullable = false, length = 20)
    public String getVrsta() {
        return this.vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

}
