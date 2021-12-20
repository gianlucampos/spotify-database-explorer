package br.com.spotify.database.explorer;

import br.com.spotify.database.explorer.models.Song;
import br.com.spotify.database.explorer.models.SpotifyApi;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

//@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    @Ignore
    void testMapper() throws Exception {
        File json = new File("C:\\Developer\\Projetos\\Java\\Pessoais\\Spring\\spotify-database-explorer\\src\\main\\resources\\response.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode name = root.get("items").findValue("track").findValue("name");
        System.out.println(name);
    }

    @Test
    @Ignore
    void testMapper2() throws Exception {
        File json = new File("C:\\Developer\\Projetos\\Java\\Pessoais\\Spring\\spotify-database-explorer\\src\\main\\resources\\response.json");
        Song song = new ObjectMapper().readValue(json, Song.class);
//        Song song = mapper.treeToValue(item.get("track"), Song.class);
        System.out.println(song);
    }

    @Test
    @Ignore
    void testMapper3() throws Exception {
        File json = new File("C:\\Developer\\Projetos\\Java\\Pessoais\\Spring\\spotify-database-explorer\\src\\main\\resources\\response.json");
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode root = mapper.readTree(json);
        JsonNode items = root.path("items");
        for (JsonNode item : items) {
            String artist = item.get("track").get("artists").findValue("name").asText();
            String album = item.get("track").get("album").get("name").asText();
            String track = item.get("track").get("name").asText();
            Song song = new Song(artist, album, track);
            System.out.println(song);
        }
    }

    @Test
    @Ignore
    void testTemplateURI() {
        String AUTHORIZE = "https://accounts.spotify.com/authorize";
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(AUTHORIZE)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", "localhost:8080/index.html")
                .queryParam("show_dialog", "true")
                .queryParam("scope", "scope=user-read-private user-read-email user-modify-playback-state user-read-playback-position user-library-read streaming user-read-playback-state user-read-recently-played playlist-read-private")
                .encode()
                .toUriString();
        System.out.println(urlTemplate);
        String REDIRECT_URI = "http://127.0.0.1:8080/index.html";
        String encoded = URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8);
        System.out.println(encoded);
    }

    @Test
    @Ignore
    void getCode() {
        String CODE = "code=AQBlKiKsxTN_eRn2JZza-KazfD5eU9Jn72eynqYEjY4roHDadskPGUw5zoIi3DrOYZAtaA4g2UdMtJrrnnCapRZSzJ9dLpB4ksYjMBS6uKWcYekXafH08XbM-Y3HN4z7naoXOaCa6F7RNwCYoBDgdK-vWxqWz8l4wYGooeUHw9TnwTjfq8BPjxZIp4qZEz-o9GMrMomS_IZ2nHy5-EmkNjQ5PJaocxfCCtBmRCXB96xFKN_wU9O1M0YNosKIOkV5E92qcaU5fxI39B_P5cLtopqXYIvinw-dUMhRPc-gBw8Z3VjmUQiriaCnNzchbjT6DrQH-u7RFbkutILgqB4PC6GlW2xhggvI_qTeR-Pxu23p7VRQfVGVhPdKSiskaigUY36f4xI0jZsBUIaJyf4-pPgFDpiAZmQksKF-K4_n4JNCTBaRdzo";
        CODE =CODE.replace("code=","");
        System.out.println(CODE);
    }

}
