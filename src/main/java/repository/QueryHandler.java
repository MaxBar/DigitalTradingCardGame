package repository;

import card.BasicCreatureCard;
import card.BasicMagicCard;
import card.EKeyword;
import card.SpecialAbilityCreatureCard;

import javax.swing.plaf.nimbus.State;
import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueryHandler {

    public void saveWinner(int id) {
        String update = "UPDATE Player SET win = win + 1, score = score + 3 WHERE id = " + id;
        Statement st;

        try {
            st = Database.con.createStatement();
            st.executeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveLoser(int id) {
        String update = "UPDATE Player SET loss = loss + 1 WHERE id = " + id;
        Statement st;

        try {
            st = Database.con.createStatement();
            st.executeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
    
    // 0 = BasicCreatureCard
    // 1 = SpecialAbilityCreatureCard
    public int fetchCheckCardType (int id) {
        String query = "SELECT * FROM CreatureCard WHERE id = " + id;
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
                String text = rs.getString("specialAbility");
                if (rs.wasNull())
                    return 0;
                else
                    return 1;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return -1;
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
    
    public BasicMagicCard fetchMagicCardId(int id) {
        String query = "SELECT * FROM MagicCard WHERE id = " + id;
        BasicMagicCard card = null;
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
                card = new BasicMagicCard(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("flavour"),
                        rs.getString("image"),
                        rs.getInt("mana"),
                        EKeyword.valueOf(rs.getString("abilityName")),
                        rs.getString("abilityDescription"),
                        rs.getInt("abilityMin"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
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
    
    public SpecialAbilityCreatureCard fetchSpecialAbilityCreatureCardId (int id) {
        String query = "SELECT * FROM CreatureCard WHERE id = " + id;
        SpecialAbilityCreatureCard card = null;
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
                card = new SpecialAbilityCreatureCard(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("flavour"),
                        rs.getString("image"),
                        rs.getInt("mana"),
                        rs.getInt("health"),
                        rs.getInt("attackPower"),
                        rs.getInt("defensePower"),
                        rs.getInt("class"),
                        EKeyword.valueOf(rs.getString("specialAbilityName")),
                        rs.getString("specialAbility"),
                        rs.getInt("abilityMinValue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }

    public List<Integer> fetchDeckMagicCardId(int playerId, int deckId) {
        String query = "SELECT card.id FROM MagicCard card JOIN Deck deck ON deck.magicCardId = card.id " +
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
                ids.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }
}
