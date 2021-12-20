package br.com.spotify.database.explorer.controller;

import br.com.spotify.database.explorer.models.SpotifyApi;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Base64;
import java.util.Collections;

import static br.com.spotify.database.explorer.util.URLConstants.*;

@RestController
@RequestMapping
public class AuthorizationController {

    @Autowired
    private SpotifyApi spotifyApi;

    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public ResponseEntity<?> authorize() {
        URI uri = UriComponentsBuilder.fromHttpUrl(AUTHORIZE)
                .queryParam("client_id", spotifyApi.getClientId())
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("show_dialog", "true")
                .queryParam("scope", spotifyApi.getAuthorizationScope())
                .build()
                .encode()
                .toUri();
        System.out.println(uri);
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }

    @SneakyThrows
    @RequestMapping(value = "/token", produces = "application/json")
    public ResponseEntity<?> token(HttpServletRequest request) {
        String CODE = request.getQueryString().replace("code=", "");
        String AUTH = spotifyApi.getClientId() + ":" + spotifyApi.getClientSecret();
        String AUTHORIZATION = "Basic " + Base64.getEncoder().encodeToString(AUTH.getBytes());

        //Headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded");
        httpHeaders.set("Authorization", AUTHORIZATION);

        //Body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.put("grant_type", Collections.singletonList("authorization_code"));
        requestBody.put("code", Collections.singletonList(CODE));
        requestBody.put("redirect_uri", Collections.singletonList(REDIRECT_URI));
        requestBody.put("client_id", Collections.singletonList(spotifyApi.getClientId()));
        requestBody.put("client_secret", Collections.singletonList(spotifyApi.getClientSecret()));

        HttpEntity<Object> httpEntity = new HttpEntity<>(requestBody, httpHeaders);
        URI uri = UriComponentsBuilder.fromHttpUrl(TOKEN).build(false).toUri();
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

        //Get Json
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode root = mapper.readTree(response.getBody());
        String acess_token = root.findValue("access_token").asText();
        String refresh_token = root.findValue("refresh_token").asText();
        spotifyApi.setAcess_token(acess_token);
        spotifyApi.setRefresh_token(refresh_token);
        return response;
    }

    @SneakyThrows
    public ResponseEntity<?> refreshToken() {
        String AUTH = spotifyApi.getClientId() + ":" + spotifyApi.getClientSecret();
        String AUTHORIZATION = "Basic " + Base64.getEncoder().encodeToString(AUTH.getBytes());
        //Headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded");
        httpHeaders.set("Authorization", AUTHORIZATION);

        //Body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.put("grant_type", Collections.singletonList("refresh_token"));
        requestBody.put("refresh_token", Collections.singletonList(spotifyApi.getRefresh_token()));
        requestBody.put("client_id", Collections.singletonList(spotifyApi.getClientId()));

        HttpEntity<Object> httpEntity = new HttpEntity<>(requestBody, httpHeaders);
        URI uri = UriComponentsBuilder.fromHttpUrl(TOKEN).build(false).toUri();
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

        //Get Json
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode root = mapper.readTree(response.getBody());
        String acess_token = root.findValue("access_token").asText();
        String refresh_token = root.findValue("refresh_token").asText();
        spotifyApi.setAcess_token(acess_token);
        spotifyApi.setRefresh_token(refresh_token);
        return response;
    }

    @RequestMapping("/add/attribute/{name}")
    public void addCustomAtributte(HttpServletRequest request, @PathVariable String name) {
        request.getSession().setAttribute("ATRIBUTO", name);
    }

    @RequestMapping("/get/attribute")
    public ResponseEntity<?> getAtributte(HttpServletRequest request) {
        return ResponseEntity.ok(request.getSession().getAttribute("ATRIBUTO"));
    }

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public ResponseEntity<?> redirect() {
        String REDIRECT_URI = "http://127.0.0.1:8080/index.html";
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(REDIRECT_URI)).build();
    }

}
