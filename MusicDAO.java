import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicDAO {

    private int getOrCreateArtist(Connection conn, String artistName) throws SQLException {
        String selectSql = "SELECT id FROM artists WHERE name = ?";
        try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
            ps.setString(1, artistName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }

        String insertSql = "INSERT INTO artists (name) VALUES (?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
            ps.setString(1, artistName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
    }

    public void saveSong(Song song) {
        String sqlMedia = "INSERT INTO media_items (title, duration_seconds) VALUES (?, ?) RETURNING id";
        String sqlSong = "INSERT INTO songs (media_id, artist_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            int artistId = getOrCreateArtist(conn, song.getArtist().getName());

            int mediaId;
            try (PreparedStatement ps = conn.prepareStatement(sqlMedia)) {
                ps.setString(1, song.getTitle());
                ps.setInt(2, song.getDurSeconds());
                ResultSet rs = ps.executeQuery();
                rs.next();
                mediaId = rs.getInt(1);
                song.setId(mediaId);
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlSong)) {
                ps.setInt(1, mediaId);
                ps.setInt(2, artistId);
                ps.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Song> getAllSongs() {
        List<Song> list = new ArrayList<>();
        String sql = "SELECT m.id, m.title, m.duration_seconds, a.name " +
                "FROM media_items m JOIN songs s ON m.id = s.media_id " +
                "JOIN artists a ON s.artist_id = a.id ORDER BY m.id";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Artist art = new Artist(rs.getString("name")); // Создаем без жанра
                Song s = new Song(rs.getString("title"), rs.getInt("duration_seconds"), art);
                s.setId(rs.getInt("id"));
                list.add(s);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public void deleteMedia(int id) {
        String sql = "DELETE FROM media_items WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}