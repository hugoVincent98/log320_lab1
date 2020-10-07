

import javax.swing.plaf.synth.SynthTabbedPaneUI;
import java.util.*;
import java.io.*;


public class Huffman {

    String fileInput;
    String fileOutput;
    String parameters;
    List<Integer> compressedFile;
    BitInputStream bitInputStream;
    BitOutputStream bitOutputStream;
    HashMap<Character,Integer> frequency = new HashMap<Character, Integer>();
    Stack stack;

    public Huffman(){};

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


    public HashMap<Character,Integer> countFrequencyCharacter(char[] letter){
        for (char charac : letter){
            if (!frequency.containsKey(charac)){
                frequency.put(charac,1);
            }else {
                frequency.put(charac,frequency.get(charac)+1);
            }
        }
        return frequency;
    }


    public Stack addRecursive(Stack current,int value){
        if (current == null) {
            return new Stack(value);
        }

        if (value < current.aPos) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.aPos) {
            current.right = addRecursive(current.right, value);
        } else {
            //value already exists
            return current;
        }

        return current;
    }

    public void add(int value) {
        stack = addRecursive(stack, value);
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
