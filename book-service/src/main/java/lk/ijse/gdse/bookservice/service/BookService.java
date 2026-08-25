package lk.ijse.gdse.bookservice.service;

import lk.ijse.gdse.bookservice.dto.BookDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    // CRUD Operations
    BookDTO createBookWithImage(BookDTO bookDTO, MultipartFile imageFile);
    BookDTO getBookById(Long id);
    BookDTO getBookByIsbn(String isbn);
    List<BookDTO> getAllBooks();
    BookDTO updateBook(Long id, BookDTO bookDTO);
    void deleteBook(Long id);

    // Advanced / Microservice Features
    List<BookDTO> searchBooksByTitle(String title);
    List<BookDTO> getBooksByCategory(String category);
    boolean updateStock(Long id, Integer qtyToDeduct); // For Order Service integration
    void reduceQuantity(Long id, int quantity);
    void restoreQuantity(Long id, int quantity);
}