package br.com.spotify.database.explorer.util;

import java.util.Arrays;
import java.util.List;

public class AuthorizationScopes {

    private static final List<String> scopes = Arrays.asList(
            "user-read-private",
            "user-read-recently-played",
            "user-library-read",
            "user-library-modify",
            "playlist-read-private",
            "playlist-modify-public",
            "streaming app-remote-control"
    );

    public static String getScopes() {
        StringBuilder scope = new StringBuilder();
        for (String s : scopes) {
            scope.append(s).append(" ");
        }
        return scope.toString().trim();
    }

}
