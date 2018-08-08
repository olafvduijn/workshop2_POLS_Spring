package genericDAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import utility.HibernateEntityManagerFactory;
import utility.Slf4j;

/**
 *
 * @author FeniksBV
 */
public class testGenericDAO {

    public static void main(String[] args) {

        Slf4j.getLogger().info("testGenericDAO started");

        // Verkrijg een entityManager
        EntityManager em = HibernateEntityManagerFactory.getEntityManager();
        // Verkrijg de DAO
        ArtikelDAOImpl artikelDAO = new ArtikelDAOImpl(em, Artikel.class);

        // Eerst maar eens alle records uit de gehele tabel verwijderen
        final ArrayList<Artikel> artikelList = artikelDAO.findAll();
        artikelList.forEach(instance -> {
            System.out.println("Deleting: " + instance.getNaam());
            artikelDAO.delete(instance);
        });
        em.clear();

        // Een artikel toevoegen
        Artikel artikel1 = new Artikel();
        artikel1.setNaam("Eerste test");
        artikel1.setPrijs(new BigDecimal(123.45));
        artikel1.setVoorraad(120);
        artikel1 = artikelDAO.create(artikel1);
        Long insertedId = artikel1.getId();

        // Nog een artikel toevoegen
        Artikel artikel2 = new Artikel();
        artikel2.setNaam("Tweede test");
        artikel2.setPrijs(new BigDecimal(11.99));
        artikel2.setVoorraad(30);
        artikel2 = artikelDAO.create(artikel2);

        // Lees het eerst toegevoegde artikel
        em.clear();
        artikel1 = artikelDAO.findById(Artikel.class, insertedId);
        if (artikel1 != null) {
            System.out.println("Gelezen artikel1 = " + artikel1.getId() + "  " + artikel1.getNaam());
        }

        // Close EntityManager (als alle acties uitgevoerd)
        em.close();

        // Close entityManager (bij Exit van de applicatie)
        HibernateEntityManagerFactory.closeEntityManagerFactory();

        Slf4j.getLogger().info("testGenericDAO ended");

    }

}
