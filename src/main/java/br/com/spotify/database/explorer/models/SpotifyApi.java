package br.com.spotify.database.explorer.models;

import br.com.spotify.database.explorer.util.AuthorizationScopes;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class SpotifyApi {

    @Value("${spotify.client.id}")
    private String clientId;
    @Value("${spotify.client.secret}")
    private String clientSecret;
    private String acess_token;
    private String refresh_token;
    private String authorizationScope = AuthorizationScopes.getScopes();

}
