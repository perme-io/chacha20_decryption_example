import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;


public class ChaCha20 {
    public static void main(String[] args) throws Exception {
        // 암호화된 데이터를 복호화 하기 위한 키를 만들 수 있는 KeyBase 값
        String keyBase = "ee01b7a1b636dd542311ff423fe3fb8f3ddf8c068044fba70abc757667b4cff3";

        // 암호화된 데이터, 예제에서는 "TEST_DATA_ALICE" 를 암호화 한 값을 임의로 입력하여 사용하겠습니다. 실제로는 받은 데이터를 사용하면 됩니다.
        byte[] encryptedData = null;
        // 테스트를 위해 미리 암호화된 데이터를 사용하였습니다.
        encryptedData = hexStringToByteArray("a867f0b296c200e100374ffb6543d8b7cf27ea5b79f3d621fc0ed3508f0ed6bd414c332b6de85d0889d7cb");

        // Split the nonce and ciphertext
        byte[] nonce = Arrays.copyOfRange(encryptedData, 0, 12);
        byte[] ciphertext = Arrays.copyOfRange(encryptedData, 12, encryptedData.length);

        // Generate the key
        byte[] keyBytes = hexStringToByteArray(keyBase);
        Key key = new SecretKeySpec(keyBytes, "ChaCha20");

        // Initialize the cipher
        Cipher cipher = Cipher.getInstance("ChaCha20-Poly1305");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(nonce));

        // Decrypt the ciphertext
        byte[] plaintext = cipher.doFinal(ciphertext);

        // Print the plaintext as a string, 예제의 데이터는 문자열이므로 단순 출력하여 확인하였습니다.
        System.out.println(new String(plaintext, StandardCharsets.UTF_8));
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
