package com.example.lookify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.lookify.models.Song;

@Repository
public interface SongRepo extends CrudRepository<Song, Long> {
	
	List<Song> findAll();
	List<Song> findByArtist(String artist);
	
	@Query(value = "SELECT * FROM songs", nativeQuery = true) 
	List<Song> getTopFive(List<Song> songs);
	@Query(value = "select song from Song song where song.rating like ?1%",nativeQuery = true)
	List<Song> findByAndSort(String rating,List<Song> songs); 
}
