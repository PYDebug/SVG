package edu.tongji.webgis.security;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.tongji.webgis.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

@Component
public class TokenHandler {
	private static final String HMAC_ALGO = "HmacSHA256";
	private static final String SEPARATOR = ".";
	private static final String SEPARATOR_SPLITTER = "\\.";
	private final Mac hmac;
	
	@Autowired
	public TokenHandler(@Value("${token.secret}") String secret) {
		byte[] secretKey = DatatypeConverter.parseBase64Binary(secret);
		try {
			hmac = Mac.getInstance(HMAC_ALGO);
			hmac.init(new SecretKeySpec(secretKey, HMAC_ALGO));
		} catch (Exception e) {
			throw new IllegalStateException(
				"failed to initialize HMAC: " + e.getMessage(), e);
		}
	}
	
	public String createTokenForAccount(AccountUserDetails account) {
		byte[] userBytes;
		try {
			userBytes = JsonUtils.serialize(account).getBytes("utf-8");
			byte[] hash = createHmac(userBytes);
			final StringBuilder sb = new StringBuilder(170);
			Encoder encoder = Base64.getEncoder();
			sb.append(encoder.encodeToString(userBytes));
			sb.append(SEPARATOR);
			sb.append(encoder.encodeToString(hash));
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public AccountUserDetails parseAccountFromToken(String token) {
		if (token == null)
			return null;
		final String[] parts = token.split(SEPARATOR_SPLITTER);
		if (parts.length == 2 && parts[0].length() > 0 && parts[1].length() > 0) {
			Decoder decoder = Base64.getDecoder();
			final byte[] userBytes = decoder.decode(parts[0]);
			final byte[] hash = decoder.decode(parts[1]);

			boolean validHash = Arrays.equals(createHmac(userBytes), hash);
			if (validHash) {
				final AccountUserDetails details = (AccountUserDetails) JsonUtils.deserialize(new String(userBytes), new TypeReference<AccountUserDetails>() {
				});
				return details;
			}
		}
		return null;
	}
	
	// synchronized to guard internal hmac object
	private synchronized byte[] createHmac(byte[] content) {
		return hmac.doFinal(content);
	}
}
