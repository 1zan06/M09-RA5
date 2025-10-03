
import java.util.Collections;
import java.util.List;

public class Monoalfabetic {
    static final char[] alfabet = "AÀÁBCÇDEÈÉFGHIÏJKLMNÑOÓPQRSTUÜVWXYZ".toCharArray();
    
    static char[] permutat;


    public Monoalfabetic() {
      this.permutat = permutaAlfabet();
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
      }

        for (int i = 0; i < alfabet; i+) {
          if (c == alfabet[i]) {
            resultat = resultat + permutat[i];
            trobat = true;
            break;
          }
        }

        if (!trobat) {
          char upper = Character.toUpperCase(c)
            for (i = 0; i < )
        }

    } 

    public String desxifraMonoAlfa(String cadena) {
      return null;
    }
    
    
    public static void main(String[] args) {
      permutat = permutaAlfabet();
      System.out.println("Alfabet original: " + Arrays.toString(alfabet));
      System.out.println("Alfabet permutat: " + Arrays.toString(permutat));

      String text = "Hola Món";
      String xifrat = xifraMonoAlfa(text);
      String desxifrat = desxifraMonoAlfa(xifrat);

      System.out.println("Text original = " + text);
      System.out.println("Text xifrat = " + xifrat);
      System.out.println("Text desxifrat = " + desxifrat);
        
    }
}