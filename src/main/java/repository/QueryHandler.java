package repository;

import card.BasicCreatureCard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueryHandler {

    public int fetchPlayerId(String email) {
        String query = "SELECT id FROM Player WHERE Player.email = '" + email + "'";
        int playerId = 0;
        Statement st;
        ResultSet rs = null;
        try {
            st = Database.con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (rs.first()) {
                playerId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerId;
    }
    
    public String fetchPlayerName(String email) {
        String query = "SELECT name FROM Player WHERE Player.email = '" + email + "'";
        String playerName = "";
        Statement st;
        ResultSet rs = null;
        try {
            st = Database.con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        try {
            if (rs.first()) {
                playerName = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerName;
    }

    public String fetchPlayerEmail(String email) {
        String query = "SELECT email FROM Player WHERE Player.email = '" + email + "'";
        String playerEmail = "";
        Statement st;
        ResultSet rs = null;
        try {
            st = Database.con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (rs.first()) {
                playerEmail = rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerEmail;
    }

    public List<Integer> fetchDeckCreatureCardId(int playerId, int deckId) {
        String query = "SELECT card.id FROM CreatureCard card JOIN Deck deck ON deck.creatureCardId = card.id " +
                "WHERE deck.deckId = " + deckId + " AND deck.playerId = " + playerId + " AND deck.creatureCardId IS NOT NULL";
        List<Integer> ids = new ArrayList<>();
        Statement st;
        ResultSet rs = null;
        try {
            st = Database.con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public BasicCreatureCard fetchCreatureCardId(int id) {
        String query = "SELECT * FROM CreatureCard WHERE id = " + id;
        BasicCreatureCard card = null;
        Statement st;
        ResultSet rs = null;
        try {
            st = Database.con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (rs.first()) {
                card = new BasicCreatureCard(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("flavour"),
                        rs.getString("image"),
                        rs.getInt("mana"),
                        rs.getInt("health"),
                        rs.getInt("attackPower"),
                        rs.getInt("defensePower"),
                        rs.getInt("class"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }

    public List<Integer> fetchDeckMagicCardId(int playerId, int deckId) {
        String query = "SELECT id FROM MagicCard card JOIN Deck deck ON deck.magicCardId = card.id " +
                "WHERE deck.deckId = " + deckId + " AND deck.playerId = " + playerId + " AND deck.magicCardId IS NOT NULL";
        List<Integer> ids = new ArrayList<>();
        Statement st;
        ResultSet rs = null;
        try {
            st = Database.con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (rs.next()) {
                ids.add(rs.getInt("magicCardId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }
}
