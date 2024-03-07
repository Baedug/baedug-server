package yerong.baedug.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.management.openmbean.InvalidKeyException;
import java.io.*;
import java.net.URL;
import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

@RequiredArgsConstructor
@Service
@Slf4j
public class AppleService {

    @Value("${social-login.provider.apple.team-id}")
    private String APPLE_TEAM_ID;

    @Value("${social-login.provider.apple.key-id}")
    private String APPLE_LOGIN_KEY;

    @Value("${spring.security.oauth2.client.registration.apple.clientId}")
    private String APPLE_CLIENT_ID;

    @Value("${social-login.provider.apple.redirect-uri}")
    private String APPLE_REDIRECT_URL;

    @Value("${spring.security.oauth2.client.registration.apple.clientSecret}")
    private String APPLE_KEY_PATH;

    private final static String APPLE_AUTH_URL = "https://appleid.apple.com";

    public String getAppleLogin() {
        return APPLE_AUTH_URL + "/auth/authorize"
                + "?client_id=" + APPLE_CLIENT_ID
                + "&redirect_uri=" + APPLE_REDIRECT_URL
                + "&response_type=code%20id_token&scope=name%20email&response_mode=form_post";
    }
    public AppleDto getAppleInfo(String code, String user) throws Exception {
        if (code == null) throw new Exception("Failed get authorization code");

        String clientSecret = createClientSecret();
        String userId = "";
        String email  = "";
        String accessToken = "";
        String username="";


        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type"   , "authorization_code");
            params.add("client_id"    , APPLE_CLIENT_ID);
            params.add("client_secret", clientSecret);
            params.add("code"         , code);
            params.add("redirect_uri" , APPLE_REDIRECT_URL);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    APPLE_AUTH_URL + "/auth/token",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

            accessToken = String.valueOf(jsonObj.get("access_token"));

            //ID TOKEN을 통해 회원 고유 식별자 받기
            SignedJWT signedJWT = SignedJWT.parse(String.valueOf(jsonObj.get("id_token")));
            ReadOnlyJWTClaimsSet getPayload = signedJWT.getJWTClaimsSet();

            ObjectMapper objectMapper = new ObjectMapper();
            JSONObject payload = objectMapper.readValue(getPayload.toJSONObject().toJSONString(), JSONObject.class);

            log.info("===" + payload.toString());
            userId = String.valueOf(payload.get("sub"));
            email  = String.valueOf(payload.get("email"));

            if (!StringUtils.isEmpty(user)) {
                username = extractUsername(user);
            }

        } catch (Exception e) {
            throw new Exception("API call failed");
        }

        return AppleDto.builder()
                .id(userId)
                .token(accessToken)
                .email(email)
                .username(username)
                .build();
    }

    private String createClientSecret() throws Exception {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(APPLE_LOGIN_KEY).build();
        JWTClaimsSet claimsSet = new JWTClaimsSet();

        Date now = new Date();
        claimsSet.setIssuer(APPLE_TEAM_ID);
        claimsSet.setIssueTime(now);
        claimsSet.setExpirationTime(new Date(now.getTime() + 3600000));
        claimsSet.setAudience(APPLE_AUTH_URL);
        claimsSet.setSubject(APPLE_CLIENT_ID);

        SignedJWT jwt = new SignedJWT(header, claimsSet);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(getPrivateKey());
        KeyFactory kf = KeyFactory.getInstance("EC");
        try {
            ECPrivateKey ecPrivateKey = (ECPrivateKey) kf.generatePrivate(spec);

            JWSSigner jwsSigner = new ECDSASigner(ecPrivateKey.getS());
            jwt.sign(jwsSigner);

        } catch (InvalidKeyException | JOSEException e) {
            throw new Exception("Failed create client secret");
        }

        return jwt.serialize();
    }

    private byte[] getPrivateKey() throws Exception {
        byte[] content = null;
        File file = new File(APPLE_KEY_PATH);
        URL res = getClass().getResource(APPLE_KEY_PATH);

        if (res == null) {
            // 파일 시스템에서 파일을 로드할 때
            file = new File(APPLE_KEY_PATH);
        } else if ("jar".equals(res.getProtocol())) {
            // JAR 파일 내부의 리소스를 읽을 때
            try {
                InputStream input = getClass().getResourceAsStream(APPLE_KEY_PATH);
                file = File.createTempFile("tempfile", ".tmp");
                OutputStream out = new FileOutputStream(file);

                int read;
                byte[] bytes = new byte[1024];

                while ((read = input.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                out.close();
                file.deleteOnExit();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (file.exists()) {
            try (FileReader keyReader = new FileReader(file);
                 PemReader pemReader = new PemReader(keyReader))
            {
                PemObject pemObject = pemReader.readPemObject();
                content = pemObject.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new Exception("File " + file + " not found");
        }

        return content;
    }
    private String extractUsername(String userJsonString) throws Exception {
        JSONObject userJson = (JSONObject) new JSONParser().parse(userJsonString);
        JSONObject nameObject = (JSONObject) userJson.get("name");

        String firstName = (String) nameObject.get("firstName");
        String lastName = (String) nameObject.get("lastName");

        return firstName + lastName;
    }
}