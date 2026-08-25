package lk.ijse.gdse.borrowservice.service.impl;

import lk.ijse.gdse.borrowservice.client.BookClient;
import lk.ijse.gdse.borrowservice.client.UserClient;
import lk.ijse.gdse.borrowservice.dto.BookDTO;
import lk.ijse.gdse.borrowservice.dto.BorrowRequestDTO;
import lk.ijse.gdse.borrowservice.dto.BorrowResponseDTO;
import lk.ijse.gdse.borrowservice.entity.BorrowRecord;
import lk.ijse.gdse.borrowservice.entity.BorrowStatus;
import lk.ijse.gdse.borrowservice.repository.BorrowRepository;
import lk.ijse.gdse.borrowservice.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookClient bookClient;
    private final UserClient userClient;

    @Override
    @Transactional
    public BorrowResponseDTO borrowBook(BorrowRequestDTO requestDTO) {
        // 1. User ඉන්නවාදැයි පරීක්ෂා කිරීම (Feign Call)
        boolean userExists = userClient.existsById(requestDTO.getUserId());
        if (!userExists) {
            throw new RuntimeException("User not found with ID: " + requestDTO.getUserId());
        }

        // 2. Book එක තියෙනවාද සහ Quantity එක ඇතිදැයි පරීක්ෂා කිරීම (Feign Call)
        BookDTO book = bookClient.getBookById(requestDTO.getBookId());
        if (book == null) {
            throw new RuntimeException("Book not found with ID: " + requestDTO.getBookId());
        }
        if (book.getQuantity() <= 0) {
            throw new RuntimeException("Book is out of stock!");
        }

        // 3. Book Quantity එක 1කින් අඩු කිරීම (Feign Call)
        bookClient.reduceQuantity(requestDTO.getBookId(), 1);

        // 4. Borrow Record එක DB එකට Save කිරීම
        BorrowRecord record = BorrowRecord.builder()
                .userId(requestDTO.getUserId())
                .bookId(requestDTO.getBookId())
                .borrowDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14))
                .status(BorrowStatus.BORROWED)
                .build();

        BorrowRecord savedRecord = borrowRepository.save(record);
        return mapToDTO(savedRecord);
    }

    @Override
    @Transactional
    public BorrowResponseDTO returnBook(Long borrowId) {
        // 1. Borrow Record එක සොයා ගැනීම
        BorrowRecord record = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found with ID: " + borrowId));

        // 2. දැනටමත් Return කර ඇති එකක්දැයි පරීක්ෂා කිරීම
        if (record.getStatus() == BorrowStatus.RETURNED) {
            throw new RuntimeException("This book has already been returned!");
        }

        // 3. Status එක සහ Return Date එක Update කිරීම
        record.setReturnDate(LocalDate.now());
        record.setStatus(BorrowStatus.RETURNED);

        // 4. Book Service එකට කතා කර Quantity එක නැවත 1කින් වැඩි කිරීම (Feign Call)
        bookClient.restoreQuantity(record.getBookId(), 1);

        BorrowRecord updatedRecord = borrowRepository.save(record);
        return mapToDTO(updatedRecord);
    }

    @Override
    public List<BorrowResponseDTO> getBorrowHistoryByUserId(String userId) {
        return borrowRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowResponseDTO> getAllBorrowRecords() {
        return borrowRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private BorrowResponseDTO mapToDTO(BorrowRecord record) {
        return BorrowResponseDTO.builder()
                .id(record.getId())
                .userId(record.getUserId())
                .bookId(record.getBookId())
                .borrowDate(record.getBorrowDate())
                .dueDate(record.getDueDate())
                .returnDate(record.getReturnDate())
                .status(record.getStatus())
                .build();
    }
}