package cap.test;

import cap.bean.Product;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.hibernate.Session;
import org.junit.Test;

public class HibernateAPITest {
    private SessionFactory sessionFactory;

    @Before
    public void init() {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @Test
    public void testSessionCache() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Product product1 = session.get(Product.class, 2);
        System.out.println(product1);
        Product product2 = session.get(Product.class, 1);
        System.out.println(product2);
        product2.setName("Lenovo");
        System.out.println(product2);
        session.flush();
        session.clear();
        product2.setPrice(365.1);
        transaction.commit();
        session.close();
    }

    @Test
    public void testObjectStatus() {
        Product product = new Product();
        product.setName("zch");
        product.setSerialNumber("1234");
        product.setPrice(1000);
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(product);
        System.out.println(product);
        tx.commit();
        session.close();

        System.out.println(product);
        Session session2 = sessionFactory.openSession();
        session2.beginTransaction();
        session2.save(product);
        System.out.println(product);
        session2.getTransaction().commit();
        session2.close();

    }

    @Test
    public void testDelete() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Product product = new Product();
        product.setId(2);
        session.delete(product);
        tx.commit();
        session.close();
    }

    @Test
    public void testUpdate() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Product product = session.get(Product.class, 3);
        product.setName("zfa");
        tx.commit();
        session.close();
    }

    @Test
    public void testSaveOrUpdate() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Product product = new Product();
        product.setId(1);
        product.setName("zch1");
        product.setSerialNumber("zch");
        product.setPrice(234.5);
        product.setStock(12);
        session.saveOrUpdate(product);
        tx.commit();
        session.close();
    }
}
