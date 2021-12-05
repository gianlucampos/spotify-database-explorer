package br.com.spotify.database.explorer.models;

import lombok.AllArgsConstructor;
import lombok.Data;

//@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
public class Song {

    private String artist;
    private String album;
    private String name;

}
