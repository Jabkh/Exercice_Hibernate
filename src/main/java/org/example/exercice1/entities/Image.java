package org.example.exercice1.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "image")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue
    private int id;
    private String url;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", produit=" + produit +
                '}';
    }

    public Image(String url, Produit produit) {
        this.url = url;
        this.produit = produit;
    }
}
