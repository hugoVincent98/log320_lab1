package log320_Lab1.src;

import java.util.Map;

import javax.print.DocFlavor.CHAR_ARRAY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LZWCompressor {
    
    String text;


    
    public LZWCompressor(String text){
        this.text = text;
    }


    public List<Byte> compress(List<Byte> t) {

        
        /*List<char> listeChar = new ArrayList();
        for (Byte b : t) {
            
        }*/
        
        List<String> compressFile;

        int dictSize = 256;
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        for (int i = 0; i < 256; i++) {
            dictionary.put("" +(char) i, i);
        }


        return null;
    }












    public void test () {
        // Build the dictionary.
        // initialise le dictionnaire avec les valeurs de base(unique)
        int dictSize = 256;
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        for (int i = 0; i < 256; i++) {
            dictionary.put("" + (char)i, i);
        }

        // rajoute au dictionnaire les combinaisons
        

        String w = "";
        List<Integer> result = new ArrayList<Integer>();
        for (char c : text.toCharArray()) {
            String wc = w + c;
            if (dictionary.containsKey(wc))
                w = wc;
            else {
                result.add(dictionary.get(w));
                // Add wc to the dictionary.
                dictionary.put(wc, dictSize++);
                w = "" + c;
            }
        }
    }
}
