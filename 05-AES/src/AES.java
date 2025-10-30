import java.security.MessageDigest;
import java.security.SecureRandom;
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

        return iv_msgXifrat;
    }

    public static String desxifraAES(byte[] bIVMsgXifrat, String clau) throws Exception {
        //extraer IV, els primers 16 bytes
        byte[] ivExtret = new byte[MIDA_IV];
        System.arraycopy(bIVMsgXifrat, 0, ivExtret, 0, MIDA_IV);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivExtret);

        
        int encryptedSize = bIVMsgXifrat.length - MIDA_IV;
        byte[] bMsgXifrat = new byte[encryptedSize];
        System.arraycopy(bIVMsgXifrat, MIDA_IV, bMsgXifrat, 0, encryptedSize);


        //generar hash de la clau
        MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
        md.update(clau.getBytes("UTF-8"));
        byte[] keyBytes = md.digest();

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORISME_XIFRAT);


        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] original = cipher.doFinal(bMsgXifrat);

        return new String(original, "UTF-8");
    }

    public static void main(String[] args) {
        String[] msgs = {
            "Lorem ipsum dicet", 
            "Hola Andrés cómo está tu cuñado",
            "Agora Illa Otto"
        };

        String CLAU_PARA_MAIN = AES.CLAU;

        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];
            byte[] bXifrats = null;
            String desxifrat = "";

            try {
                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desxifraAES(bXifrats, CLAU);
            } catch (Exception e) {
                System.err.println("Error de Xifrat: " + e.getLocalizedMessage());
                continue; 
            }

            System.out.println("----------------------------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats)); 
            System.out.println("DEC: " + desxifrat);
        }
        System.out.println("----------------------------------------");
    }
}
    