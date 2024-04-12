package com.example.lookify.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.lookify.models.Song;
import com.example.lookify.services.SongService;

@Controller
public class SongController {
	
	@Autowired
	SongService songService;
	
	@GetMapping("/")
	public String index() {
		return "index.jsp";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(String artist, Model model) {
		List<Song> songs = songService.allSongs();
		model.addAttribute("songs", songs);
		return "dashboard.jsp";
	}
	
	@PostMapping("/search")
	public String search(@ModelAttribute("searchterm") String artist, Model model) {
		List<Song> filteredsongs = songService.findSongsByArtist(artist);
		if(filteredsongs != null) {
			model.addAttribute("songs", filteredsongs);
			model.addAttribute("searchterm", artist);
			return "songsbyartist.jsp";
		}
		else {
//			List<Song> songs = songService.allSongs();
//			model.addAttribute("songs", songs);
//			songService.searchError(songs, result);
			model.addAttribute("searchterm", artist);
			return "dashboard.jsp";
		}
	}
	
	@GetMapping("/songs/new")
	public String addSong(@ModelAttribute("song") Song song) {
		return "add.jsp";
	}
	
	@PostMapping("/songs/new")
	public String addSong(@Valid @ModelAttribute("song") Song song, BindingResult result) {
		if(result.hasErrors()) {
			return "add.jsp";
		}else {
			songService.addSong(song);
			return "redirect:/dashboard";
		}
	}
	
	@GetMapping("/songs/{id}")
	public String details(@PathVariable("id") Long id, Model model) {
		Song song = songService.findSong(id);
		model.addAttribute("song", song);
		return "details.jsp";
	}
	
	@GetMapping("/top-ten")
	public String details(Model model) {
		List<Song> allSongs = songService.allSongs();
		List<Song> sortedSongs = songService.sortByrating(allSongs);
		List<Song> topFivesongs = songService.topFive(sortedSongs);
		model.addAttribute("songs", topFivesongs);
		return "top.jsp";
	}
	
	@RequestMapping("/songs/{id}/delete")
	public String deleteSong(@PathVariable("id") Long id) {
		songService.deleteSong(songService.findSong(id));
		return "redirect:/dashboard";
	}
	
}
