
public class RotX {

    static char[] minuscules = "aàábcçdeèéfghiïjklmnñoópqrstuüvwxyz".toCharArray();

    static char[] majuscules = "AÀÁBCÇDEÈÉFGHIÏJKLMNÑOÓPQRSTUÜVWXYZ".toCharArray();

    // aqui lo que hacemos es un método que desplaza una letra dentro de un alfabeto
    private static char desplaça(char c, int d, char[] alfabet) {
        for (int i = 0; i < alfabet.length; i++) {
            if (alfabet[i] == c) {
                int novaPos = (i + d + alfabet.length) % alfabet.length; // amb això calculo la nova posició
                return alfabet[novaPos];
            }
        }
        return c; // si no está en el alfabeto, devolver igual
    }

    public static String xifraRotX(String cadena, int desplaçament) {
        String resultat = "";
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (Character.isLowerCase(c)) {
                resultat += desplaça(c, desplaçament, minuscules);
            } else if (Character.isUpperCase(c)) {
                resultat += desplaça(c, desplaçament, majuscules);
            } else {
                resultat += c; // deja signos y espacios igual
            }
        }
        return resultat;
    }
        // metode per desxifrar, es posa en negatiu per que torni cap enrere amb el metode de xifrar i aixi ens el dexifra
    public static String desxifraRotX(String cadena, int desplaçament) {
        return xifraRotX(cadena, -desplaçament);
    }

    public static void forcaBrutaRotX(String cadenaXifrada) {
        System.out.println("Força Bruta:");
        for (int d = 1; d <= minuscules.length; d++) {
            String possible = desxifraRotX(cadenaXifrada, d);
            System.out.println("(" + d + ") -> " + possible);
        }
    }
    public static void main(String[] args) {
        String mensaje = "Hola, Mr. calçot";
        int desplaçament = 4;

        String xifrat = xifraRotX(mensaje, desplaçament);
        System.out.println("Xifrat (" + desplaçament + "): " + xifrat);

        String desxifrat = desxifraRotX(xifrat, desplaçament);
        System.out.println("Desxifrat: " + desxifrat);

        forcaBrutaRotX(xifrat);
    
    }
}