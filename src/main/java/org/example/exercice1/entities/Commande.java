package org.example.exercice1.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "commande")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double total;
    private LocalDate dateCommande;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "commande_produit",
            joinColumns = @JoinColumn(name = "commande_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id"))
    private List<Produit> produits;

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", total=" + total +
                ", dateCommande=" + dateCommande +
                '}';
    }
}
