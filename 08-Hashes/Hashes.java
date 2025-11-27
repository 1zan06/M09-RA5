import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    public int npass = 0;
    
    public Hashes() {
        this.npass = 0;
    }
    
    public String getSHA512AmbSalt(String pw, String salt) {
        try {
            String combined = pw + salt;
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = md.digest(combined.getBytes());
            
            HexFormat hex = HexFormat.of();
            return hex.formatHex(hashBytes);
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: Algoritmo SHA-512 no disponible", e);
        }
    }
    
    public String getPBKDF2AmbSalt(String pw, String salt) {
        try {
            KeySpec spec = new PBEKeySpec(pw.toCharArray(), salt.getBytes(), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashBytes = factory.generateSecret(spec).getEncoded();
            
            HexFormat hex = HexFormat.of();
            return hex.formatHex(hashBytes);
            
        } catch (Exception e) {
            throw new RuntimeException("Error en PBKDF2", e);
        }
    }
    
    public String forcaBruta(String alg, String hash, String salt) {
        npass = 0;
        String charset = "abcdefABCDEF1234567890";
        
        for (int length = 1; length <= 6; length++) {
            String result = generateCombinations(alg, hash, salt, charset, length);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
    
    private String generateCombinations(String alg, String hash, String salt, 
                                      String charset, int length) {
        char[] current = new char[length];
        return generateRecursive(alg, hash, salt, charset, current, 0);
    }
    
    private String generateRecursive(String alg, String hash, String salt, 
                                   String charset, char[] current, int position) {
        if (position == current.length) {
            npass++;
            String testPassword = new String(current);
            
            String testHash;
            if (alg.equals("SHA-512")) {
                testHash = getSHA512AmbSalt(testPassword, salt);
            } else {
                testHash = getPBKDF2AmbSalt(testPassword, salt);
            }
            
            if (testHash.equals(hash)) {
                return testPassword;
            }
            return null;
        }
        
        for (int i = 0; i < charset.length(); i++) {
            current[position] = charset.charAt(i);
            String result = generateRecursive(alg, hash, salt, charset, current, position + 1);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
    
    public String getInterval(long t1, long t2) {
        long millis = t2 - t1;
        
        long segundos = millis / 1000;
        long minutos = segundos / 60;
        long horas = minutos / 60;
        long dias = horas / 24;
        
        return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis",
                            dias, horas % 24, minutos % 60, segundos % 60, millis % 1000);
    }
    
    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruahs1kdfjz";
        String pw = "aaabF1";
        Hashes h = new Hashes();
        String[] aliashes = { h.getSHA512AmbSalt(pw, salt), h.getPBKDF2AmbSalt(pw, salt) };
        String pwTrobat = null;
        String[] algorismes = {"SHA-512", "PBKDF2"};
        
        for(int i = 0; i < aliashes.length; i++) {
            System.out.printf("========================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n", aliashes[i]);
            System.out.printf("---\n");
            System.out.printf("-- Inici de força bruta ---\n");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aliashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("---\n\n");
        }
    }
}