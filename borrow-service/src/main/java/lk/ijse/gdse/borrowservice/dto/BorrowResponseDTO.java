package lk.ijse.gdse.borrowservice.dto;

import lk.ijse.gdse.borrowservice.entity.BorrowStatus;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowResponseDTO {

    private Long id;
    private String userId;
    private Long bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private BorrowStatus status;
}