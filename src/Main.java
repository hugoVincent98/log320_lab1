import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        final String algotype = args[0];
        final String parameters = args[1];
        final String inputFile = args[2];
        final String outputFile = args[3];
        final int maxArgs = 4;
        
        /*
         * –[huff|lzw|opt] –[d|c] <fichier d’entrée> <fichier de sortie> la topologie
         * que nous devons utilise pour acceder aux algo avec le terminal
         */

        if (args.length != maxArgs) {
            System.out.println("Missing arguments");
            System.out.println("–[huff|lzw|opt] –[d|c] <inputFile> <outputFile>");
        } else {

            if (parameters.equals("-c")) {
                switch (algotype) {
                    case "-lzw":
                        LZWCompressor lzwc = new LZWCompressor(inputFile, outputFile);
                        lzwc.compress();
                        lzwc.save();
                        break;
                    case "-huff":
                        Huffman huffman = new Huffman(inputFile, outputFile);
                        huffman.compress();
                        break;
                    case "-opt":
                        LZWCompressorOpt lz = new LZWCompressorOpt(inputFile, outputFile);
                        lz.compress();
                        break;
                    default:
                        System.out.println("–[huff|lzw|opt]");
                }
            } else if (parameters.equals("-d")) {
                switch (algotype) {
                    case "-lzw":
                        LZWDecompressor lzwd = new LZWDecompressor(inputFile, outputFile);
                        lzwd.decompress();
                        lzwd.save();
                        break;
                    case "-huff":
                        Huffman huffman = new Huffman(inputFile, outputFile);
                        huffman.decompress();
                        break;
                    case "-opt":
                        LZWDecompressorOpt lzd = new LZWDecompressorOpt(inputFile, outputFile);
                        lzd.decompress();
                        lzd.save();
                        break;
                    default:
                        System.out.println("–[huff|lzw|opt]");
                }
            } else {
                System.out.println("–[d|c] only");
            }
        }
    }

}