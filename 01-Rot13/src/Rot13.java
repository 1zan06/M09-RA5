public class Rot13{
    
    public static char[] minuscules = "abcdefghijklmnopqrstuvwxyzàáèéíïòóúüñç".toCharArray();
    public static char[] majuscules = "ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÈÉÍÏÒÓÚÜÑÇ".toCharArray();

    public static String xifraRot13(String cadena) {
        String resultat = "";

        for (int i = 0; i < cadena.length();i++) {
            char c = cadena.charAt(i);
            for (int j = 0; j<minuscules.length; j++) {
                if (c == minuscules[j]) {
                    int novaPos = (j + 13) % minuscules.length;
                    resultat = resultat + minuscules[novaPos];
                    break;
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
