package ma.projet.service;

import ma.projet.classes.*;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProduitService extends AbstractService<Produit> {

    public ProduitService() {
        super(Produit.class);
    }

    // 1. Produits par catégorie
    public List<Produit> findByCategorie(Categorie categorie) {
        try (Session s = openSession()) {
            return s.createQuery("from Produit p where p.categorie.id = :id", Produit.class)
                    .setParameter("id", categorie.getId())
                    .list();
        }
    }

    // 2. Produits commandés entre deux dates
    public List<Produit> findCommandesEntreDates(Date d1, Date d2) {
        try (Session s = openSession()) {
            return s.createQuery(
                            "select distinct l.produit from LigneCommandeProduit l " +
                                    "where l.commande.date between :d1 and :d2", Produit.class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
        }
    }

    // 3. Produits d'une commande donnée (affichage formaté)
    public void afficherProduitsCommande(int idCommande) {
        try (Session s = openSession()) {
            Commande c = s.get(Commande.class, idCommande);
            if (c == null) {
                System.out.println("Commande introuvable : " + idCommande);
                return;
            }
            List<LigneCommandeProduit> lignes = s.createQuery(
                            "from LigneCommandeProduit l join fetch l.produit " +
                                    "where l.commande.id = :id", LigneCommandeProduit.class)
                    .setParameter("id", idCommande)
                    .list();

            SimpleDateFormat f = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
            System.out.println("Commande : " + c.getId() + "     Date : " + f.format(c.getDate()));
            System.out.println("Liste des produits :");
            System.out.printf("%-12s%-10s%s%n", "Référence", "Prix", "Quantité");
            for (LigneCommandeProduit l : lignes) {
                System.out.printf("%-12s%-10s%d%n",
                        l.getProduit().getReference(),
                        String.format("%.0f DH", l.getProduit().getPrix()),
                        l.getQuantite());
            }
        }
    }

    // 4. Produits dont le prix est supérieur à une valeur (requête nommée)
    public List<Produit> findPrixSuperieur(float prix) {
        try (Session s = openSession()) {
            return s.createNamedQuery("Produit.findByPrixSuperieur", Produit.class)
                    .setParameter("prix", prix)
                    .list();
        }
    }
}