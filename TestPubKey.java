import java.nio.file.*;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;
import java.util.List;

public class TestPubKey {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("public.key"));
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("-----") || line.isEmpty()) continue;
            sb.append(line);
        }
        byte[] decodedKey = Base64.getDecoder().decode(sb.toString());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        System.out.println("公钥解析成功！");
    }
} 