package ma.projet.service;

import ma.projet.beans.Femme;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class FemmeService extends AbstractService<Femme> {

    public FemmeService() { super(Femme.class); }

    // Requête native nommée : nombre d'enfants d'une femme entre deux dates
    public int nbrEnfantsEntreDates(Femme femme, Date d1, Date d2) {
        try (Session s = openSession()) {
            Object result = s.createNamedQuery("Femme.nbrEnfantsEntreDates")
                    .setParameter("id", femme.getId())
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getSingleResult();
            return ((Number) result).intValue();
        }
    }

    // Requête nommée : femmes mariées au moins deux fois
    public List<Femme> findMarieesAuMoinsDeuxFois() {
        try (Session s = openSession()) {
            return s.createNamedQuery("Femme.marieesAuMoinsDeuxFois", Femme.class).list();
        }
    }

    // Femme la plus âgée
    public Femme findPlusAgee() {
        try (Session s = openSession()) {
            return s.createQuery("from Femme f order by f.dateNaissance asc", Femme.class)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }
}