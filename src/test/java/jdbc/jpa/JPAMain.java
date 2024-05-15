package jdbc.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jdbc.jpa.user.Artist;

public class JPAMain {
    public static void main(String[] args) {

        try (var sessionFactory = Persistence.createEntityManagerFactory("jdbc.jpa.user");
             EntityManager entityManager = sessionFactory.createEntityManager()) {
            var transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(new Artist("Water"));
            Artist artist = entityManager.find(Artist.class, 213);
            artist.setArtistName("Muddy Waters");
            System.out.println(artist);
//            entityManager.remove(artist);
//            entityManager.persist(new Artist("Muddy Water"));
            transaction.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
