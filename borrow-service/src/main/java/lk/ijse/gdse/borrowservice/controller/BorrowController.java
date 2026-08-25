package lk.ijse.gdse.borrowservice.controller;

import jakarta.validation.Valid;
import lk.ijse.gdse.borrowservice.dto.BorrowRequestDTO;
import lk.ijse.gdse.borrowservice.dto.BorrowResponseDTO;
import lk.ijse.gdse.borrowservice.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrows/")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    // 1. පොතක් Borrow කිරීම (POST - Raw JSON)
    @PostMapping("/save")
    public ResponseEntity<BorrowResponseDTO> borrowBook(@Valid @RequestBody BorrowRequestDTO requestDTO) {
        BorrowResponseDTO response = borrowService.borrowBook(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. පොතක් Return කිරීම (PUT)
    @PutMapping("/{id}/return")
    public ResponseEntity<BorrowResponseDTO> returnBook(@PathVariable("id") Long borrowId) {
        BorrowResponseDTO response = borrowService.returnBook(borrowId);
        return ResponseEntity.ok(response);
    }

    // 3. User කෙනෙකුගේ සියලුම Borrow History ලබාගැනීම (GET)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BorrowResponseDTO>> getHistoryByUser(@PathVariable("userId") String userId) {
        List<BorrowResponseDTO> history = borrowService.getBorrowHistoryByUserId(userId);
        return ResponseEntity.ok(history);
    }

    // 4. සියලුම Borrow Records ලබාගැනීම (GET)
    @GetMapping("/getAll")
    public ResponseEntity<List<BorrowResponseDTO>> getAllRecords() {
        return ResponseEntity.ok(borrowService.getAllBorrowRecords());
    }
}