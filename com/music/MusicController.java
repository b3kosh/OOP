package com.music;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/music")
public class MusicController {
    private final MusicDAO musicDAO = new MusicDAO();

    @GetMapping("/songs")
    public List<Song> getSongs() {
        return musicDAO.getAllSongs();
    }

    @GetMapping("/songs/{id}")
    public Song getSongById(@PathVariable int id) {
        return musicDAO.getSongById(id);
    }

    @DeleteMapping("/songs/{id}")
    public String deleteSong(@PathVariable int id) {
        musicDAO.deleteSong(id);
        return "Song with ID " + id + " was successfully deleted!";
    }

    @PostMapping("/songs")
    public String addSong(@RequestBody Song song) {
        musicDAO.insertSong(song);
        return "Song '" + song.getTitle() + "' added successfully!";
    }
}