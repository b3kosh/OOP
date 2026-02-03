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
                songs.add(new Song(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("duration_seconds"),
                        artist,
                        rs.getString("surname")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public Song getSongById(int id) {
        String sql = "SELECT m.id, m.title, m.duration_seconds, a.name as artist_name, m.surname " +
                "FROM media_items m " +
                "JOIN songs s ON m.id = s.media_id " +
                "JOIN artists a ON s.artist_id = a.id " +
                "WHERE m.id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Artist artist = new Artist(rs.getString("artist_name"));
                return new Song(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("duration_seconds"),
                        artist,
                        rs.getString("surname")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertSong(Song song) {
        String findArtistSql = "SELECT id FROM artists WHERE name = ?";
        String insertArtistSql = "INSERT INTO artists (name) VALUES (?) RETURNING id";
        String insertMediaSql = "INSERT INTO media_items (title, duration_seconds, surname) VALUES (?, ?, ?) RETURNING id";
        String insertSongLinkSql = "INSERT INTO songs (media_id, artist_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            int artistId;
            PreparedStatement psArt = conn.prepareStatement(findArtistSql);
            psArt.setString(1, song.getArtist().getName());
            ResultSet rsArt = psArt.executeQuery();

            if (rsArt.next()) {
                artistId = rsArt.getInt(1);
            } else {
                PreparedStatement psNewArt = conn.prepareStatement(insertArtistSql);
                psNewArt.setString(1, song.getArtist().getName());
                ResultSet rsNewArt = psNewArt.executeQuery();
                rsNewArt.next();
                artistId = rsNewArt.getInt(1);
            }

            PreparedStatement psMed = conn.prepareStatement(insertMediaSql);
            psMed.setString(1, song.getTitle());
            psMed.setInt(2, song.getDurSeconds());
            psMed.setString(3, song.getSurname());
            ResultSet rsMed = psMed.executeQuery();

            if (rsMed.next()) {
                int newMediaId = rsMed.getInt(1);
                PreparedStatement psLink = conn.prepareStatement(insertSongLinkSql);
                psLink.setInt(1, newMediaId);
                psLink.setInt(2, artistId);
                psLink.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}