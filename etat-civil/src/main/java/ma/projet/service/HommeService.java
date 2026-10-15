package ma.projet.service;

import jakarta.persistence.criteria.*;
import ma.projet.beans.*;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HommeService extends AbstractService<Homme> {

    public HommeService() { super(Homme.class); }

    // Épouses d'un homme entre deux dates
    public List<Femme> findEpousesEntreDates(Homme h, Date d1, Date d2) {
        try (Session s = openSession()) {
            return s.createQuery(
                            "select m.femme from Mariage m " +
                                    "where m.homme.id = :id and m.dateDebut between :d1 and :d2", Femme.class)
                    .setParameter("id", h.getId())
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
        }
    }

    // API Criteria : nombre d'hommes mariés à 4 femmes entre deux dates
    public long countHommesMariesQuatreFemmes(Date d1, Date d2) {
        try (Session s = openSession()) {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
            Root<Mariage> m = cq.from(Mariage.class);

            cq.select(m.get("homme").get("id"))
                    .where(cb.between(m.<Date>get("dateDebut"), d1, d2))
                    .groupBy(m.get("homme").get("id"))
                    .having(cb.equal(cb.countDistinct(m.get("femme")), 4L));

            return s.createQuery(cq).list().size();
        }
    }

    // Mariages d'un homme avec tous les détails
    public void afficherMariages(int idHomme) {
        try (Session s = openSession()) {
            Homme h = s.get(Homme.class, idHomme);
            if (h == null) {
                System.out.println("Homme introuvable : " + idHomme);
                return;
            }
            List<Mariage> mariages = s.createQuery(
                            "from Mariage m join fetch m.femme where m.homme.id = :id order by m.dateDebut",
                            Mariage.class)
                    .setParameter("id", idHomme)
                    .list();

            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("Nom : " + h);

            System.out.println("Mariages En Cours :");
            int i = 1;
            for (Mariage m : mariages) {
                if (m.getDateFin() == null) {
                    System.out.printf("%d. Femme : %-15s Date Début : %s    Nbr Enfants : %d%n",
                            i++, nomComplet(m.getFemme()), f.format(m.getDateDebut()), m.getNbrEnfant());
                }
            }

            System.out.println("\nMariages échoués :");
            i = 1;
            for (Mariage m : mariages) {
                if (m.getDateFin() != null) {
                    System.out.printf("%d. Femme : %-15s Date Début : %s%n", i++,
                            nomComplet(m.getFemme()), f.format(m.getDateDebut()));
                    System.out.printf("   Date Fin : %s    Nbr Enfants : %d%n",
                            f.format(m.getDateFin()), m.getNbrEnfant());
                }
            }
        }
    }

    private String nomComplet(Femme femme) {
        return (femme.getPrenom() + " " + femme.getNom()).toUpperCase();
    }
}