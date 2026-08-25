package lk.ijse.gdse.bookservice.service.impl;

import lk.ijse.gdse.bookservice.dto.BookDTO;
import lk.ijse.gdse.bookservice.entity.Book;
import lk.ijse.gdse.bookservice.repository.BookRepository;
import lk.ijse.gdse.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public BookDTO createBookWithImage(BookDTO dto, MultipartFile imageFile) {
        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new RuntimeException("Book with ISBN " + dto.getIsbn() + " already exists!");
        }

        String imagePath = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imagePath = saveImageLocally(imageFile);
        }

        Book book = Book.builder()
                .isbn(dto.getIsbn())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .imageUrl(imagePath)
                .build();

        Book savedBook = bookRepository.save(book);
        return mapToDTO(savedBook);
    }

    private String saveImageLocally(MultipartFile file) {
        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Save with unique name
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image file", e);
        }
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found ID: " + id));
        return mapToDTO(book);
    }

    @Override
    public BookDTO getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found with ISBN: " + isbn));
        return mapToDTO(book);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setCategory(dto.getCategory());
        book.setPrice(dto.getPrice());
        book.setQuantity(dto.getQuantity());

        return mapToDTO(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with ID: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDTO> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksByCategory(String category) {
        return bookRepository.findByCategoryIgnoreCase(category).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateStock(Long id, Integer qtyToDeduct) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getQuantity() < qtyToDeduct) {
            throw new RuntimeException("Insufficient stock for book ID: " + id);
        }

        book.setQuantity(book.getQuantity() - qtyToDeduct);
        bookRepository.save(book);
        return true;
    }

    // Mapper Methods
    private Book mapToEntity(BookDTO dto) {
        return Book.builder()
                .id(dto.getId())
                .isbn(dto.getIsbn())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .build();
    }

    private BookDTO mapToDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .category(book.getCategory())
                .price(book.getPrice())
                .quantity(book.getQuantity())
                .imageUrl(book.getImageUrl())
                .build();
    }
}