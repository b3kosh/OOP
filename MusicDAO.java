import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicDAO {

    // 1. CREATE
    public void saveSong(Song song) {
        String sqlMedia = "INSERT INTO media_items (title, duration_seconds) VALUES (?, ?) RETURNING id";
        String sqlSong = "INSERT INTO songs (media_id, artist_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Для безопасности данных (транзакция)

            int mediaId;
            try (PreparedStatement ps = conn.prepareStatement(sqlMedia)) {
                ps.setString(1, song.getTitle());
                ps.setInt(2, song.getDurSeconds());
                ResultSet rs = ps.executeQuery();
                rs.next();
                mediaId = rs.getInt(1);
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlSong)) {
                ps.setInt(1, mediaId);
                ps.setInt(2, 1); // Для упрощения ставим ID артиста = 1
                ps.executeUpdate();
            }
            conn.commit();
            System.out.println("✅ Song saved to DB!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. READ
    public void printAllSongs() {
        String sql = "SELECT m.id, m.title, m.duration_seconds FROM media_items m JOIN songs s ON m.id = s.media_id";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") + ": " + rs.getString("title") + " (" + rs.getInt("duration_seconds") + " sec)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. UPDATE
    public void updateTitle(int id, String newTitle) {
        String sql = "UPDATE media_items SET title = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newTitle);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("✅ Title updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. DELETE
    public void deleteMedia(int id) {
        String sql = "DELETE FROM media_items WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("✅ Media deleted (and its Song details too)!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}