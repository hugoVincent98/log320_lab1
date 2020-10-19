
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

public class LZWCompressorOpt {

    String fileInput;
    String fileOutput;
    List<Integer> compressedFile;
    // https://codereview.stackexchange.com/questions/122080/simplifying-lzw-compression-decompression

    public LZWCompressorOpt(String fileInput, String fileOutput) {
        this.fileInput = fileInput;
        this.fileOutput = fileOutput;
        compressedFile = new ArrayList<>();
    }

    /**
     * Permet de compresser le fichier demander selon LWZ Suit une logique inspiré
     * de :
     * https://github.com/ayonious/File-Compression/blob/master/src/main/java/prog/Lzipping.java
     * 
     * @throws IOException
     * 
     */
    public void compress() throws IOException {

        // initialise le dictionnaire
        HashMap<String, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put("" + (char) i, i);
        }

        // pseudo code -------->
        BitOutputStream bos = new BitOutputStream(fileOutput);
        try (FileInputStream fileinputStream = new FileInputStream(fileInput)) {
            try (DataInputStream datainStream = new DataInputStream(fileinputStream)) {
                String s = "";
                Byte b;

                try {

                    b = datainStream.readByte(); // récupère le premier Byte du fichier
                    int i = b.intValue();
                    if (i < 0)
                        i += 256;
                    char c = (char) i;// transforme le Byte en Char
                    s = "" + c;
                    while (true) {

                        b = datainStream.readByte(); // récupère le Byte du fichier
                        i = b.intValue();
                        if (i < 0)
                            i += 256;

                        c = (char) i;// transforme le Byte en Char
                        String sc = s + c;
                        if (dictionary.containsKey(sc))
                            s = s + c;
                        else {

                            String resultWithPadding = String.format("%12s", Integer.toBinaryString(dictionary.get(s))).replace(" ", "0");
                            for (int j=0;j<resultWithPadding.length();j++)
                                bos.writeBit(Integer.parseInt(resultWithPadding.substring(j, j+1)));

                            if (dictionary.size() < 4096)
                                dictionary.put(sc, dictionary.size());
                            s = "" + c;

                            if (dictionary.size() == 4096)
                                resetDictionnary(dictionary);
                        }
                    }
                } catch (EOFException e) {
                    System.out.println("End of File");
                    if (!s.equals("")) {
                        String resultWithPadding = String.format("%12s", Integer.toBinaryString(dictionary.get(s))).replace(" ", "0");
                        for (int j=0;j<resultWithPadding.length();j++)
                            bos.writeBit(Integer.parseInt(resultWithPadding.substring(j, j+1)));
                    }
                }
                
            }
        bos.close();
        }
        // <--------pseudo code
    }

    public void resetDictionnary(Map<String, Integer> dictionary) {
        dictionary.clear();
        for (int i = 0; i < 256; i++) {
            dictionary.put("" + (char) i, i);
        }
    }


}
