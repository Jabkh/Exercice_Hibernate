package org.example.exercice1.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "commentaire")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Commentaire {
    @Id
    @GeneratedValue
    private int id;
    private String contenu;
    private int note;
    private LocalDate dateAjout;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "produit_id")
    private Produit produit;



    public Commentaire(String contenu, int note, LocalDate dateAjout, Produit produit) {
        this.contenu = contenu;
        this.note = note;
        this.dateAjout = dateAjout;
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                ", note=" + note +
                ", dateAjout=" + dateAjout +
                ", produit=" + produit +
                '}';
    }
}