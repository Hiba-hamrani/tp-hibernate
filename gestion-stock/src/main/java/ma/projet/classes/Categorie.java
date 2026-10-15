package ma.projet.classes;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    private String libelle;

    @OneToMany(mappedBy = "categorie")
    private List<Produit> produits;

    public Categorie() {}

    public Categorie(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public int getId() {
        return id;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public String getLibelle() {
        return libelle;
    }
// Getters et setters : Alt + Insert → Getter and Setter → tout sélectionner
}