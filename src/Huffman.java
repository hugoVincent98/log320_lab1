

import java.util.*;
import java.io.*;


public class Huffman {

    String fileInput;
    String fileOutput;
    String parameters;
    List<Integer> compressedFile;
    BitInputStream bitInputStream;



    public Huffman(String fileInput, String fileOutput){
        this.fileInput = fileInput;
        this.fileOutput = fileOutput;
        this.bitInputStream = new BitInputStream(fileInput);
        compressedFile = new ArrayList<>();
    }

    public Huffman(String fileInput, String fileOutput, String parameters){
        this.fileInput = fileInput;
        this.fileOutput = fileOutput;
        this.parameters = parameters;
        this.bitInputStream = new BitInputStream(fileInput);
        compressedFile = new ArrayList<>();
    }


    public void encode(Stack stack, String code, Map<Character,String> huffman){
        if (stack == null)
            return;
        if (stack.left == null && stack.right == null){
            huffman.put(stack.aChar,code);
        }
        encode(stack.left,code+'0',huffman);
        encode(stack.right,code+'1',huffman);
    }

    public int decode(Stack stack, int pos, StringBuilder stringBuilder){
        if (stack == null)
            return pos;
        if (stack.left == null && stack.right == null){
            return pos;
        }
        pos++;

        if (stringBuilder.charAt(pos) == '0')
            pos = decode(stack.left,pos,stringBuilder);
        else
            pos = decode(stack.right,pos,stringBuilder);
        return pos;
    }


    /**
     * Permet de compresser le fichier demander selon LWZ
     *
     */
    public void compress() throws IOException {

    }


    /**
     * Permet de compresser le fichier demander selon LWZ
     *
     */
    public void decompress() throws IOException {

    }

    /**
     * Permet de output le fichier compresser
     *
     */
    public void save() throws IOException {


    }
}
