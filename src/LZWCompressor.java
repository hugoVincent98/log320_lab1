package log320_Lab1.src;

import java.util.Map;

import javax.print.DocFlavor.CHAR_ARRAY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import FileUtils.*;
import java.io.*;

public class LZWCompressor {
    
    String fileInput;
    String fileOutput;
    List<Integer> compressedFile;


    
    public LZWCompressor(String fileInput, String fileOutput){
        this.fileInput = fileInput;
        this.fileOutput = fileOutput;
        compressedFile = new ArrayList<Integer>();
    }


    /**
     * Permet de compresser le fichier demander selon LWZ
     * 
     */
    public void compress() {

        ArrayList<String> listeChar = new ArrayList<>();

        BitInputStream b = new BitInputStream(fileInput);

        int bit = 0;
        while (bit != -1) {
            //change byte --> char
            bit = b.readByte();
            listeChar.add(""+ (char) bit);

        }
        
        
        //initialise le dictionnaire
        int dictSize = 256;
        int lastKey  = 255;

        HashMap<String, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < dictSize; i++) {
            dictionary.put("" +(char) i, i);
        }



        //pseudo code -------->
        String s = listeChar.get(0);
        
        for(int i = 1 ; i < listeChar.size(); i++){
            
            String c = listeChar.get(i);
           
            if(dictionary.keySet().contains(s+c)){
                s = s+c;
                
            }else{                
                compressedFile.add(dictionary.get(s));
                dictionary.put(s+c, lastKey);
                lastKey++;
                s = c;

            }
        }
        compressedFile.add(dictionary.get(s));
        //<--------pseudo code
        
    }


    /**
     * Permet de output le fichier compresser
     * 
     */
    public void save() throws IOException {

        

        String text = "";
        for (Integer i : compressedFile ) {
            text += i + "," ;
        }
        text = text.substring(0, text.length() - 1);


        BitOutputStream bos = new BitOutputStream(fileOutput);
        
        for (int i = 0; i< compressedFile.size() ; i++ ) {
            bos.writeBit(compressedFile.get(i));
        }
        
        
        /*try (PrintStream out = new PrintStream(new FileOutputStream(fileOutput))) {
            out.print(text);
        }*/

        
        
                
    }
}
