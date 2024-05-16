package planet;

import client.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import planet.Planet;
import storage.HibernateUtil;
import ticket.Ticket;

import java.util.List;

public class PlanetCrudService {

    public Planet getById(String id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.get(Planet.class, id);
        }
    }

    public void create(String id, String name) {
        if (id == null || id.isEmpty() || name == null || name.isEmpty() || name.length() > 500) {
            throw new IllegalArgumentException("Invalid id or name");
        }

        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Planet planet = getPlanetByName(name,session);
            if (planet == null) {
                planet = new Planet();
            }
            planet.setId(id);
            planet.setName(name);

            session.persist(planet);
            transaction.commit();
        }
    }

    public void update(String id, String newName) {
        if (id == null || id.isEmpty() || newName == null || newName.isEmpty() || newName.length() > 500) {
            throw new IllegalArgumentException("Invalid id or name");
        }

        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Query<?> query = session.createQuery("update Planet set name = :newName where id = :id");
            query.setParameter("newName", newName);
            query.setParameter("id", id);
            transaction.commit();
        }
    }

    public void deleteById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }

        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Planet planetToDelete = session.get(Planet.class, id);

            if (planetToDelete != null) {

                Query<Ticket> ticketQuery = session.createQuery(
                        "FROM Ticket WHERE fromPlanet = :planet OR toPlanet = :planet", Ticket.class
                );
                ticketQuery.setParameter("planet", planetToDelete);
                List<Ticket> ticketsToDelete = ticketQuery.getResultList();

                for (Ticket ticket : ticketsToDelete) {
                    session.remove(ticket);
                }

                session.remove(planetToDelete);
            }

            transaction.commit();
        }
    }

    public void clear() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = "delete from Planet";
            Query query = session.createQuery(hql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Planet> getAll() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery("from Planet", Planet.class).list();
        }
    }

    private Planet getPlanetByName(String name, Session session) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        Query<Planet> query = session.createQuery("FROM Planet WHERE name = :name", Planet.class);
        query.setParameter("name", name);
        return query.uniqueResult();
    }
}