package br.com.spotify.database.explorer;

import br.com.spotify.database.explorer.models.Song;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
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

}
