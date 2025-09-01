package gr.aueb.cf.morseapp.authentication;

import gr.aueb.cf.morseapp.dto.AuthenticationRequestDTO;
import gr.aueb.cf.morseapp.dto.AuthenticationResponseDTO;
import gr.aueb.cf.morseapp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponseDTO login(AuthenticationRequestDTO req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        UserDetails principal = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(principal);

        String role = principal.getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority())
                .orElse("USER");

        return new AuthenticationResponseDTO(token, principal.getUsername(), role);
    }
}
