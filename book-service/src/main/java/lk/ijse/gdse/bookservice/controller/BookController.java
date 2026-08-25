package lk.ijse.gdse.bookservice.controller;

import jakarta.validation.Valid;
import lk.ijse.gdse.bookservice.dto.BookDTO;
import lk.ijse.gdse.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/books/")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // Create Book
    @PostMapping("/save")
    public ResponseEntity<BookDTO> createBook(
            @Valid @ModelAttribute BookDTO bookDTO) {
//        System.out.println("book save");
//        System.out.println("=================================");
//        System.out.println("Content-Type: " + contentType);
//        System.out.println("ISBN       : " + bookDTO.getIsbn());
//        System.out.println("Title      : " + bookDTO.getTitle());
//        System.out.println("Image      : " + bookDTO.getImage());
//        System.out.println("=================================");

        BookDTO createdBook = bookService.createBookWithImage(bookDTO, bookDTO.getImage());
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    // Get All Books
    @GetMapping("/getAll")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Get Book by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // Get Book by ISBN
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    // Update Book
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    // Delete Book
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully!");
    }

    // Search Books by Title
    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam String title) {
        return ResponseEntity.ok(bookService.searchBooksByTitle(title));
    }

    // Get Books by Category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BookDTO>> getBooksByCategory(@PathVariable String category) {
        return ResponseEntity.ok(bookService.getBooksByCategory(category));
    }

    // Stock Management (Inter-service Communication / Feign Client Ready)
    @PatchMapping("/{id}/reduce-stock")
    public ResponseEntity<Boolean> updateStock(@PathVariable Long id, @RequestParam Integer quantity) {
        return ResponseEntity.ok(bookService.updateStock(id, quantity));
    }
}