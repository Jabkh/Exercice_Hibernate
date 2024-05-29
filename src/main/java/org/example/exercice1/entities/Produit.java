package org.example.exercice1.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produit")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Produit {

    @Id
    @GeneratedValue

    private int id;
    private String marque;
    private String reference;
    private LocalDate dateAchat;
    private double prix;
    private int stock;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentaire> commentaires = new ArrayList<>();


    public Produit(double prix, int stock, String marque, String reference, LocalDate dateAchat) {
        this.prix = prix;
        this.stock = stock;
        this.marque = marque;
        this.reference = reference;
        this.dateAchat = dateAchat;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", marque='" + marque + '\'' +
                ", reference='" + reference + '\'' +
                ", dateAchat=" + dateAchat +
                ", prix=" + prix +
                ", stock=" + stock +
                '}';
    }
}
