package gr.aueb.cf.morseapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInsertDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Invalid Password")
    @Pattern(
            regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$",
            message = "Invalid Password"
    )
    private String password;
}
