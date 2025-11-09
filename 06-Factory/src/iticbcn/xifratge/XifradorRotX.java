package iticbcn.xifratge;

public class XifradorRotX implements Xifrador {
    private char[] minuscules = "aàábcçdeèéfghiïjklmnñoópqrstuüvwxyz".toCharArray();
    private char[] majuscules = "AÀÁBCÇDEÈÉFGHIÏJKLMNÑOÓPQRSTUÜVWXYZ".toCharArray();

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        int desplazamiento;
        try {
            desplazamiento = Integer.parseInt(clau);
            if (desplazamiento < 0 || desplazamiento > 40) {
                throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
            }
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
        
        String resultat = xifraRotX(msg, desplazamiento);
        return new TextXifrat(resultat.getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        int desplazamiento;
        try {
            desplazamiento = Integer.parseInt(clau);
            if (desplazamiento < 0 || desplazamiento > 40) {
                throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
            }
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
        
        return desxifraRotX(new String(xifrat.getBytes()), desplazamiento);
    }

    private char desplaça(char c, int d, char[] alfabet) {
        for (int i = 0; i < alfabet.length; i++) {
            if (alfabet[i] == c) {
                int novaPos = (i + d + alfabet.length) % alfabet.length;
                return alfabet[novaPos];
            }
        }
        return c;
    }

    public String xifraRotX(String cadena, int desplaçament) {
        String resultat = "";
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (Character.isLowerCase(c)) {
                resultat += desplaça(c, desplaçament, minuscules);
            } else if (Character.isUpperCase(c)) {
                resultat += desplaça(c, desplaçament, majuscules);
            } else {
                resultat += c;
            }
        }
        return resultat;
    }

    public String desxifraRotX(String cadena, int desplaçament) {
        return xifraRotX(cadena, -desplaçament);
    }
}