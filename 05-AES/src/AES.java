import java.crypto.*;
import java.security.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv =new byte[MIDA_IV];
    private static final String CLAU = "LaClauSecreta123"; //contra a hashear

    public static byte[] xifraAES(String msg, String clau) throws Exception {
        //1- obtenir els bytes del String
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        digest.update(clau.getBytes("UTF-8"));
        byte[] keyBytes = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORISME_XIFRAT);

        //2- generar IvParameterSpec
        SecureRandom random = new SecureRandom();
        iv = new byte[MIDA_IV];
        random.nextBytes(iv);

        //3-generar IvParameterSpec
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        
        //4-inicializar i cifrar
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] msgBytes = msg.getBytes("UTF-8");
        byte[] msgXifrat = cipher.doFinal(msgBytes);

        //5- combinar iv i part cifrada
        byte[] iv_msgXifrat = new byte[MIDA_IV + msgXifrat.length];
        
        System.arraycopy(iv, 0, iv_msgXifrat, 0, MIDA_IV);
        System.arraycopy(msgXifrat, 0, iv_msgXifrat, MIDA_IV, msgXifrat.length);

        //6- retornar
        return iv_msgXifrat;
    }
}
    