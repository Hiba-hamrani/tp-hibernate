package ma.projet.service;

import ma.projet.classes.*;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ProjetService extends AbstractService<Projet> {

    public ProjetService() { super(Projet.class); }

    // Tâches planifiées pour un projet
    public List<Tache> findTachesPlanifiees(Projet p) {
        try (Session s = openSession()) {
            return s.createQuery("from Tache t where t.projet.id = :id", Tache.class)
                    .setParameter("id", p.getId())
                    .list();
        }
    }

    // Tâches réalisées avec les dates réelles (affichage formaté)
    public void afficherTachesRealisees(int idProjet) {
        try (Session s = openSession()) {
            Projet p = s.get(Projet.class, idProjet);
            if (p == null) {
                System.out.println("Projet introuvable : " + idProjet);
                return;
            }
            List<EmployeTache> liste = s.createQuery(
                            "from EmployeTache et join fetch et.tache " +
                                    "where et.tache.projet.id = :id order by et.dateDebutReelle", EmployeTache.class)
                    .setParameter("id", idProjet)
                    .list();

            SimpleDateFormat longue = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
            SimpleDateFormat courte = new SimpleDateFormat("dd/MM/yyyy");

            System.out.println("Projet : " + p.getId() + "      Nom : " + p.getNom()
                    + "     Date début : " + longue.format(p.getDateDebut()));
            System.out.println("Liste des tâches:");
            System.out.printf("%-4s%-15s%-20s%s%n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");
            for (EmployeTache et : liste) {
                System.out.printf("%-4d%-15s%-20s%s%n",
                        et.getTache().getId(),
                        et.getTache().getNom(),
                        courte.format(et.getDateDebutReelle()),
                        courte.format(et.getDateFinReelle()));
            }
        }
    }
}