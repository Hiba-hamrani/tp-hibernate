package ma.projet.service;

import ma.projet.classes.*;
import org.hibernate.Session;
import java.util.Date;
import java.util.List;

public class TacheService extends AbstractService<Tache> {

    public TacheService() { super(Tache.class); }

    // Tâches dont le prix > valeur (requête nommée)
    public List<Tache> findPrixSuperieur(double prix) {
        try (Session s = openSession()) {
            return s.createNamedQuery("Tache.findByPrixSuperieur", Tache.class)
                    .setParameter("prix", prix)
                    .list();
        }
    }

    // Tâches réalisées entre deux dates
    public List<Tache> findRealiseesEntreDates(Date d1, Date d2) {
        try (Session s = openSession()) {
            return s.createQuery(
                            "select distinct et.tache from EmployeTache et " +
                                    "where et.dateDebutReelle >= :d1 and et.dateFinReelle <= :d2", Tache.class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
        }
    }

}