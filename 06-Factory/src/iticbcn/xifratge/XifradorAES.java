package iticbcn.xifratge;

import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class XifradorAES implements Xifrador {
    public final String ALGORISME_XIFRAT = "AES";
    public final String ALGORISME_HASH = "SHA-256";
    public final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private final int MIDA_IV = 16;
    private final String CLAU = "LaClauSecreta123";

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            byte[] bXifrats = xifraAES(msg, clau);
            return new TextXifrat(bXifrats);
        } catch (Exception e) {
            System.err.println("Error de Xifrat: " + e.getLocalizedMessage());
            System.exit(1);
            return null;
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            return desxifraAES(xifrat.getBytes(), clau);
        } catch (Exception e) {
            System.err.println("Error de Desxifrat: " + e.getLocalizedMessage());
            System.exit(1);
            return null;
        }
    }

    public byte[] xifraAES(String msg, String clau) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        digest.update(clau.getBytes("UTF-8"));
        byte[] keyBytes = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORISME_XIFRAT);

        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[MIDA_IV];
        random.nextBytes(iv);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] msgBytes = msg.getBytes("UTF-8");
        byte[] msgXifrat = cipher.doFinal(msgBytes);

        byte[] iv_msgXifrat = new byte[MIDA_IV + msgXifrat.length];
        System.arraycopy(iv, 0, iv_msgXifrat, 0, MIDA_IV);
        System.arraycopy(msgXifrat, 0, iv_msgXifrat, MIDA_IV, msgXifrat.length);

        return iv_msgXifrat;
    }

    public String desxifraAES(byte[] bIVMsgXifrat, String clau) throws Exception {
        byte[] ivExtret = new byte[MIDA_IV];
        System.arraycopy(bIVMsgXifrat, 0, ivExtret, 0, MIDA_IV);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivExtret);

        int encryptedSize = bIVMsgXifrat.length - MIDA_IV;
        byte[] bMsgXifrat = new byte[encryptedSize];
        System.arraycopy(bIVMsgXifrat, MIDA_IV, bMsgXifrat, 0, encryptedSize);

        MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
        md.update(clau.getBytes("UTF-8"));
        byte[] keyBytes = md.digest();

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORISME_XIFRAT);

        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] original = cipher.doFinal(bMsgXifrat);
        return new String(original, "UTF-8");
    }
}