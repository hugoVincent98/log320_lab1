import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        /*
         * –[huff|lzw|opt] –[d|c] <fichier d’entrée> <fichier de sortie> la topologie
         * que nous devons utilise pour acceder aux algo avec le terminal
         */

        // test pour compress

        if (true) {
            args = new String[] { "-huff", "-c",
                    "C:\\Users\\Rajani\\Documents\\GitHub\\log320_Lab1\\media_files\\test.txt", "test12.huff" };
        } else {
            args = new String[] { "-lzw", "-d", "C:\\Users\\Rajani\\Documents\\GitHub\\log320_Lab1\\test12.lzw",
                    "testrecompiled.txt" };
        }

        // test pour decompress

        if (args[0].equals("-lzw") && args[1].equals("-c")) {
            LZWCompressor myCompressor = new LZWCompressor(args[2], args[3]);
            try {
                myCompressor.compress();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                myCompressor.save();
            } catch (IOException e) {
                // ok
            }

        } else if (args[0].equals("-lzw") && args[1].equals("-d")) {

            LZWDecompressor myDecompressor = new LZWDecompressor(args[2], args[3]);
            myDecompressor.decompress();

            try {
                myDecompressor.save();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        else if (args[0].equals("-huff") && args[1].equals("-c")) {
            try {
                System.out.println("test");
                Huffman myCompressor = new Huffman(args[2], args[3]);
                myCompressor.compress();
            } catch (IOException e) {

                e.printStackTrace();
            }
        } else if (args[0].equals("-huff") && args[1].equals("-d")) {

        }

        else
            throw new IllegalArgumentException("Illegal command line argument");
    }
}