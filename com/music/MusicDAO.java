package com.music;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicDAO {

    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT m.id, m.title, m.duration_seconds, a.name as artist_name, m.surname " +
                "FROM media_items m LEFT JOIN songs s ON m.id = s.media_id " +
                "LEFT JOIN artists a ON s.artist_id = a.id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                songs.add(new Song(rs.getInt("id"), rs.getString("title"),
                        rs.getInt("duration_seconds"), new Artist(rs.getString("artist_name")), rs.getString("surname")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return songs;
    }

    public Song getSongById(int id) {
        String sql = "SELECT m.id, m.title, m.duration_seconds, a.name as artist_name, m.surname " +
                "FROM media_items m JOIN songs s ON m.id = s.media_id " +
                "JOIN artists a ON s.artist_id = a.id WHERE m.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Song(rs.getInt("id"), rs.getString("title"),
                        rs.getInt("duration_seconds"), new Artist(rs.getString("artist_name")), rs.getString("surname"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public String insertSong(Song song) {
        if (song.getTitle() == null || song.getTitle().trim().isEmpty()) return "Error: Title is empty";
        if (song.getDurSeconds() <= 0) return "Error: Duration must be > 0";

        try (Connection conn = DBConnection.getConnection()) {
            // Check Duplicates
            String check = "SELECT COUNT(*) FROM media_items WHERE title = ? AND surname = ?";
            PreparedStatement psCheck = conn.prepareStatement(check);
            psCheck.setString(1, song.getTitle());
            psCheck.setString(2, song.getSurname());
            ResultSet rsCheck = psCheck.executeQuery();
            if (rsCheck.next() && rsCheck.getInt(1) > 0) return "Error: Song already exists";

            // Find/Create Artist
            int artistId;
            PreparedStatement psArt = conn.prepareStatement("SELECT id FROM artists WHERE name = ?");
            psArt.setString(1, song.getArtist().getName());
            ResultSet rsArt = psArt.executeQuery();
            if (rsArt.next()) { artistId = rsArt.getInt(1); }
            else {
                PreparedStatement psNewArt = conn.prepareStatement("INSERT INTO artists (name) VALUES (?) RETURNING id");
                psNewArt.setString(1, song.getArtist().getName());
                ResultSet rsNewArt = psNewArt.executeQuery();
                rsNewArt.next();
                artistId = rsNewArt.getInt(1);
            }

            // Insert Media
            PreparedStatement psMed = conn.prepareStatement("INSERT INTO media_items (title, duration_seconds, surname) VALUES (?, ?, ?) RETURNING id");
            psMed.setString(1, song.getTitle());
            psMed.setInt(2, song.getDurSeconds());
            psMed.setString(3, song.getSurname());
            ResultSet rsMed = psMed.executeQuery();
            if (rsMed.next()) {
                int mediaId = rsMed.getInt(1);
                PreparedStatement psLink = conn.prepareStatement("INSERT INTO songs (media_id, artist_id) VALUES (?, ?)");
                psLink.setInt(1, mediaId);
                psLink.setInt(2, artistId);
                psLink.executeUpdate();
            }
            return "Success: Song added!";
        } catch (SQLException e) { return "DB Error: " + e.getMessage(); }
    }

    public void updateSong(int id, Song song) {
        String sql = "UPDATE media_items SET title = ?, duration_seconds = ?, surname = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, song.getTitle());
            ps.setInt(2, song.getDurSeconds());
            ps.setString(3, song.getSurname());
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteSong(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps1 = conn.prepareStatement("DELETE FROM songs WHERE media_id = ?");
            ps1.setInt(1, id);
            ps1.executeUpdate();
            PreparedStatement ps2 = conn.prepareStatement("DELETE FROM media_items WHERE id = ?");
            ps2.setInt(1, id);
            ps2.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}