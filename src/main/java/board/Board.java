package board;

import Game.Game;
import card.BasicCard;
import card.BasicCreatureCard;
import player.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    public final int PLAYER_A = 0;
    public final int PLAYER_B = 1;
    private int turn;
    private int round;
    private int maxTableSize;
    private Player[] players;
    private List<BasicCard> playerADeck;
    private List<BasicCard> playerBDeck;
    private List<BasicCard> playerAGraveyard;
    private List<BasicCard> playerBGraveyard;
    private List<BasicCard> playerATableCards = new ArrayList<>();
    private List<BasicCard> playerBTableCards = new ArrayList<>();

    Game game = Game.getInstance();

    private Board(){
        maxTableSize = 5;
        round = 1;
        players = new Player[2];
        playerAGraveyard = new ArrayList<>();
        playerBGraveyard = new ArrayList<>();
        playerADeck = new ArrayList<>(Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 0, 1, 2, 1, 2),
                new BasicCreatureCard(2, "Plopp", "Chocolate with gooey caramel center", "does not exist", 0, 2, 1, 1, 2),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 0, 5, 1, 1, 2),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 0, 1, 1, 1, 2),
                new BasicCreatureCard(5, "Djungelvrål", "Licorice candy that makes you scream", "does not exist yet", 0, 2, 2, 1, 2),
                new BasicCreatureCard(6, "Nick's", "Sugar-free candy", "does not exist yet", 0, 3, 3, 1, 2),
                new BasicCreatureCard(7, "Daim", "Chocolate with hard filling", "does not exist yet", 0, 3, 5, 1, 2),
                new BasicCreatureCard(8, "Bounty", "Chocolate with coconut filling", "does not exist yet", 0, 2, 1, 1, 2),
                new BasicCreatureCard(9, "Hubba Bubba", "Sweet chewing-gum", "does not exist yet", 0, 3, 3, 1, 2),
                new BasicCreatureCard(10, "Raisin", "Dried up grapes pretending to be candy", "does not exist yet", 0, 1, 1, 1, 2))
        );

        playerBDeck = new ArrayList<>(Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 0, 1, 2, 1, 2),
                new BasicCreatureCard(2, "Plopp", "Chocolate with gooey caramel center", "does not excist", 0, 2, 1, 1, 2),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 0, 5, 1, 1, 2),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 0, 1, 1, 1, 2),
                new BasicCreatureCard(5, "Djungelvrål", "Licorice candy that makes you scream", "does not exist yet", 0, 2, 2, 1, 2),
                new BasicCreatureCard(6, "Nick's", "Sugar-free candy", "does not exist yet", 0, 3, 3, 1, 2),
                new BasicCreatureCard(7, "Daim", "Chocolate with hard filling", "does not exist yet", 0, 3, 5, 1, 2),
                new BasicCreatureCard(8, "Bounty", "Chocolate with coconut filling", "does not exist yet", 0, 2, 1, 1, 2),
                new BasicCreatureCard(9, "Hubba Bubba", "Sweet chewing-gum", "does not exist yet", 0, 3, 3, 1, 2),
                new BasicCreatureCard(10, "Raisin", "Dried up grapes pretending to be candy", "does not exist yet", 0, 1, 1, 1, 2))
        );
        playerATableCards = new ArrayList<>();
        playerBTableCards = new ArrayList<>();
    }
}
