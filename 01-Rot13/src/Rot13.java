public class Rot13{
    
    public static char[] minuscules = "abcdefghijklmnopqrstuvwxyzàáèéíïòóúüñç".toCharArray();
    public static char[] majuscules = "ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÈÉÍÏÒÓÚÜÑÇ".toCharArray();

    public static void main(String[] args) {
        String cadenaXifrada = "Hola, què tal?";
        String cadenaDesxifrada = "Óvug, abm ägu?";

        String resultatCadenaXifrada = xifraRot13(cadenaXifrada);
        String resultatCadenaDesxifrada = desxifraRot13(cadenaDesxifrada);

        System.out.println("xifrat: " + cadenaXifrada + " => " + resultatCadenaXifrada);
        System.out.println("desxifrat: " + cadenaDesxifrada + " => " + resultatCadenaDesxifrada);
    }

    public static String xifraRot13(String cadena) {
        String xifraRot13 = "";

        for (int i = 0; i < cadena.length();i++) {
            char lletra = cadena.charAt(i);
            if (Character.isLowerCase(lletra) {
                for (int index = 0; index < cadena.length(); index++) {
                    
                }
            }
            boolean trobat = false;

            for (int j = 0; j<majuscules.length; j++) {
                if (c == majuscules[j]) {
                    int novaPos = (j + 13) % majuscules.length;
                    resultat = resultat + majuscules[novaPos];
                    trobat = true;
                    break;
                }
            }
        }
        
    }
    public static String desxifraRot13(String cadena) {return null}

    public static void main(String[] args) {
        
    }
}
