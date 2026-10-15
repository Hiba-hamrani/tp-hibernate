package ma.projet.classes;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_commande")
    private Date date;

    public List<LigneCommandeProduit> getLignes() {
        return lignes;
    }

    @OneToMany(mappedBy = "commande")
    private List<LigneCommandeProduit> lignes;

    public Commande() {}

    public Commande(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLignes(List<LigneCommandeProduit> lignes) {
        this.lignes = lignes;
    }
}
// Getters et setters (Alt + Insert)
