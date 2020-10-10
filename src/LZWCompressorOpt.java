
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.io.*;

public class LZWCompressorOpt {

    String fileInput;
    String fileOutput;
    List<Integer> compressedFile;
    byte[] bytes;

    public LZWCompressorOpt(String fileInput, String fileOutput) {
        this.fileInput = fileInput;
        this.fileOutput = fileOutput;
        compressedFile = new ArrayList<>();
        bytes = new byte[2];
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

                            compressedFile.add(dictionary.get(s)); // sortir le code de s
                            if (dictionary.size() < 4096)
                                dictionary.put(sc, dictionary.size());
                            s = "" + c;
                        }
                    }
                } catch (EOFException e) {
                    System.out.println("End of File");
                    if (!s.equals("")) {
                        compressedFile.add(dictionary.get(s));
                    }
                }
            }
        }
        // <--------pseudo code
    }

    /**
     * Permet de output le fichier compresser
     * 
     */
    public void save() throws IOException {

        BitOutputStream bos = new BitOutputStream(fileOutput);

        // change tous les chiffre en binaire et les met dans une liste de string
        ArrayList<String> strs = new ArrayList<>();
        for (Integer i : compressedFile) {
            if (i != null) {
                String result = Integer.toBinaryString(i);
                String resultWithPadding = String.format("%12s", result).replace(" ", "0"); // 12-bit Integer
                strs.add(resultWithPadding);
            }
        }

        // passe sur tous la liste de String et sur chaque charactere des strings
        for (int i = 0; i < strs.size(); i++) {
            for (int j = 0; j < strs.get(i).length(); j++) {

                // sort le premier charactere(premier bit) de la string en charactere
                char monChara = strs.get(i).charAt(j);

                // ecrit dans le fichier et change le charactere en int, car sinon il affiche le
                // charactere en code ascii
                bos.writeBit(Character.getNumericValue(monChara));
            }
        }
        bos.close();

    }
}
