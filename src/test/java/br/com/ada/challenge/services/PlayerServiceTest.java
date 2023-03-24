package br.com.ada.challenge.services;

import br.com.ada.challenge.entities.Player;
import br.com.ada.challenge.repositories.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;
    @InjectMocks
    private PlayerService playerService;

    @Test
    void testFindByUsernameSuccess() {
        // GIVEN
        String username = "";
        Player player = mock(Player.class);

        // WHEN
        when(playerRepository.findByUsername(username)).thenReturn(player);
        var result = playerService.findByUsername(username);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(player, result.get());
    }
}