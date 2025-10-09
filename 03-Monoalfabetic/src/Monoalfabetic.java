
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Monoalfabetic {
    private final char[] alfabet = "AÀÁBCÇDEÈÉFGHIÏJKLMNÑOÓPQRSTUÜVWXYZ".toCharArray();
    
    private char[] permutat;

    public Monoalfabetic() {
      this.permutat = permutaAlfabet();
    }


    public char[] permutaAlfabet() {
      List<Character> lista = new ArrayList<>();
        
        // pasamos todos los caracteres del array alfabet a la lista
        for (char c : alfabet) {
            lista.add(c);
        }

        // mezclamos la lista con shuffle, que lo que hace es barajar los elementos
        Collections.shuffle(lista);

        // creamos un nuevo array del mismo tamaño que la lista
        char[] perm = new char[lista.size()];

        // pasamos los elementos mezclados de la lista al array este
        for (int i = 0; i < lista.size(); i++) {
            perm[i] = lista.get(i);
        }

        return perm; 
    }

    public String xifraMonoAlfa(String cadena) {
      String resultat = ""; 

        // recorremos cada caracter de la cadena original
        for (char c : cadena.toCharArray()) {
            boolean trobat = false; // para saber si hemos encontrado la letra en el alfabeto

            // comprobamos si el caracter es mayúscula
            for (int i = 0; i < alfabet.length; i++) {
                if (c == alfabet[i]) { 
                    resultat = resultat + permutat[i]; 
                    trobat = true;
                    break; 
                }
            }

            // si no era mayúscula, comprobamos si es minúscula
            if (!trobat) {
                char upper = Character.toUpperCase(c); 
                for (int i = 0; i < alfabet.length; i++) {
                    if (upper == alfabet[i]) {
                        // si coincide, añadimos la versión minúscula de la letra cifrada
                        resultat = resultat + Character.toLowerCase(permutat[i]);
                        trobat = true;
                        break;
                    }
                }
            }

            // si no se ha encontrado espacio, número, símbolo o lo que sea, se deja igual
            if (!trobat) {
                resultat = resultat + c;
            }
        }
        return resultat;
    } 

    public String desxifraMonoAlfa(String cadena) {
      String resultat = ""; // texto descifrado

        for (char c : cadena.toCharArray()) {
            boolean trobat = false;

            // compruebo si el carácter es mayuscula cifrada
            for (int i = 0; i < permutat.length; i++) {
                if (c == permutat[i]) {
                    resultat = resultat + alfabet[i]; 
                    trobat = true;
                    break;
                }
            }

            // si no era mayuscula, comprobamos si es minúscula
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

            // si no está en el alfabeto, se deja igual
            if (!trobat) {
                resultat = resultat + c;
            }
        }

        return resultat;
    }
    
    
    public static void main(String[] args) {
      Monoalfabetic mono = new Monoalfabetic();

      System.out.println("Alfabet original: " + Arrays.toString(mono.alfabet));
      System.out.println("Alfabet permutat: " + Arrays.toString(mono.permutat));

      String text = "Hola Món!";
      String xifrat = mono.xifraMonoAlfa(text);
      String desxifrat = mono.desxifraMonoAlfa(xifrat);

      System.out.println("Text original  = " + text);
      System.out.println("Text xifrat    = " + xifrat);
      System.out.println("Text desxifrat = " + desxifrat);
    }
}