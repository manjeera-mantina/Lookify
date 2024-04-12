package com.example.lookify.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.lookify.models.Song;
import com.example.lookify.repositories.SongRepo;

@Service
public class SongService {
	private final SongRepo songRepo;
	
	public SongService(SongRepo songRepo) {
		this.songRepo = songRepo;
	}
//	public void searchError(List<Song> songs, BindingResult result) {
//		if (songs.isEmpty()){
//			result.rejectValue("searchterm", "na", "No songs of this artist!");
//		}
//	}
	public List<Song> allSongs(){
		return songRepo.findAll();
	}
	
	public Song addSong(Song song) {
		return songRepo.save(song);
	}
	
	public Song findSong(Long id) {
		Optional<Song> optionalSong = songRepo.findById(id);
		if(optionalSong.isPresent()) {
			return optionalSong.get();
		}else {
			return null;
		}
	}
	public List<Song> findSongsByArtist(String artist) {
		List<Song> songs = songRepo.findByArtist(artist);
		if(songs.isEmpty()) {
			return null;
		}
		else {
			return songs;
		}
	}
	public List<Song> topFive(List<Song> songs) {
		return songRepo.getTopFive(songs);
	}
	public List<Song> sortByrating(List<Song> songs) {
		return songRepo.findByAndSort("rating", songs);
	}
	public Song updateSong(Song song) {
		return songRepo.save(song);
	}
	
	public void deleteSong(Song song) {
		songRepo.delete(song);
	}
}
