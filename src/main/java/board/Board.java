package board;

import card.BasicCard;
import card.BasicCreatureCard;
import player.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    public final int PLAYER_A = 0;
    public final int PLAYER_B = 1;
    public final int maxTableSize = 5;
    private int turn;
    private int round;
    private Player[] players;

    public Board(){
        round = 0;
        turn = 0;
        players = new Player[2];
    }
    
    public int getTurn() {
        return turn;
    }
    
    public void setTurn(int turn) {
        if(this.turn == 0) {
            this.turn = turn;
        } else {
            this.turn = turn;
            ++round;
        }
    }

    public int checkTurnCombat() {
        int combat = turn;
        return combat == 1 ? 0 : 1;
    }
    
    public void increaseTurn(int increase) {
        if(turn == 0) {
            turn += increase;
        } else {
            turn = 0;
            ++round;
        }
    }
    
    public int getRound() {
        return round;
    }
    
    public void setRound(int round) {
        this.round = round;
    }
    
    public void increaseRound() {
        round += 1;
    }
    
    public Player[] getPlayers() {
        return players;
    }
    
    public void setPlayers(Player[] players) {
        this.players = players;
    }
    
    public void addPlayer(Player player, int pos) {
        players[pos] = player;
    }
}
