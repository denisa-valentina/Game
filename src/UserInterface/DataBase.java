package UserInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    public static DataBase dataBaseInstance;
    private Connection c;
    private Statement stmt;

    private DataBase() {
        c = null;
        stmt = null;
    }

    public static DataBase getInstance() {
        if (DataBase.dataBaseInstance == null) {
            dataBaseInstance = new DataBase();
        }
        return dataBaseInstance;
    }

    public void saveGame(int levelIndex, int hearts, int score, int xPos, int yPos)
    {
        System.out.println("Saving game");

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Game.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            // stergem inregistrarile anterioare, nu salvam decat ultima "evolutie"
            String sql = "DELETE from GAME;";
            try (Statement stmt = c.createStatement()) {
                stmt.executeUpdate(sql);
            }

            sql = "INSERT INTO GAME (LEVEL_INDEX,HEARTS,SCORE,X_POSITION,Y_POSITION) " +
                    "VALUES (?, ?, ?, ?, ?);";
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setInt(1, levelIndex);
                pstmt.setInt(2, hearts);
                pstmt.setInt(3, score);
                pstmt.setInt(4, xPos);
                pstmt.setInt(5, yPos);
                pstmt.executeUpdate();
            }
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println(STR."\{e.getClass().getName()}: \{e.getMessage()}");
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public List<Integer> loadGame() {
        List<Integer> loadGameValues = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Game.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM GAME;");
            loadGameValues.add(rs.getInt("LEVEL_INDEX"));
            loadGameValues.add(rs.getInt("HEARTS"));
            loadGameValues.add(rs.getInt("SCORE"));
            loadGameValues.add(rs.getInt("X_POSITION"));
            loadGameValues.add(rs.getInt("Y_POSITION"));

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(STR."\{e.getClass().getName()}: \{e.getMessage()}");
            System.exit(0);
        }
        return loadGameValues;
    }
}
