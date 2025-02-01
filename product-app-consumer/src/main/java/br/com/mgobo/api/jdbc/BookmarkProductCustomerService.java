package br.com.mgobo.api.jdbc;

import br.com.mgobo.dto.BookmarkProductCustomerDto;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

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
    public String remove(BookmarkProductCustomerDto bookmarkProductCustomerDto) throws Exception {
        int rows = this.jdbcTemplate.update("DELETE FROM bookmark_product WHERE customer_id =? AND product_id = ?", bookmarkProductCustomerDto.idCustomer(), bookmarkProductCustomerDto.idProduct());
        if (rows >= 1) {
            return "Bookmark product customer successfully removed";
        }
        throw new Exception("Bookmark product customer not added");
    }

    public boolean sendEmail(BookmarkProductCustomerDto bookmarkProductCustomerDto) throws Exception {
        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            mailSender.setHost("sandbox.smtp.mailtrap.io");
            mailSender.setPort(2525);
            mailSender.setProtocol("smtp");
            mailSender.setUsername("edd41a70512377");
            mailSender.setPassword("27b5d018c48be2");
            helper.setTo(bookmarkProductCustomerDto.email());
            helper.setSubject("Inclusao de favorito - loja de produtos");
            helper.setText("Produto inserido na sua lista de favoritos.", true); // true for HTML content

            mailSender.send(message);
            LoggerFactory.getLogger(BookmarkProductCustomerService.class)
                    .info("Email enviado com sucesso...");
            return true;
        } catch (Exception e) {
            LoggerFactory.getLogger(BookmarkProductCustomerService.class).error("Erro ao enviar email {}", e.getMessage());
            throw new Exception(e);
        }
    }
}
