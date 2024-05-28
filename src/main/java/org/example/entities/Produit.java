package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

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


}
