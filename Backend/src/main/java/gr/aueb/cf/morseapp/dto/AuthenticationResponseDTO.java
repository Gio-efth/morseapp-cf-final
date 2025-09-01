package gr.aueb.cf.morseapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponseDTO {

    private String token;
    private String username;
    private String role;
}