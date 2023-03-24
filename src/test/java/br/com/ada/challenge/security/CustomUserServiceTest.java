package br.com.ada.challenge.security;

import br.com.ada.challenge.entities.Player;
import br.com.ada.challenge.services.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserServiceTest {

    @Mock
    private PlayerService playerService;
    @InjectMocks
    private CustomUserService customUserService;

    @Test
    void testLoadUserByUsernameSuccess() {
        // GIVEN
        String username = "";
        Player player = new Player(UUID.randomUUID(), "username", "password");
        Optional<Player> optPlayer = Optional.of(player);

        // WHEN
        when(playerService.findByUsername(username)).thenReturn(optPlayer);
        var result = customUserService.loadUserByUsername(username);

        // THEN
        assertNotNull(result);
        assertEquals(player.getUsername(), result.getUsername());
        assertEquals(player.getPassword(), result.getPassword());
        assertEquals(1, result.getAuthorities().size());
    }

    @Test
    void testLoadUserByUsernameFailure() {
        // GIVEN
        String username = "";
        Optional<Player> optPlayer = Optional.empty();

        // WHEN
        when(playerService.findByUsername(username)).thenReturn(optPlayer);
        var exception = assertThrows(UsernameNotFoundException.class, () -> customUserService.loadUserByUsername(username));

        // THEN
        assertEquals("Usuário não criado ainda.", exception.getMessage());
    }
}