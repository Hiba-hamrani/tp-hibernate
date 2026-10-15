package ma.projet.classes;

import jakarta.persistence.*;
import java.util.List;

@Entity
@NamedQuery(name = "Produit.findByPrixSuperieur",
        query = "from Produit p where p.prix > :prix")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String reference;
    private float prix;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "produit")
    private List<LigneCommandeProduit> lignes;

    public Produit() {}

    public Produit(String reference, float prix, Categorie categorie) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
    }

    // Getters et setters (Alt + Insert)

    @Override
    public String toString() {
        return reference + " | " + prix + " DH";
    }

    public int getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public float getPrix() {
        return prix;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public List<LigneCommandeProduit> getLignes() {
        return lignes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public void setLignes(List<LigneCommandeProduit> lignes) {
        this.lignes = lignes;
    }

}