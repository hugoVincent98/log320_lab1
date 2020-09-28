
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sun.jvm.hotspot.utilities.Bits;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LZWCompressor {
    
    String fileInput;
    String fileOutput;
    List<Integer> compressedFile;


    
    public LZWCompressor(String fileInput, String fileOutput){
        this.fileInput = fileInput;
        this.fileOutput = fileOutput;
        compressedFile = new ArrayList<>();
    }


    /**
     * Permet de compresser le fichier demander selon LWZ
     * 
     * @throws IOException
     * 
     */
    public void compress() throws IOException {

        ArrayList<String> listeChar = new ArrayList<>();

       // BitInputStream b = new BitInputStream(fileInput);
       byte[] array = Files.readAllBytes(Paths.get(fileInput));

        for (byte b : array)
         listeChar.add(""+ (char) b);
        
        
        //initialise le dictionnaire
        
        HashMap<String, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < 255; i++) {
            dictionary.put("" + (char) i, i);
        }
        


        //pseudo code -------->
        String c = "";
        String p = listeChar.get(0);
        int code = 256;
        for(int i = 0 ; i < listeChar.size(); i++){
            
            if (i != listeChar.size() -1)
                c += listeChar.get(i);
                
           
            if (dictionary.keySet().contains(p+c)) {
                p = p + c;
                
            } else {                
                compressedFile.add(dictionary.get(p));
                dictionary.put(p+c,code );
                code++;
                p = c;

            }
            c = "";
        }
        compressedFile.add(dictionary.get(p));
        //<--------pseudo code
        
    }

    /**
     * Permet de output le fichier compresser
     * 
     */
    public void save() throws IOException {

        

        BitOutputStream bos = new BitOutputStream(fileOutput);

        //change tous les chiffre en binaire et les met dans une liste de string
        ArrayList<String> strs = new ArrayList<String>();
        for (Integer i : compressedFile ) {
            String result = Integer.toBinaryString(i);
            String resultWithPadding = String.format("%8s", result).replace(" ", "0");  // 8-bit Integer
            strs.add(resultWithPadding);
        }
        System.out.println(strs);
          
        //passe sur tous la liste de String et sur chaque charactere des strings
        for(int i=0; i<strs.size(); i++) {
            for(int j=0; j<strs.get(i).length(); j++){

                //sort le premier charactere(premier bit) de la string en charactere 
                char monChara = strs.get(i).charAt(strs.get(i).length()-1-j);

                //ecrit dans le fichier et change le charactere en int, car sinon il affiche le charactere en code
                bos.writeBit(Character.getNumericValue(monChara));
            }
        }   
                
    }
}
