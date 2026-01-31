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
        String insertMedia = "INSERT INTO media_items (title, duration_seconds, surname) VALUES (?, ?, ?) RETURNING id";
        String insertSongLink = "INSERT INTO songs (media_id, artist_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement ps1 = conn.prepareStatement(insertMedia);
            ps1.setString(1, song.getTitle());
            ps1.setInt(2, song.getDurSeconds());
            ps1.setString(3, song.getSurname());

            ResultSet rs = ps1.executeQuery();
            if (rs.next()) {
                int newMediaId = rs.getInt(1);


                PreparedStatement ps2 = conn.prepareStatement(insertSongLink);
                ps2.setInt(1, newMediaId);
                ps2.setInt(2, 1);
                ps2.executeUpdate();
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}