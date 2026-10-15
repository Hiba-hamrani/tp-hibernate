package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;

import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) throws Exception {
        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService cms = new CommandeService();
        LigneCommandeService ls = new LigneCommandeService();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        // Données
        Categorie c1 = new Categorie("PC", "Ordinateurs");
        Categorie c2 = new Categorie("ACC", "Accessoires");
        cs.create(c1);
        cs.create(c2);

        Produit p1 = new Produit("ES12", 120, c1);
        Produit p2 = new Produit("ZR85", 100, c2);
        Produit p3 = new Produit("EE85", 200, c1);
        ps.create(p1);
        ps.create(p2);
        ps.create(p3);

        Commande cmd1 = new Commande(f.parse("14/03/2013"));
        Commande cmd2 = new Commande(f.parse("20/05/2013"));
        cms.create(cmd1);
        cms.create(cmd2);

        ls.create(new LigneCommandeProduit(p1, cmd1, 7));
        ls.create(new LigneCommandeProduit(p2, cmd1, 14));
        ls.create(new LigneCommandeProduit(p3, cmd1, 5));
        ls.create(new LigneCommandeProduit(p2, cmd2, 3));

        // Tests
        System.out.println("\n=== Produits de la catégorie " + c1.getLibelle() + " ===");
        ps.findByCategorie(c1).forEach(System.out::println);

        System.out.println("\n=== Produits commandés entre 01/03/2013 et 31/03/2013 ===");
        ps.findCommandesEntreDates(f.parse("01/03/2013"), f.parse("31/03/2013"))
                .forEach(System.out::println);

        System.out.println("\n=== Produits de la commande " + cmd1.getId() + " ===");
        ps.afficherProduitsCommande(cmd1.getId());

        System.out.println("\n=== Produits dont le prix > 100 DH ===");
        ps.findPrixSuperieur(100).forEach(System.out::println);
    }
}