package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryHandler {

    public String fetchPlayer(String email) {
        String query = "SELECT id FROM Player WHERE Player.email = '" + email + "'";
        String playerId = "";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = Database.con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (rs.first()) {
                playerId = rs.getString("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerId;
    }
}
