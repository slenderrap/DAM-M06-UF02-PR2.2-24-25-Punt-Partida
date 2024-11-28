package com.project;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
public class Manager {
    private static SessionFactory factory;

    public static void createSessionFactory() {
        try {
            Configuration conf = new Configuration();
            conf.addResource("Ciutat.hbm.xml");
            conf.addResource("Ciutada.hbm.xml");
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(conf.getProperties()).build();
            factory = conf.buildSessionFactory(serviceRegistry);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public static void close(){factory.close();}

    public static Ciutat addCiutat(String nomCiutat, String nomPais, int poblacio) {
        Session session = factory.openSession();
        Transaction tx = null;
        Ciutat result = null;
        try {
            tx = session.beginTransaction();
            result= new Ciutat(nomCiutat,nomPais,poblacio);
            session.persist(result);
            tx.commit();
        }catch (HibernateException e){
            if (tx != null) tx.rollback();
            e.printStackTrace();
            result = null;
        }finally {
            session.close();
        }
        return result;

    }


    public static Ciutada addCiutada(String nom, String cognom, int edat) {
        Session session = factory.openSession();
        Transaction tx = null;
        Ciutada result = null;
        try {
            tx = session.beginTransaction();
            result = new Ciutada(nom, cognom, edat);
            session.persist(result);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            result = null;
        } finally {
            session.close();
        }
        return result;
    }

}
