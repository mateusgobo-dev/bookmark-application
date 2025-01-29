package br.com.mgobo.web.controller;

import br.com.mgobo.api.services.BookmarkProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookmark")
@RequiredArgsConstructor
public class BookmarkProductController {

    private final BookmarkProductService productService;

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        try{
            return this.productService.findBookmarkProductByCustomerId(id);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
