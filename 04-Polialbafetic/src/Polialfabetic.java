import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Polialfabetic {
    private static final char[] alfabet = "AÀÁBCÇDEÈÉFGHIÏJKLMNÑOÓPQRSTUÜVWXYZ".toCharArray();

    private static int calcularSeed(String claveSecret) {
        int seed = 0;
        // recorremos la clave y sumamos el valor de cada carácter.
        for (char c : claveSecret.toCharArray()) {
            seed += c;
        }
        return seed;
    }

    public static char[] permutaAlfabet(int contrasena) {
        // convertimos el array alfabet a una List para poder barajarlo
        List<Character> lista = new ArrayList<>();
        for (char c : alfabet) {
            lista.add(c);
        }

        // inicializamos el generador Random con la constraseña, esto garantiza que si la contrasseña es la misma, la secuencia de números aleatorios sea la misma.
        Random random = new Random(contrasena);

        // barajamos la lista. Collections.shuffle usa el objeto Random que le pasamos.
        Collections.shuffle(lista, random);

        // convertimos la lista barajada de nuevo a un array de chars para devolverlo
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
                if (c == alfabet[j] || Character.toUpperCase(c) == alfabet[i]) {
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
            }

            else {
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
            }

            else {
    
                resultat = resultat + c;
            }
        } 
        return resultat;
    }
        

    public static void main(String[] args) {
        String clauSecreta = "CLAVE"; 
        String msgs[]={"Test 01 àrbitre, coixí, Perímetre",
                "Test 02 Taüll, DÍA, año",
                "Test 03 Peça, Òrrius, Bòvila"};
        String msgsXifrats[]= new String[msgs.length];

        System.out.println("Xifratge:\n--------");
        for (int i = 0; i < msgs.length;i++) {

            msgsXifrats[i] = xifraPoliAlfa(msgs[i], clauSecreta);
            
            // Se corrige el formato de impresión: %n, -> %n
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }
        
        System.out.println("\nDesxifratge:\n--------");
        for (int i = 0; i < msgs.length;i++) {
            String msg = desxifraPoliAlfa(msgsXifrats[i], clauSecreta);
            
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }    
    }   
}



