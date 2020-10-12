
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;

public class LZWDecompressorOpt {

    String fileInput;
    String fileOutput;
    List<String> deCompressedFile;

    public LZWDecompressorOpt(String fileInput, String fileOutput) {
        this.fileInput = fileInput;
        this.fileOutput = fileOutput;
        deCompressedFile = new ArrayList<>();
    }

    public void decompress() {

        // initialise le dictionnaire avec les char
        HashMap<Integer, String> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put(i, "" + (char) i);
        }

        // sort les caractere de compress (met en bit)
        StringBuilder bitString = new StringBuilder();
        int b = 0;

        // lecture du fichier
        BitInputStream bis = new BitInputStream(this.fileInput);

        while (b != -1) {
            b = bis.readBit();
            if (b >= 0)
                bitString.append(b);
        }
        bis.close();

        // met les bit en caractere
        ArrayList<Integer> fileCompressed = new ArrayList<>();

        for (int i = 0; i < bitString.length() / 12; i++) {

            int a = Integer.parseInt(bitString.substring(12 * i, (i + 1) * 12), 2);
            fileCompressed.add(a);
        }

        int previousCode = fileCompressed.get(0);
        deCompressedFile.add(dictionary.get(previousCode));

        for (int i = 1; i < fileCompressed.size(); i++) {
            
            

            Integer currentCode = fileCompressed.get(i);

            if (currentCode >= dictionary.size()) {
                if (dictionary.size() < 4096)
                    dictionary.put(dictionary.size(),
                            dictionary.get(previousCode) + dictionary.get(previousCode).charAt(0));
                deCompressedFile.add(dictionary.get(previousCode) + dictionary.get(previousCode).charAt(0));
            } else {
                if (dictionary.size() < 4096)
                    dictionary.put(dictionary.size(),
                            dictionary.get(previousCode) + dictionary.get(currentCode).charAt(0));
                deCompressedFile.add(dictionary.get(currentCode));
            }
            previousCode = currentCode;
            if (dictionary.size()==4096)
                dictionary.clear();
        }

    }

    public void save() throws IOException {
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileOutput)))) {
            for (String c : deCompressedFile)
                out.writeBytes(c);
        }
    }

}