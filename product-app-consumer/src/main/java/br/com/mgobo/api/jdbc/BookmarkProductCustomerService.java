package br.com.mgobo.api.jdbc;

import br.com.mgobo.dto.BookmarkProductCustomerDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookmarkProductCustomerService {

    private final JdbcTemplate jdbcTemplate;
    public BookmarkProductCustomerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String save(BookmarkProductCustomerDto bookmarkProductCustomerDto) throws Exception {
        int rows = this.jdbcTemplate.update("INSERT INTO bookmark_product VALUES (nextval('bookmark_product_seq'),?,?)", bookmarkProductCustomerDto.idCustomer(), bookmarkProductCustomerDto.idProduct());
        if (rows >= 1) {
            return "Bookmark product customer successfully added";
        }
        throw new Exception("Bookmark product customer not added");
    }
}
