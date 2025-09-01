package gr.aueb.cf.morseapp.authentication;

import gr.aueb.cf.morseapp.core.enums.Role;
import gr.aueb.cf.morseapp.dto.AuthenticationRequestDTO;
import gr.aueb.cf.morseapp.dto.AuthenticationResponseDTO;
import gr.aueb.cf.morseapp.model.User;
import gr.aueb.cf.morseapp.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void login_success() {
        AuthenticationRequestDTO req = new AuthenticationRequestDTO("morseUser", "12345");

        User user = User.builder()
                .id(1L)
                .username("morseUser")
                .password("encoded")
                .role(Role.USER)
                .build();

        Authentication auth =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);

        when(jwtService.generateToken(any(UserDetails.class)))
                .thenReturn("jwt-token-123");

        AuthenticationResponseDTO resp = authenticationService.login(req);

        assertEquals("jwt-token-123", resp.getToken());
        assertEquals("morseUser", resp.getUsername());
        assertEquals("ROLE_USER", resp.getRole());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(user);
        verifyNoMoreInteractions(authenticationManager, jwtService);
    }

    @Test
    void login_userNotFound_like_principal_null_throws_NPE() {
        AuthenticationRequestDTO req = new AuthenticationRequestDTO("unknown", "pass");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(null, null));

        when(jwtService.generateToken(any())).thenThrow(new NullPointerException("principal is null"));

        assertThrows(NullPointerException.class, () -> authenticationService.login(req));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(null);
        verifyNoMoreInteractions(authenticationManager, jwtService);
    }

    @Test
    void login_badCredentials_throws() {
        AuthenticationRequestDTO req = new AuthenticationRequestDTO("morseUser", "wrong");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class, () -> authenticationService.login(req));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(jwtService);
    }
}
