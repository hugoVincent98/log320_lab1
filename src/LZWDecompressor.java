
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;

public class LZWDecompressor {

    String fileInput;
    String fileOutput;
    List<Character> deCompressedFile;
    BitInputStream bis;
    BitOutputStream bos;

    public LZWDecompressor(String fileInput, String fileOutput) {
        this.fileInput = fileInput;
        this.fileOutput = fileOutput;
        this.bis = new BitInputStream(this.fileInput);
        this.bos = new BitOutputStream(this.fileOutput);
        deCompressedFile = new ArrayList<>();
    }

    public void decompress() throws IOException {

        // initialise le dictionnaires
        HashMap<Integer, String> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put(i, "" + (char) i);
        }

        // sort les caractere de compress (met en bit)
        String bitString = "";
        int b = 0;

        while (b != -1) {
            b = bis.readBit();
            if (b >= 0)
                bitString = bitString + b;
        }
        bis.close();

        // met les bit en caractere
        ArrayList<Integer> fileCompressed = new ArrayList<Integer>();

        for (int i = 0; i < bitString.length() / 12; i++) {

            int a = Integer.parseInt(bitString.substring(12 * i, (i + 1) * 12), 2);
            fileCompressed.add(a);
        }

        // debut pseudo-code
        String s = null;

        for (int i = 0; i < fileCompressed.size(); i++) {

            Integer k = fileCompressed.get(i);
            String seq = dictionary.get(k);

            if (seq == null) {
                seq = s + s.charAt(0);
            }
            deCompressedFile.add(seq.charAt(0));
            if (s != null) {
                if (dictionary.size() < 4096)
                    dictionary.put(i, s + seq.charAt(0));
            }
            s = seq;

        }

        System.out.println(deCompressedFile);
    }

    public void save() throws IOException {

        // change tous les chiffre en binaire et les met dans une liste de string
        ArrayList<String> strs = new ArrayList<String>();
        for (Character c : deCompressedFile) {

            String result = Integer.toBinaryString(c);
            String resultWithPadding = String.format("%8s", result).replace(" ", "0"); // 8-bit Integer
            strs.add(resultWithPadding);

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