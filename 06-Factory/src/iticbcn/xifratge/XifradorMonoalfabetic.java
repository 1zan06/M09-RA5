package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XifradorMonoalfabetic implements Xifrador {
    private final char[] alfabet = "AÀÁBCÇDEÈÉFGHIÏJKLMNÑOÓPQRSTUÜVWXYZ".toCharArray();
    private char[] permutat;

    public XifradorMonoalfabetic() {
        this.permutat = permutaAlfabet();
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
        String resultat = xifraMonoAlfa(msg);
        return new TextXifrat(resultat.getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
        return desxifraMonoAlfa(new String(xifrat.getBytes()));
    }

    public char[] permutaAlfabet() {
        List<Character> lista = new ArrayList<>();
        
        for (char c : alfabet) {
            lista.add(c);
        }

        Collections.shuffle(lista);

        char[] perm = new char[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            perm[i] = lista.get(i);
        }

        return perm;
    }

    public String xifraMonoAlfa(String cadena) {
        String resultat = "";

        for (char c : cadena.toCharArray()) {
            boolean trobat = false;

            for (int i = 0; i < alfabet.length; i++) {
                if (c == alfabet[i]) {
                    resultat = resultat + permutat[i];
                    trobat = true;
                    break;
                }
            }

            if (!trobat) {
                char upper = Character.toUpperCase(c);
                for (int i = 0; i < alfabet.length; i++) {
                    if (upper == alfabet[i]) {
                        resultat = resultat + Character.toLowerCase(permutat[i]);
                        trobat = true;
                        break;
                    }
                }
            }

            if (!trobat) {
                resultat = resultat + c;
            }
        }
        return resultat;
    }

    public String desxifraMonoAlfa(String cadena) {
        String resultat = "";

        for (char c : cadena.toCharArray()) {
            boolean trobat = false;

            for (int i = 0; i < permutat.length; i++) {
                if (c == permutat[i]) {
                    resultat = resultat + alfabet[i];
                    trobat = true;
                    break;
                }
            }

            if (!trobat) {
                char upper = Character.toUpperCase(c);
                for (int i = 0; i < permutat.length; i++) {
                    if (upper == permutat[i]) {
                        resultat = resultat + Character.toLowerCase(alfabet[i]);
                        trobat = true;
                        break;
                    }
                }
            }

            if (!trobat) {
                resultat = resultat + c;
            }
        }
        return resultat;
    }
}