package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractService<T> implements IDao<T> {

    private final Class<T> type;

    protected AbstractService(Class<T> type) {
        this.type = type;
    }

    protected Session openSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    private boolean executer(Consumer<Session> action) {
        Session session = openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            action.accept(session);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean create(T o) {
        return executer(s -> s.persist(o));
    }

    @Override
    public boolean update(T o) {
        return executer(s -> s.merge(o));
    }

    @Override
    public boolean delete(T o) {
        return executer(s -> s.remove(s.contains(o) ? o : s.merge(o)));
    }

    @Override
    public T findById(int id) {
        try (Session s = openSession()) {
            return s.get(type, id);
        }
    }

    @Override
    public List<T> findAll() {
        try (Session s = openSession()) {
            return s.createQuery("from " + type.getSimpleName(), type).list();
        }
    }
}