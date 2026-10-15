package ma.projet.util;

import ma.projet.beans.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties props = new Properties();
            InputStream in = HibernateUtil.class.getClassLoader()
                    .getResourceAsStream("application.properties");
            props.load(in);

            Configuration cfg = new Configuration();
            cfg.setProperties(props);
            cfg.addAnnotatedClass(Personne.class);
            cfg.addAnnotatedClass(Homme.class);
            cfg.addAnnotatedClass(Femme.class);
            cfg.addAnnotatedClass(Mariage.class);

            sessionFactory = cfg.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}