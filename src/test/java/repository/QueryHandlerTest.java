package repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QueryHandlerTest {
    @Mock
    QueryHandler queryHandler = new QueryHandler();
    
    @Test
    void saveWinner() {
        verify(queryHandler).saveWinner(8);
    }
    
    @Test
    void saveLoser() {
    }
    
    @Test
    void fetchPlayerId() {
    }
    
    @Test
    void fetchPlayerName() {
    }
    
    @Test
    void fetchPlayerEmail() {
    }
    
    @Test
    void fetchCheckCardType() {
    }
    
    @Test
    void fetchDeckCreatureCardId() {
    }
    
    @Test
    void fetchMagicCardId() {
    }
    
    @Test
    void fetchCreatureCardId() {
    }
    
    @Test
    void fetchSpecialAbilityCreatureCardId() {
    }
    
    @Test
    void fetchDeckMagicCardId() {
    }
}