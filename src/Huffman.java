
import javax.swing.plaf.synth.SynthTabbedPaneUI;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.security.KeyStore.Entry;

public class Huffman {

    String fileInput;
    String fileOutput;
    String parameters;
    List<Integer> compressedFile;
    BitInputStream bitInputStream;
    BitOutputStream bitOutputStream;
    HashMap<Byte, Integer> frequency = new HashMap<Byte, Integer>();
    Stack stack;

    public Huffman() {

    };

    public Huffman(String fileInput, String fileOutput) {
        this.fileInput = fileInput;
        this.fileOutput = fileOutput;
        this.bitInputStream = new BitInputStream(fileInput);
        compressedFile = new ArrayList<>();
    }

    public Huffman(String fileInput, String fileOutput, String parameters) {
        this.fileInput = fileInput;
        this.fileOutput = fileOutput;
        this.parameters = parameters;
        this.bitInputStream = new BitInputStream(fileInput);
        compressedFile = new ArrayList<>();
    }

    public void compress() throws IOException {

        try (FileInputStream fileinputStream = new FileInputStream(fileInput)) {
            byte currentByte;
            int b = fileinputStream.read();
            while (b > 0) {
                currentByte = (byte) b;
                addToFrequencyTable(currentByte);
            }

            // trie la liste par fréquences
            Map<Byte, Integer> hm1 = GFG.sortByValue(frequency); 

		// print the sorted hashmap 
		/*for (Map.Entry<String, Integer> en : hm1.entrySet()) { 
			System.out.println("Key = " + en.getKey() + 
						", Value = " + en.getValue()); 
        } */
        
        }

    }
    
    public void encode(Stack stack, String code, Map<Character, String> huffman) {
        if (stack == null)
            return;
        if (stack.left == null && stack.right == null) {
            huffman.put(stack.aChar, code);
        }
        encode(stack.left, code + '0', huffman);
        encode(stack.right, code + '1', huffman);
    }

    public int decode(Stack stack, int pos, StringBuilder stringBuilder) {
        if (stack == null)
            return pos;
        if (stack.left == null && stack.right == null) {
            return pos;
        }
        pos++;

        if (stringBuilder.charAt(pos) == '0')
            pos = decode(stack.left, pos, stringBuilder);
        else
            pos = decode(stack.right, pos, stringBuilder);
        return pos;
    }

    // calcul le nombre de fois le byte est repeté
    public void addToFrequencyTable(Byte b) {

        if (!frequency.containsKey(b)) {
            frequency.put(b, 1);
        } else {
            frequency.put(b, frequency.get(b) + 1);
        }
    }

    public Stack addRecursive(Stack current, int value) {
        if (current == null) {
            return new Stack(value);
        }

        if (value < current.aPos) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.aPos) {
            current.right = addRecursive(current.right, value);
        } else {
            // value already exists
            return current;
        }

        return current;
    }

    public void add(int value) {
        stack = addRecursive(stack, value);
    }
    
}
