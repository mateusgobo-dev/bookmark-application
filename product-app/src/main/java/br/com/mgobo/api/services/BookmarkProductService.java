package br.com.mgobo.api.services;

import br.com.mgobo.api.entities.BookmarkProduct;
import br.com.mgobo.api.repositories.BookmarkProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkProductService {
    private final BookmarkProductRepository bookmarkProductRepository;

    public ResponseEntity<?> findBookmarkProductByCustomerId(Long customerId) {
        List<BookmarkProduct> bookmarkProducts = this.bookmarkProductRepository.findBookmarkProductByCustomerId(customerId);
        return !bookmarkProducts.isEmpty() ? ResponseEntity.ok(bookmarkProducts) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum favorito encontrado para o customer %s".formatted(customerId));
    }
}
