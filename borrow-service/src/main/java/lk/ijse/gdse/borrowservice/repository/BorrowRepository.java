package lk.ijse.gdse.borrowservice.repository;

import lk.ijse.gdse.borrowservice.entity.BorrowRecord;
import lk.ijse.gdse.borrowservice.entity.BorrowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByUserId(String userId);
    List<BorrowRecord> findByBookId(Long bookId);
    Optional<BorrowRecord> findByUserIdAndBookIdAndStatus(Long userId, Long bookId, BorrowStatus status);
}