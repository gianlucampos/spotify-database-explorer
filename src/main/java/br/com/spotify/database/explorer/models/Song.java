package br.com.spotify.database.explorer.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
public class Song {

    private String artist;
    private String album;
    private String name;


    public static List<Song> toDomain(String json) throws JsonProcessingException {
        List<Song> songs = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode root = mapper.readTree(json);
        JsonNode items = root.path("items");
        for (JsonNode item : items) {
            String artist = item.get("track").get("artists").findValue("name").asText();
            String album = item.get("track").get("album").get("name").asText();
            String track = item.get("track").get("name").asText();
            songs.add(new Song(artist, album, track));
        }
        return songs;
    }
}
