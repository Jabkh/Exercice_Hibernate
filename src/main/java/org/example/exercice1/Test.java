package org.example.exercice1;

import org.example.exercice1.entities.Commentaire;
import org.example.exercice1.entities.Image;
import org.example.exercice1.entities.Produit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.hibernate.query.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        StandardServiceRegistry registre = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registre).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            LocalDate dateAchat1 = LocalDate.of(2024, 4, 1);
            LocalDate dateAchat2 = LocalDate.of(2024, 4, 3);
            LocalDate dateAchat3 = LocalDate.of(2024, 4, 3);
            LocalDate dateAchat4 = LocalDate.of(2024, 4, 4);
            LocalDate dateAchat5 = LocalDate.of(2024, 4, 5);


            Produit p1 = new Produit(950.00, 5, "X", "X01", dateAchat1);
            Produit p2 = new Produit(50.00, 5, "Y", "Y02", dateAchat2);
            Produit p3 = new Produit(850.00, 30, "Z", "Z03", dateAchat3);
            Produit p4 = new Produit(1250.00, 40, "W", "W04", dateAchat4);
            Produit p5 = new Produit(60.00, 15, "V", "V05", dateAchat5);

            session.save(p1);
            session.save(p2);
            session.save(p3);
            session.save(p4);
            session.save(p5);

            session.getTransaction().commit();

            session.beginTransaction();

            Scanner scanner = new Scanner(System.in);

            // Afficher la totalité des produits
            afficherListProduits(session);

            // Afficher les produits dont le prix est supérieur à 100 euros
            produitsPlusDe100Euros(session, 100);

            // Lire les dates au clavier pour afficher les produits achetés entre ces deux dates
//            System.out.println("Entrez la date de début (yyyy-MM-dd): ");
//            LocalDate dateDebut = LocalDate.parse(scanner.nextLine());
//            System.out.println("Entrez la date de fin (yyyy-MM-dd): ");
//            LocalDate dateFin = LocalDate.parse(scanner.nextLine());
//            produitsAchetePourUnePeriode(session, dateDebut, dateFin);

            // Lire la valeur du stock au clavier et retourner les numéros et références des articles dont le stock est inférieur à cette valeur
//            System.out.println("Entrez la valeur du stock: ");
//            int stockLimite = scanner.nextInt();
//            produitsStockLimite(session, stockLimite);

            // Afficher la valeur du stock des produits d'une marque choisie
//            scanner.nextLine();
            System.out.println("Entrez la marque des produits pour afficher le stock: ");
            String marque = scanner.nextLine();
            afficherStockParMarque(session, marque);

            // Calculer le prix moyen des produits
            calculerPrixMoyen(session);

            // Récupérer la liste des produits d'une marque choisie
            System.out.println("Entrez la marque des produits à lister: ");
            String marqueListe = scanner.nextLine();
            listerProduitsParMarque(session, marqueListe);

            // Supprimer les produits d'une marque choisie de la table produit
