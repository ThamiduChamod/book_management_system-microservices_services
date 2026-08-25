package lk.ijse.gdse.borrowservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowRequestDTO {

    @NotNull(message = "User ID is required")
    private String userId;

    @NotNull(message = "Book ID is required")
    private Long bookId;
}