package br.com.mgobo.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "product")
@SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1, initialValue = 1)
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "product_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String title;

    @Column(nullable = false, length = 1000)
    private double price;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String category;
    private String image;

    @JoinColumn(name = "ratingId", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private Rating rating;
}
