package genericDAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.mindrot.jbcrypt.BCrypt;
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
        AccountDAOImpl accountDAO = new AccountDAOImpl(em, Account.class);

        //
        //  Eerst iets met accounts
        //
        Account acc1 = accountDAO.findByName(Account.class, "steff");
        if (acc1 != null) {
            System.out.println("Ja, gevonden, dus nu eerst delete!");
            accountDAO.delete(acc1);
        }

        em.clear();
        String pwd = BCrypt.hashpw("steff", BCrypt.gensalt(12));
        Account account = new Account("steff", pwd, Account.Rol.beheerder);
        account = accountDAO.create(account);

        //
        // Artikelen: Eerst maar eens alle records uit de gehele tabel verwijderen
        //
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

        List<Account> accountList = accountDAO.getKlantAccountsZonderKlant();
        for (int i = 0; i < accountList.size(); i++) {
            System.out.println("Accountnaam = " + accountList.get(i).getId() + " " + accountList.get(i).getUserNaam());
        }

        // Close EntityManager (als alle acties uitgevoerd)
        em.close();

        // Close entityManager (bij Exit van de applicatie)
        HibernateEntityManagerFactory.closeEntityManagerFactory();

        Slf4j.getLogger().info("testGenericDAO ended");

    }

}
