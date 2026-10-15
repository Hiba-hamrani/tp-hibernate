package ma.projet.service;

import ma.projet.classes.*;
import org.hibernate.Session;
import java.util.List;

public class EmployeService extends AbstractService<Employe> {

    public EmployeService() { super(Employe.class); }

    // Tâches réalisées par un employé
    public List<Tache> findTachesRealisees(Employe e) {
        try (Session s = openSession()) {
            return s.createQuery(
                            "select et.tache from EmployeTache et where et.employe.id = :id", Tache.class)
                    .setParameter("id", e.getId())
                    .list();
        }
    }

    // Projets gérés par un employé (chef de projet)
    public List<Projet> findProjetsGeres(Employe e) {
        try (Session s = openSession()) {
            return s.createQuery("from Projet p where p.chefProjet.id = :id", Projet.class)
                    .setParameter("id", e.getId())
                    .list();
        }
    }
}