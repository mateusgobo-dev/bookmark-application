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
@Table(name = "rating")
@SequenceGenerator(name = "rating_seq", sequenceName = "rating_seq", allocationSize = 1, initialValue = 1)
public class Rating implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "rating_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private double rate;

    @Column(nullable = false)
    private int count;
}
