package ma.projet.test;

import ma.projet.beans.*;
import ma.projet.service.*;

import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) throws Exception {
        HommeService hs = new HommeService();
        FemmeService fs = new FemmeService();
        MariageService ms = new MariageService();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        // 1. Créer 10 femmes
        Femme salima = new Femme("RAMI", "Salima", "0611111111", "Marrakech", f.parse("12/05/1968"));
        Femme amal   = new Femme("ALI", "Amal", "0611111112", "Casablanca", f.parse("20/08/1972"));
        Femme wafa   = new Femme("ALAOUI", "Wafa", "0611111113", "Rabat", f.parse("03/02/1978"));
        Femme karima = new Femme("ALAMI", "Karima", "0611111114", "Fès", f.parse("15/11/1965"));
        Femme nadia  = new Femme("BENNANI", "Nadia", "0611111115", "Tanger", f.parse("07/07/1975"));
        Femme hind   = new Femme("TAZI", "Hind", "0611111116", "Agadir", f.parse("22/01/1980"));
        Femme sara   = new Femme("IDRISSI", "Sara", "0611111117", "Meknès", f.parse("30/09/1985"));
        Femme laila  = new Femme("FASSI", "Laila", "0611111118", "Oujda", f.parse("14/03/1970"));
        Femme imane  = new Femme("CHRAIBI", "Imane", "0611111119", "El Jadida", f.parse("05/12/1982"));
        Femme zineb  = new Femme("BERRADA", "Zineb", "0611111120", "Safi", f.parse("18/06/1990"));
        for (Femme fe : new Femme[]{salima, amal, wafa, karima, nadia, hind, sara, laila, imane, zineb}) {
            fs.create(fe);
        }

        // 2. Créer 5 hommes
        Homme said    = new Homme("SAFI", "Said", "0622222221", "Marrakech", f.parse("10/01/1960"));
        Homme ahmed   = new Homme("ALAOUI", "Ahmed", "0622222222", "Rabat", f.parse("25/04/1962"));
        Homme omar    = new Homme("BENNANI", "Omar", "0622222223", "Tanger", f.parse("08/08/1970"));
        Homme youssef = new Homme("TAZI", "Youssef", "0622222224", "Agadir", f.parse("17/10/1975"));
        Homme hamza   = new Homme("IDRISSI", "Hamza", "0622222225", "Meknès", f.parse("02/02/1980"));
        for (Homme ho : new Homme[]{said, ahmed, omar, youssef, hamza}) {
            hs.create(ho);
        }

        // Mariages
        ms.create(new Mariage(said, karima, f.parse("03/09/1989"), f.parse("03/09/1990"), 0)); // échoué
        ms.create(new Mariage(said, salima, f.parse("03/09/1990"), null, 4));
        ms.create(new Mariage(said, amal,   f.parse("03/09/1995"), null, 2));
        ms.create(new Mariage(said, wafa,   f.parse("04/11/2000"), null, 3));
        ms.create(new Mariage(ahmed, karima, f.parse("01/01/1992"), null, 2));  // Karima : 2e mariage
        ms.create(new Mariage(omar, nadia,  f.parse("15/06/1998"), null, 1));
        ms.create(new Mariage(youssef, hind, f.parse("10/05/2005"), f.parse("10/05/2010"), 1));
        ms.create(new Mariage(hamza, sara,  f.parse("20/07/2012"), null, 2));

        // 3. Liste des femmes
        System.out.println("=== Liste des femmes ===");
        fs.findAll().forEach(System.out::println);

        // 4. Femme la plus âgée
        System.out.println("\n=== Femme la plus âgée ===");
        System.out.println(fs.findPlusAgee());

        // 5. Épouses d'un homme entre deux dates
        System.out.println("\n=== Épouses de " + said + " entre 1985 et 2005 ===");
        hs.findEpousesEntreDates(said, f.parse("01/01/1985"), f.parse("31/12/2005"))
                .forEach(System.out::println);

        // 6. Nombre d'enfants d'une femme entre deux dates
        System.out.println("\n=== Nombre d'enfants de " + salima + " entre 1985 et 2020 ===");
        System.out.println(fs.nbrEnfantsEntreDates(salima, f.parse("01/01/1985"), f.parse("31/12/2020")));

        // 7. Femmes mariées deux fois ou plus
        System.out.println("\n=== Femmes mariées au moins deux fois ===");
        fs.findMarieesAuMoinsDeuxFois().forEach(System.out::println);

        // 8. Hommes mariés à quatre femmes entre deux dates
        System.out.println("\n=== Nombre d'hommes mariés à 4 femmes entre 1985 et 2005 ===");
        System.out.println(hs.countHommesMariesQuatreFemmes(f.parse("01/01/1985"), f.parse("31/12/2005")));

        // 9. Mariages d'un homme avec détails
        System.out.println("\n=== Mariages de " + said + " ===");
        hs.afficherMariages(said.getId());
    }
}