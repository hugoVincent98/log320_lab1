
import java.io.IOException;


public class Main {

    public static void main(String[] args) {

        /*–[huff|lzw|opt] –[d|c] <fichier d’entrée> <fichier de sortie>
        la topologie que nous devons utilise pour acceder aux algo avec le terminal
        */

        args = new String[]{"lzw","c","C:\\Users\\Rajani\\Documents\\GitHub\\log320_Lab1\\media_files\\text.txt", "test2.txt"};

        if (args[0].equals("lzw") && args[1].equals("c")){
            LZWCompressor myCompressor = new LZWCompressor(args[2], args[3]);
            try {
                myCompressor.compress();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
                myCompressor.save();
            } catch (IOException e ) {
                //ok
            }
        
        }
        else if (args[0].equals("lzw") && args[1].equals("d")) {
           // LZWDecompressor myDeCompressor = new LZWDecompressor(args[2], args[3]);
        }


        /*
        else if (args[0].equals("huff") && args[1].equals("c")){
            HUFFCompressor myCompressor = new HUFFCompressor(args[2], args[3]);
        }
        else if (args[0].equals("huff") && args[1].equals("d")){
            HUFFDecompressor myDeCompressor = new HUFFDecompressor(args[2], args[3]);
        }
        


        else if (args[0].equals("opt") && args[1].equals("c")) {
            OPTCompressor myCompressor = new OPTCompressor(args[2], args[3]);
        }
        else if (args[0].equals("opt") && args[1].equals("d")) {
            OPTDecompressor myDeCompressor = new OPTDecompressor(args[2], args[3]);
        }
        */
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
