package br.com.mgobo.api.repositories;

import br.com.mgobo.api.entities.BookmarkProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkProductRepository extends JpaRepository<BookmarkProduct, Long> {
    List<BookmarkProduct> findBookmarkProductByCustomerId(Long customerId);
}
