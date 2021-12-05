package br.com.spotify.database.explorer.controller;

import br.com.spotify.database.explorer.models.SongMapper;
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

    private static final String URL_BASE = "https://api.spotify.com/v1/me/tracks";
    private static final String SCOPE = "user-library-modify";

    /**
     * @see <a href="https://developer.spotify.com/documentation/web-api/reference/#/operations/get-track">Liked Tracks</a>
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getLikedSongs() {
        try {
//            String TOKEN = SecurityConfig.getAuthenticateToken();
            String TOKEN = "Bearer BQBVc0g9Hyu-KslViSuORFgBrJDqCPjmgT1hxb80a5UtE3-Oo7jXLVfe_NIQJcYYhyhblKzbLvAXJiSYh2zjf0xjF7v8pL7z38xM_sFlhd3HDKreAq7JJqY4GmIhoTD_pFQhAwDuSPfej2BMaGYFFDScyMbM2cdkZbojig7X-f9tG38ziaPZvV09dYp5VyFbNw";
            RestTemplate restTemplate = new RestTemplateBuilder()
                    .defaultHeader("Authorization", TOKEN)
                    .build();
            ResponseEntity<String> response = restTemplate.getForEntity(URL_BASE, String.class);
            return ResponseEntity.ok(SongMapper.toDomain(response.getBody()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //TODO method addLikedSongs @PUT
    //TODO method removeLikedSongs @DELETE

}
