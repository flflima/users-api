package br.com.dev.users.utilities;

import java.util.UUID;

import br.com.dev.users.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Utils {

    private static final String SECRET = "TeRcEsYM";

    public static String getJWTToken(final User user) {
	return Jwts.builder().setSubject(user.getId()).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public static String getUUID() {
	return UUID.randomUUID().toString();
    }

}
