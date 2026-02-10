package com.music;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/music")
public class MusicController {
    private final MusicDAO musicDAO = new MusicDAO();

    @GetMapping("/songs")
    public List<Song> getSongs() { return musicDAO.getAllSongs(); }

    @GetMapping("/songs/{id}")
    public Song getSong(@PathVariable int id) { return musicDAO.getSongById(id); }

    @PostMapping("/songs")
    public String addSong(@RequestBody Song song) { return musicDAO.insertSong(song); }

    @PutMapping("/songs/{id}")
    public String updateSong(@PathVariable int id, @RequestBody Song song) {
        musicDAO.updateSong(id, song);
        return "Updated!";
    }

    @DeleteMapping("/songs/{id}")
    public String deleteSong(@PathVariable int id) {
        musicDAO.deleteSong(id);
        return "Deleted!";
    }
}