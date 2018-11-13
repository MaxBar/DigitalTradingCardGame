package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