//            System.out.println("Entrez la marque des produits à supprimer: ");
//            String marqueSupprimer = scanner.nextLine();
//            supprimerProduitsParMarque(session, marqueSupprimer);

            // Ajout d'une image à un produit
            System.out.println("Entrez l'URL de l'image à ajouter: ");
            String imageUrl = scanner.nextLine();
            System.out.println("Entrez l'ID du produit auquel ajouter l'image: ");
            int productId = scanner.nextInt();
            Produit produit = session.get(Produit.class, productId);
            ajouterImage(session, produit, imageUrl);

            // Ajout d'un commentaire à un produit
            scanner.nextLine();
            System.out.println("Entrez le contenu du commentaire: ");
            String commentaireContenu = scanner.nextLine();
            System.out.println("Entrez la note du commentaire: ");
            int commentaireNote = scanner.nextInt();
            ajouterCommentaire(session, produit, commentaireContenu, commentaireNote);

            // Affichage des produits avec une note de 4 ou plus en commentaire
            afficherProduitsAvecNoteSuperieur(session, 4);

            session.getTransaction().commit();

        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    public static void afficherListProduits(Session session) {
        Query<Produit> produitQuery = session.createQuery("from Produit", Produit.class);
        List<Produit> produits = produitQuery.list();
        System.out.println("Tous les produits:");
        for (Produit produit : produits) {
            System.out.println(produit);
        }
    }

    public static void produitsPlusDe100Euros(Session session, double prix) {
        Query<Produit> produitQuery = session.createQuery("from Produit where prix > :prix", Produit.class);
        produitQuery.setParameter("prix", prix);
        List<Produit> produits = produitQuery.list();
        System.out.println("Produits avec un prix supérieur à " + prix + " euros:");
        for (Produit produit : produits) {
            System.out.println(produit);
        }
    }

    public static void produitsAchetePourUnePeriode(Session session, LocalDate dateDebut, LocalDate dateFin) {
        Query<Produit> produitQuery = session.createQuery("from Produit where dateAchat between :dateDebut and :dateFin", Produit.class);
        produitQuery.setParameter("dateDebut", dateDebut);
        produitQuery.setParameter("dateFin", dateFin);
        List<Produit> produits = produitQuery.list();
        System.out.println("Produits achetés entre " + dateDebut + " et " + dateFin + ":");
        for (Produit produit : produits) {
            System.out.println(produit);
        }
    }

    public static void produitsStockLimite(Session session, int stockLimite) {
        Query<Produit> produitQuery = session.createQuery("from Produit where stock < :stockLimite", Produit.class);
        produitQuery.setParameter("stockLimite", stockLimite);
        List<Produit> produits = produitQuery.list();
        System.out.println("Produits avec un stock inférieur à " + stockLimite + ":");
        for (Produit produit : produits) {
            System.out.println("Numéro: " + produit.getId() + ", Référence: " + produit.getReference());
        }
    }

    public static void afficherStockParMarque(Session session, String marque) {
        Query<Produit> produitQuery = session.createQuery("from Produit where marque = :marque", Produit.class);
        produitQuery.setParameter("marque", marque);
        List<Produit> produits = produitQuery.list();
        int totalStock = produits.stream().mapToInt(Produit::getStock).sum();
        System.out.println("La valeur du stock des produits de la marque " + marque + " est : " + totalStock);
    }

    public static void calculerPrixMoyen(Session session) {
        Query<Double> produitQuery = session.createQuery("select avg(prix) from Produit", Double.class);
        Double prixMoyen = produitQuery.uniqueResult();
        System.out.println("Le prix moyen des produits est : " + prixMoyen);
    }

    public static void listerProduitsParMarque(Session session, String marque) {
        Query<Produit> produitQuery = session.createQuery("from Produit where marque = :marque", Produit.class);
        produitQuery.setParameter("marque", marque);
        List<Produit> produits = produitQuery.list();
        System.out.println("Produits de la marque " + marque + " :");
        for (Produit produit : produits) {
            System.out.println(produit);
        }
    }

    public static void supprimerProduitsParMarque(Session session, String marque) {
        Query<Produit> produitQuery = session.createQuery("from Produit where marque = :marque", Produit.class);
        produitQuery.setParameter("marque", marque);
        List<Produit> produits = produitQuery.list();
        for (Produit produit : produits) {
            session.delete(produit);
        }
        System.out.println("Produits de la marque " + marque + " ont été supprimés.");
    }

    public static void ajouterImage(Session session, Produit produit, String url) {
        Image image = new Image();
        image.setUrl(url);
        image.setProduit(produit);
        produit.getImages().add(image);
        session.save(image);
        System.out.println("Image ajoutée avec succès au produit : " + produit.getId());
    }

    public static void ajouterCommentaire(Session session, Produit produit, String contenu, int note) {
        Commentaire commentaire = new Commentaire();
        commentaire.setContenu(contenu);
        commentaire.setNote(note);
        commentaire.setDateAjout(LocalDate.now());
        commentaire.setProduit(produit);
        produit.getCommentaires().add(commentaire);
        session.save(commentaire);
        System.out.println("Commentaire ajouté avec succès au produit : " + produit.getId());
    }

    public static void afficherProduitsAvecNoteSuperieur(Session session, int note) {
        Query<Produit> produitQuery = session.createQuery("select c.produit from Commentaire c where c.note >= :note", Produit.class);
        produitQuery.setParameter("note", note);
        List<Produit> produits = produitQuery.list();
        System.out.println("Produits avec une note supérieure ou égale à " + note + " :");
        for (Produit produit : produits) {
            System.out.println(produit);
        }
    }
}