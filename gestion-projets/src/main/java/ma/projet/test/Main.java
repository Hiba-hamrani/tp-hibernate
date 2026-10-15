package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;

import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) throws Exception {
        EmployeService es = new EmployeService();
        ProjetService ps = new ProjetService();
        TacheService ts = new TacheService();
        EmployeTacheService ets = new EmployeTacheService();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        // Employés
        Employe e1 = new Employe("ALAMI", "Karim", "0600000001");
        Employe e2 = new Employe("RAMI", "Salma", "0600000002");
        es.create(e1);
        es.create(e2);

        // Projet (chef : e1)
        Projet p1 = new Projet("Gestion de stock", f.parse("14/01/2013"), f.parse("30/06/2013"), e1);
        ps.create(p1);

        // Tâches planifiées
        Tache t1 = new Tache("Analyse", f.parse("01/02/2013"), f.parse("15/02/2013"), 800, p1);
        Tache t2 = new Tache("Conception", f.parse("01/03/2013"), f.parse("15/03/2013"), 1500, p1);
        Tache t3 = new Tache("Développement", f.parse("01/04/2013"), f.parse("30/04/2013"), 3000, p1);
        ts.create(t1);
        ts.create(t2);
        ts.create(t3);

        // Réalisation des tâches par e2 (dates réelles)
        ets.create(new EmployeTache(e2, t1, f.parse("10/02/2013"), f.parse("20/02/2013")));
        ets.create(new EmployeTache(e2, t2, f.parse("10/03/2013"), f.parse("15/03/2013")));
        ets.create(new EmployeTache(e2, t3, f.parse("10/04/2013"), f.parse("25/04/2013")));

        // Tests
        System.out.println("\n=== Tâches réalisées par " + e2 + " ===");
        es.findTachesRealisees(e2).forEach(System.out::println);

        System.out.println("\n=== Projets gérés par " + e1 + " ===");
        es.findProjetsGeres(e1).forEach(System.out::println);

        System.out.println("\n=== Tâches planifiées du projet " + p1.getNom() + " ===");
        ps.findTachesPlanifiees(p1).forEach(System.out::println);

        System.out.println("\n=== Tâches réalisées avec dates réelles ===");
        ps.afficherTachesRealisees(p1.getId());

        System.out.println("\n=== Tâches dont le prix > 1000 DH ===");
        ts.findPrixSuperieur(1000).forEach(System.out::println);

        System.out.println("\n=== Tâches réalisées entre 01/03/2013 et 30/04/2013 ===");
        ts.findRealiseesEntreDates(f.parse("01/03/2013"), f.parse("30/04/2013"))
                .forEach(System.out::println);
    }
}