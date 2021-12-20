package br.com.spotify.database.explorer.controller;

import br.com.spotify.database.explorer.models.Song;
import br.com.spotify.database.explorer.models.SpotifyApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @see <a href="https://developer.spotify.com/console/library/">Library Docs</a>
 */
@RestController
@RequestMapping(value = "/library/tracks")
public class LibraryTracksController {

    @Autowired
    private SpotifyApi spotifyApi;

    private static final String URL_BASE = "https://api.spotify.com/v1/me/tracks";

    /**
     * @see <a href="https://developer.spotify.com/documentation/web-api/reference/#/operations/get-track">Liked Tracks</a>
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getLikedSongs() {
        try {
            RestTemplate restTemplate = new RestTemplateBuilder()
                    .defaultHeader("Authorization", "Bearer " + spotifyApi.getAcess_token())
                    .build();
            ResponseEntity<String> response = restTemplate.getForEntity(URL_BASE, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //TODO method addLikedSongs @PUT
    //TODO method removeLikedSongs @DELETE

}
