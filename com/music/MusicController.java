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

    @PostMapping("/songs")
    public String addSong(@RequestBody Song song) {
        musicDAO.insertSong(song);
        return "Song '" + song.getTitle() + "' added successfully!";
    }
}