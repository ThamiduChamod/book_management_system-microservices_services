package lk.ijse.gdse.borrowservice.client;

import lk.ijse.gdse.borrowservice.dto.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "BOOK-SERVICE", path = "/api/books")
public interface BookClient {

    @GetMapping("/{id}")
    BookDTO getBookById(@PathVariable("id") Long id);

    @PutMapping("/{id}/reduce-quantity")
        void reduceQuantity(@PathVariable("id") Long id, @RequestParam("quantity") int quantity);

    @PutMapping("/{id}/restore-quantity")
    void restoreQuantity(@PathVariable("id") Long id, @RequestParam("quantity") int quantity);
}