package iticbcn.xifratge;

import java.security.KeyPair;
import java.util.Base64;

public class Main {
    public static void main(String args[]) throws Exception {

        Panorama cp = new Panorama();

        String msg = "Missatge de prova per xifrar aefotu aefotu aefotu";

        KeyPair parellClaus = cp.generaParellClausRSA();

        byte[] msgXifrat = cp.xifraRSA(msg, parellClaus.getPublic());

        System.out.println("===============================");
        System.out.print("Text xifrat: ");
        System.out.println(bytesToHex(msgXifrat));

        String msgDesxifrat = cp.desxifraRSA(msgXifrat, parellClaus.getPrivate());
        System.out.println("===============================");
        System.out.println("Text desxifrat: " + msgDesxifrat);

        String strClauPub = Base64.getEncoder().encodeToString(parellClaus.getPublic().getEncoded());
        String strClauPriv = Base64.getEncoder().encodeToString(parellClaus.getPrivate().getEncoded());

        System.out.println("===============================");
        System.out.println("Clau publica: " + strClauPub);
        System.out.println("===============================");
        System.out.println("Clau privada: " + strClauPriv);
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}