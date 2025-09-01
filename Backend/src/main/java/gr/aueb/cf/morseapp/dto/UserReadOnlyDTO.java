package gr.aueb.cf.morseapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReadOnlyDTO {

    private String uuid;
    private String username;
    private String role;
}