package com.project;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.sqm.tree.AbstractSqmNode;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Manager {
    private static SessionFactory factory;

    public static void createSessionFactory() {
        try {
            Configuration conf = new Configuration();
            conf.addAnnotatedClass(Ciutat.class);
            conf.addAnnotatedClass(Ciutada.class);
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



    public static <T> String collectionToString(Class<? extends T> clazz, Collection<?> collection){
        String txt = "";
        for(Object obj: collection) {
            T cObj = clazz.cast(obj);
            txt += "\n" + cObj.toString();
        }
        if (txt.length() > 0 && txt.substring(0, 1).compareTo("\n") == 0) {
            txt = txt.substring(1);
        }
        return txt;
    }

    public static <T> Collection<?> listCollection(Class<? extends T> clazz, String where){
        Session session = factory.openSession();
        Transaction tx = null;
        Collection<?> result = null;
        try {
            tx = session.beginTransaction();
            if (where.length() == 0) {
                result = session.createQuery("FROM " + clazz.getName(), clazz).list(); // Added class parameter
            } else {
                result = session.createQuery("FROM " + clazz.getName() + " WHERE " + where, clazz).list();
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public static Ciutat getCiutatWithCiutadans(Long ciutatId) {
        Session session = factory.openSession();
        Ciutat ciutat = null;
        try {
            ciutat = session.get(Ciutat.class, ciutatId);

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return ciutat;
    }


    public static void updateCiutat(Long ciutatId,String nomCiutat, String pais,int poblacio, Set<Ciutada> ciutadans ){

        //obrir conexio
        Session session =factory.openSession();
        Transaction tx = null;
        try{
            //indiquem que iniciem la transaccio
            tx = session.beginTransaction();
            //fem una recerca de l'id de la ciutat a la bbdd
            Ciutat ciutat =session.get(Ciutat.class, ciutatId);
            if (ciutat==null){
                throw new RuntimeException("L'id de la ciutat no existeix");
            }
            //canviem de moment les dades de l'objecte
            ciutat.setNom(nomCiutat);
            ciutat.setPais(pais);
            ciutat.setPoblacio(poblacio);

            //si hi ha ciutadans existens a la bbdd s'eliminen
            if (ciutat.getCiutadans()!=null){
                for (Ciutada oldCiutada : new HashSet<>(ciutat.getCiutadans())){
                    ciutat.removeCiutada(oldCiutada);
                }
            }
            //si hi ha ciutadans per afegir s'afegeixen
            if (ciutadans!=null){
                for (Ciutada ciutada : ciutadans) {
                    Ciutada managedCiutada =session.get(Ciutada.class,ciutada.getCiutadaId());
                    if (managedCiutada!=null){
                        ciutat.addCiutada(managedCiutada);
                    }
                }
            }
            session.merge(ciutat);
            tx.commit();

        } catch (HibernateException e) {
            if(tx!=null){
                tx.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public static void updateCiutada(Long ciutadaId, String nom, String cognom, int edat) {
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Ciutada ciutada = session.get(Ciutada.class, ciutadaId);

            ciutada.setNom(nom);
            ciutada.setCognom(cognom);
            ciutada.setEdat(edat);

            session.merge(ciutada);
            tx.commit();
        }catch (HibernateException e){
            if (tx!=null){
                tx.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }

    }

    public static <T>void delete(Class <?extends T> clase, Long id) {
        Session session = factory.openSession();
        Transaction tx = null;
        T objecte = null;
        try{
            tx = session.beginTransaction();
            //aixo es fa perque pot ser un objecte qualsevol i d'aquesta manera s'obte la classe
            objecte = clase.cast(session.get(clase,id));
            if (objecte != null){
                session.remove(objecte);
                tx.commit();
            }
            
        }catch (HibernateException e){
            if (tx != null){
                tx.rollback();
                e.printStackTrace();
            }
        }finally {
            session.close();
        }
    }
}
