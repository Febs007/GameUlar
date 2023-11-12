import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    // Atur informasi koneksi database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gameular";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Metode untuk menyimpan skor ke database
    public void saveScore(String playerName, int score) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO skor_tertinggi (Nama, Skor) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, playerName);
                preparedStatement.setInt(2, score);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateScore(String playerName, int newScore) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String updateQuery = "UPDATE skor_tertinggi SET Skor = ? WHERE Nama = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, newScore);
                preparedStatement.setString(2, playerName);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }

    
    public int getCurrentScore(String playerName) {
        int currentScore = 0; // Default value if no score is found

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Mendapatkan skor saat ini berdasarkan nama pemain
            String query = "SELECT Skor FROM skor_tertinggi WHERE Nama = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, playerName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        currentScore = resultSet.getInt("Skor");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }

        return currentScore;
    }
}
