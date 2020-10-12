import java.io.IOException;

public class Main {

    public static String[] test(boolean v) {
        if (v) {
           return new String[] { "-huff", "-c",
                    "C:\\Users\\Rajani\\Documents\\GitHub\\log320_Lab1\\media_files\\text.txt", "test12.huff" };
        } else {
            return  new String[] { "-huff", "-d", "C:\\Users\\Rajani\\Documents\\GitHub\\log320_Lab1\\test12.huff",
                    "testrecompiled.txt" };
        }
    }
    public static void main(String[] args) {

        /*
         * –[huff|lzw|opt] –[d|c] <fichier d’entrée> <fichier de sortie> la topologie
         * que nous devons utilise pour acceder aux algo avec le terminal
         */

        // test pour compress
        args = test(false);
       

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

                //test a retirer après
                BitInputStream boi = new BitInputStream(args[3]);
                int b = boi.readBit();
                while (b > -1) {
        
                    System.out.print(b);
                    b = boi.readBit();
                }
                boi.close();

            } catch (IOException e) {

                e.printStackTrace();
            }
        } else if (args[0].equals("-huff") && args[1].equals("-d")) {
            Huffman decompressor = new Huffman(args[2], args[3]);
            try {
                decompressor.decompress();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        else
            throw new IllegalArgumentException("Illegal command line argument");
    }
}