import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class Main {

    public static String [] test (boolean v) {
        if (v) {
            return new String[] { "-opt", "-c",
                    "C:\\Users\\Rajani\\Documents\\GitHub\\log320_Lab1\\media_files\\exemple.txt", "test12opt.lzw" };
        } else {
            return new String[] { "-opt", "-d",
                    "C:\\Users\\Rajani\\Documents\\GitHub\\log320_Lab1\\test12opt.lzw", "testcompiled.txt" };
        }
    }
    public static void main(String[] args) {

        args = test(true);
        /*
         * –[huff|lzw|opt] –[d|c] <fichier d’entrée> <fichier de sortie> la topologie
         * que nous devons utilise pour acceder aux algo avec le terminal
         */


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

        }  else if (args[0].equals("-opt") && args[1].equals("-c")) {
           
           
            try {
                LZWCompressorOpt myCompressor = new LZWCompressorOpt(args[2], args[3]);
                Instant start = Instant.now();
                myCompressor.compress();
                myCompressor.save();
                Instant end = Instant.now();
                Duration diff = Duration.between(start, end);
                System.out.println("milli: " + diff.toMillis());
                System.out.println(String.format("%d:%02d:%02d", 
                     diff.toMinutesPart(), 
                     diff.toSecondsPart(),
                     diff.toMillisPart()));

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
        } 

        /*
         * else if (args[0].equals("huff") && args[1].equals("c")){ HUFFCompressor
         * myCompressor = new HUFFCompressor(args[2], args[3]); } else if
         * (args[0].equals("huff") && args[1].equals("d")){ HUFFDecompressor
         * myDeCompressor = new HUFFDecompressor(args[2], args[3]); }
         * 
         * 
         * 
         * else if (args[0].equals("opt") && args[1].equals("c")) { OPTCompressor
         * myCompressor = new OPTCompressor(args[2], args[3]); } else if
         * (args[0].equals("opt") && args[1].equals("d")) { OPTDecompressor
         * myDeCompressor = new OPTDecompressor(args[2], args[3]); }
         */
        else
            throw new IllegalArgumentException("Illegal command line argument");
    }
}
