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
@Table(name = "bookmark_product")
@SequenceGenerator(sequenceName = "bookmark_product_seq", name = "bookmark_product_seq", initialValue = 1, allocationSize = 1)
public class BookmarkProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "bookmark_product_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @JoinColumn(name = "productId", referencedColumnName = "id")
    @ManyToOne
    private Product product;
    private Long customerId;
}
