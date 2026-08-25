package lk.ijse.gdse.borrowservice.service;

import lk.ijse.gdse.borrowservice.dto.BorrowRequestDTO;
import lk.ijse.gdse.borrowservice.dto.BorrowResponseDTO;

import java.util.List;

public interface BorrowService {
    BorrowResponseDTO borrowBook(BorrowRequestDTO requestDTO);
    BorrowResponseDTO returnBook(Long borrowId);
    List<BorrowResponseDTO> getBorrowHistoryByUserId(String userId);
    List<BorrowResponseDTO> getAllBorrowRecords();
}