
import java.io.IOException;
import java.util.Arrays;


public class Main {

    public static void main(String[] args) throws IOException {

        final String algotype = args[0];
        final String parameters = args[1];
        final String inputFile = args[2];
        final String outputFile = args[3];
        final int maxArgs = 4;


        /*–[huff|lzw|opt] –[d|c] <fichier d’entrée> <fichier de sortie>
        la topologie que nous devons utilise pour acceder aux algo avec le terminal
        */

        if (args.length != maxArgs) {
            System.out.println("Missing arguments");
            System.out.println("–[huff|lzw|opt] –[d|c] <inputFile> <outputFile>");
        } else {

            if (parameters.equals("-c") || parameters.equals("-d")) {
                switch (algotype){
                    case "-lzw" :   // LZW lzw = new LZW(inputFile,outputFile);
                                    // LZWCompressor lzwc = new LZWCompressor(inputFile,outputFile,parameters);
                        break;
                    case "-huff" :  // Huffman huffman = new Huffman(inputFile,outputFile);
                                    // Huffman huffman = new Huffman(inputFile,outputFile,parameters);
                        break;
                    case "-opt" :   // OPT opt = new OPT(inputFile,outputFile);
                                    // OPT opt = new OPT(inputFile,outputFile,parameters);
                        break;
                    default:        System.out.println("–[huff|lzw|opt]");
                }
            } else {
                System.out.println("–[d|c]");
            }
        }
        //else throw new IllegalArgumentException("Illegal command line argument");

    }
}
