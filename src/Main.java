package log320_Lab1.src;

public class Main {

    public static void main(String[] args) {
        LZWCompressor compressor = new LZWCompressor("Hello World");

        compressor.test();

        /*if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");*/
    }
}
