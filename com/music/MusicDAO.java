package com.music;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicDAO {

    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT m.id, m.title, m.duration_seconds, a.name as artist_name, m.surname " +
                "FROM media_items m " +
                "LEFT JOIN songs s ON m.id = s.media_id " +
                "LEFT JOIN artists a ON s.artist_id = a.id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Artist artist = new Artist(rs.getString("artist_name"));
                songs.add(new Song(rs.getInt("id"), rs.getString("title"),
                        rs.getInt("duration_seconds"), artist, rs.getString("surname")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return songs;
    }

    public void insertSong(Song song) {
        String findArtist = "SELECT id FROM artists WHERE name = ?";
        String insertArtist = "INSERT INTO artists (name) VALUES (?) RETURNING id";
        String insertMedia = "INSERT INTO media_items (title, duration_seconds, surname) VALUES (?, ?, ?) RETURNING id";
        String insertSongLink = "INSERT INTO songs (media_id, artist_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            int artistId;
            PreparedStatement psArt = conn.prepareStatement(findArtist);
            psArt.setString(1, song.getArtist().getName());
            ResultSet rsArt = psArt.executeQuery();

            if (rsArt.next()) {
                artistId = rsArt.getInt(1);
            } else {
                PreparedStatement psNewArt = conn.prepareStatement(insertArtist);
                psNewArt.setString(1, song.getArtist().getName());
                ResultSet rsNewArt = psNewArt.executeQuery();
                rsNewArt.next();
                artistId = rsNewArt.getInt(1);
            }

            PreparedStatement psMed = conn.prepareStatement(insertMedia);
            psMed.setString(1, song.getTitle());
            psMed.setInt(2, song.getDurSeconds());
            psMed.setString(3, song.getSurname());
            ResultSet rsMed = psMed.executeQuery();

            if (rsMed.next()) {
                int newMediaId = rsMed.getInt(1);
                PreparedStatement psLink = conn.prepareStatement(insertSongLink);
                psLink.setInt(1, newMediaId);
                psLink.setInt(2, artistId);
                psLink.executeUpdate();
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Song getSongById(int id) {
        String sql = "SELECT m.id, m.title, m.duration_seconds, a.name as artist_name, m.surname " +
                "FROM media_items m " +
                "LEFT JOIN songs s ON m.id = s.media_id " +
                "LEFT JOIN artists a ON s.artist_id = a.id " +
                "WHERE m.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Artist artist = new Artist(rs.getString("artist_name"));
                return new Song(rs.getInt("id"), rs.getString("title"),
                        rs.getInt("duration_seconds"), artist, rs.getString("surname"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }


    public void deleteSong(int id) {
        String deleteLink = "DELETE FROM songs WHERE media_id = ?";
        String deleteMedia = "DELETE FROM media_items WHERE id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps1 = conn.prepareStatement(deleteLink);
            ps1.setInt(1, id);
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(deleteMedia);
            ps2.setInt(1, id);
            ps2.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}