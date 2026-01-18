import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicDAO {


    private void ensureDefaultArtist(Connection conn) throws SQLException {
        String sql = "INSERT INTO artists (id, name, genre) VALUES (1, 'Default Artist', 'Various') ON CONFLICT (id) DO NOTHING";
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
        }
    }

    // 1. CREATE (Запись)
    public void saveSong(Song song) {
        String sqlMedia = "INSERT INTO media_items (title, duration_seconds) VALUES (?, ?) RETURNING id";
        String sqlSong = "INSERT INTO songs (media_id, artist_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            ensureDefaultArtist(conn);

            int mediaId;

            try (PreparedStatement ps = conn.prepareStatement(sqlMedia)) {
                ps.setString(1, song.getTitle());
                ps.setInt(2, song.getDurSeconds());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    mediaId = rs.getInt(1);
                    song.setId(mediaId); // Передаем ID из базы в Java-объект
                } else {
                    throw new SQLException("Failed to get generated ID");
                }
            }


            try (PreparedStatement ps = conn.prepareStatement(sqlSong)) {
                ps.setInt(1, song.getId());
                ps.setInt(2, 1); // Привязываем к артисту №1
                ps.executeUpdate();
            }

            conn.commit();
            System.out.println("✅ Saved with ID: " + song.getId());
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    // 2. READ (Чтение)
    public List<Song> getAllSongs() {
        List<Song> list = new ArrayList<>();
        String sql = "SELECT m.id, m.title, m.duration_seconds, a.name, a.genre " +
                "FROM media_items m " +
                "JOIN songs s ON m.id = s.media_id " +
                "JOIN artists a ON s.artist_id = a.id";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Artist art = new Artist(rs.getString("name"), rs.getString("genre"));
                Song s = new Song(rs.getString("title"), rs.getInt("duration_seconds"), art);
                s.setId(rs.getInt("id")); // Устанавливаем ID, чтобы Update/Delete работали
                list.add(s);
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при чтении: " + e.getMessage());
        }
        return list;
    }

    // 3. UPDATE (Обновление)
    public void updateTitle(int id, String newTitle) {
        String sql = "UPDATE media_items SET title = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newTitle);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("✅ Title updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. DELETE (Удаление)
    public void deleteMedia(int id) {
        String sql = "DELETE FROM media_items WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("✅ Media " + id + " deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}