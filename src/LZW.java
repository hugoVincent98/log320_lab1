
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LZW {
    
    String fileInput;
    String fileOutput;
    String parameters;
    List<Integer> compressedFile;


    
    public LZW(String fileInput, String fileOutput){
        this.fileInput = fileInput;
        this.fileOutput = fileOutput;
        compressedFile = new ArrayList<>();
    }

    public LZW(String fileInput, String fileOutput, String parameters){
        this.fileInput = fileInput;
        this.fileOutput = fileOutput;
        this.parameters = parameters;
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

        

        String text = "";
        for (Integer i : compressedFile ) {
            text += i + "," ;
            System.out.println(text+"");
        }


       // text = text.substring(0, text.length() - 1);


       /* BitOutputStream bos = new BitOutputStream(fileOutput);
        
        for (int i = 0; i< compressedFile.size() ; i++ ) {
            bos.writeBit(compressedFile.get(i));
        }*/
        
        
        try (PrintStream out = new PrintStream(new FileOutputStream(fileOutput))) {
            out.print(text);
        }

        
        
                
    }
}
