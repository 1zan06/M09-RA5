package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {
    private static final char[] alfabet = "AÀÁBCÇDEÈÉFGHIÏJKLMNÑOÓPQRSTUÜVWXYZ".toCharArray();

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            Long.parseLong(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }
        
        String resultat = xifraPoliAlfa(msg, clau);
        return new TextXifrat(resultat.getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            Long.parseLong(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau de Polialfabètic ha de ser un String convertible a long");
        }
        
        return desxifraPoliAlfa(new String(xifrat.getBytes()), clau);
    }

    private static int calcularSeed(String claveSecret) {
        int seed = 0;
        for (char c : claveSecret.toCharArray()) {
            seed += c;
        }
        return seed;
    }

    public static char[] permutaAlfabet(int contrasena) {
        List<Character> lista = new ArrayList<>();
        for (char c : alfabet) {
            lista.add(c);
        }

        Random random = new Random(contrasena);
        Collections.shuffle(lista, random);

        char[] perm = new char[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            perm[i] = lista.get(i);
        }

        return perm;
    }

    public static String xifraPoliAlfa(String text, String claveSecret) {
        int seed = calcularSeed(claveSecret);
        String resultat = "";
        int numLletres = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            boolean trobat = false;
            int index = -1;

            for (int j = 0; j < alfabet.length; j++) {
                if (c == alfabet[j] || Character.toUpperCase(c) == alfabet[j]) {
                    index = j;
                    trobat = true;
                    break;
                }
            }

            if (trobat) {
                int semillaActual = seed + numLletres;
                char[] permutat = permutaAlfabet(semillaActual);
                char letraCifrada = permutat[index];

                if (Character.isLowerCase(c)) {
                    resultat = resultat + Character.toLowerCase(letraCifrada);
                } else {
                    resultat = resultat + letraCifrada;
                }
                numLletres++;
            } else {
                resultat = resultat + c;
            }
        }    
        return resultat;
    }  

    public static String desxifraPoliAlfa(String text, String claveSecret) {
        int seed = calcularSeed(claveSecret);
        String resultat = "";
        int numLletres = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            boolean trobat = false;
            int index = -1;

            int semillaActual = seed + numLletres;
            char[] permutat = permutaAlfabet(semillaActual);

            for (int j = 0; j < permutat.length; j++) {
                if (c == permutat[j] || Character.toUpperCase(c) == permutat[j]) {
                    index = j;
                    trobat = true;
                    break;
                }
            }

            if (trobat) {
                char letraDescifrada = alfabet[index];

                if (Character.isLowerCase(c)) {
                    resultat = resultat + Character.toLowerCase(letraDescifrada);
                } else {
                    resultat = resultat + letraDescifrada;
                }
                numLletres++;
            } else {
                resultat = resultat + c;
            }
        }
        return resultat;
    }
}
