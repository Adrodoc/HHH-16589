package org.hibernate.bugs;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java
 * Persistence API.
 */
public class JPAUnitTestCase {

  private EntityManagerFactory entityManagerFactory;

  @Before
  public void init() {
    entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
  }

  @After
  public void destroy() {
    entityManagerFactory.close();
  }

  // Entities are auto-discovered, so just add them anywhere on class-path
  // Add your tests, using standard JUnit.
  @Test
  public void hhh16589Test() throws Exception {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Query query = entityManager.createQuery("select m from MyEntity m where m.id in ?1");
    List<Long> ids = LongStream.rangeClosed(1, 1900).mapToObj(it -> it).collect(Collectors.toList());
    query.setParameter(1, ids);
    query.getResultList();
    entityManager.getTransaction().commit();
    entityManager.close();
  }
}
