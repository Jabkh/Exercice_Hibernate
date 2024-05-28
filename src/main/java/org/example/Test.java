package org.example;

import org.example.entities.Produit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDate;
import java.util.Date;

public class Test {

    public static void main(String[] args) {
        StandardServiceRegistry registre = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registre).buildMetadata().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.getTransaction().begin();

        LocalDate dateAchat1 = LocalDate.of(2024, 04, 1);
        LocalDate dateAchat2 = LocalDate.of(2024, 04, 3);
        LocalDate dateAchat3 = LocalDate.of(2024, 04, 3);
        LocalDate dateAchat4 = LocalDate.of(2024, 04, 4);
        LocalDate dateAchat5 = LocalDate.of(2024, 04, 5);

        Produit p1 = new Produit();
        p1.setPrix(950.00);
        p1.setStock(35);
        p1.setMarque("X");
        p1.setReference("X01");
        p1.setDateAchat(dateAchat1);

        Produit p2 = new Produit();
        p2.setPrix(1950.00);
        p2.setStock(5);
        p2.setMarque("Y");
        p2.setReference("Y02");
        p2.setDateAchat(dateAchat2);

        Produit p3 = new Produit();
        p3.setPrix(850.00);
        p3.setStock(30);
        p3.setMarque("Z");
        p3.setReference("Z03");
        p3.setDateAchat(dateAchat3);

        Produit p4 = new Produit();
        p4.setPrix(1250.00);
        p4.setStock(40);
        p4.setMarque("W");
        p4.setReference("W04");
        p4.setDateAchat(dateAchat4);

        Produit p5 = new Produit();
        p5.setPrix(650.00);
        p5.setStock(15);
        p5.setMarque("V");
        p5.setReference("V05");
        p5.setDateAchat(dateAchat5);

        session.save(p1);
        session.save(p2);
        session.save(p3);
        session.save(p4);
        session.save(p5);

        session.getTransaction().commit();

        session.getTransaction().begin();

        Produit produitRecherche = session.load(Produit.class, 2);
        System.out.println("La marque de votre produit : "+produitRecherche.getMarque());

        Produit produitSuppression = session.load(Produit.class, 3);

        Produit produitModification = session.load(Produit.class, 1);

        produitModification.setMarque("MarqueModifiee");
        session.update(produitModification);
        System.out.println("La marque de votre produit après modification : "+produitModification.getMarque());

        session.delete(produitSuppression);
        System.out.println("Produit supprimé");

        session.getTransaction().commit();

        session.close();
        sessionFactory.close();

    }
}
