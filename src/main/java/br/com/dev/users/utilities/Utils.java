package br.com.dev.users.utilities;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.dev.users.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Utilitário para diversas classes
 * 
 * @author felipe
 *
 */
public class Utils {

    private static final String SECRET_KEY = "TeRcEsYM";

    public static String getJWTToken(final User user) {
	return Jwts.builder().setSubject(user.getId()).signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public static String getUUID() {
	return UUID.randomUUID().toString();
    }

    public static String encrypt(final String value) {
	return DigestUtils.md5Hex(value);
    }

}
