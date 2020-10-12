
import java.util.*;

import java.io.*;

public class Huffman {

    String fileInput;
    String fileOutput;
    HashMap<Byte, Integer> tableFrequences;
    HashMap<Byte, String> tableCodes;
    Queue<Byte> file;

    public Huffman(String fileInput, String fileOutput) {
        this.fileInput = fileInput;
        this.fileOutput = fileOutput;
        file = new LinkedList<>();
        tableCodes = new HashMap<>();
        tableFrequences = new HashMap<>();
    }

    /**
     * Permet de compresser le fichier demander selon l'algorithme huffman
     */
    public void compress() throws IOException {

        try (FileInputStream fileinputStream = new FileInputStream(fileInput)) {
            byte currentByte;
            int b = fileinputStream.read();
            while (b > -1) {
                currentByte = (byte) b;
                addToFrequencyTable(currentByte);
                file.add(currentByte);

                b = fileinputStream.read();
            }
        }
        // trie la liste par fréquences on utilise lambda pour comparer les fréquences
        PriorityQueue<HuffmanNode> huffmanQueue = new PriorityQueue<>(tableFrequences.size(),
                (HuffmanNode o1, HuffmanNode o2) -> o1.frequence - o2.frequence);

        // ajouter les données du hashMap dans la queue
        for (Map.Entry<Byte, Integer> entry : tableFrequences.entrySet())
            huffmanQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));

        HuffmanNode racine = null;

        while (huffmanQueue.size() > 1) {

            // on retrieve les 2 min de la queue
            HuffmanNode h1 = huffmanQueue.poll();
            HuffmanNode h2 = huffmanQueue.poll();

            // crée un nouveau qui a la somme de frequence
            HuffmanNode hsum = new HuffmanNode(h1.frequence + h2.frequence);

            hsum.left = h1;
            hsum.right = h2;
            racine = hsum;

            // on rajoute le noeud combiné dans la queue
            huffmanQueue.add(hsum);
        }

        // prend le dernier élément (racine) de la Queue
        racine = huffmanQueue.poll();

        // écrire le code compressé dans le file
        StringBuilder fileCompressed = new StringBuilder();
        int nbSymbole = tableFrequences.size();

        // premier byte dédié à au nombre de char utilisé dans le treeMap
        fileCompressed.append(String.format("%8s", Integer.toBinaryString(nbSymbole)).replace(" ", "0"));

        // On dédie ensuite un total de n bits pour le treeMap Huffman
        // n bits est égal à la formule : 10 * NUMBER_OF_CHARACTERS - 1
        // NUMBER_OF_CHARACTERS étant le nombre de symboles utilisés dans le treeMap
        // C'est pour ça qu'on dédie le premier byte au nombre de char pour savoir quand
        // arrêter
        encodeHuffmanTreemap(racine, fileCompressed);

        // on crée un HashMap clé valeur de la table de codes pour l'encodage
        createCode(racine, "");

        // on encode ensuite le file en utilisant la table de codes
        Byte b = file.poll();
        while (b != null) {
            String code = tableCodes.get(b);
            // on écrit le byte bit par bit dans le Stream
            fileCompressed.append(code);
            b = file.poll();
        }

        // on ajoute au debut du fichier un autre byte qui sera dédié à dire combien de
        // 0
        // il y a à la fin du file. C'est parce que si le file ne finit pas avec un 8
        // bits, BitOutputStream va rajouter des 0 à la fin pour compléter le dernier
        // byte.
        int eof = 8 - (fileCompressed.length() % 8);
        fileCompressed.insert(0, String.format("%8s", Integer.toBinaryString(eof)).replace(" ", "0"));

        // compresser les bits dans le fichier

        BitOutputStream bos = new BitOutputStream(fileOutput);

        for (int n = 0; n < fileCompressed.length(); n++)
            bos.writeBit(Character.getNumericValue(fileCompressed.charAt(n)));

        bos.close();
    }

    /**
     * méthode qui sert à encoder le Huffman treeMap au début du fichier.
     * https://stackoverflow.com/questions/759707/efficient-way-of-storing-huffman-tree
     * 
     * https://mkyong.com/java/java-how-to-convert-a-byte-to-a-binary-string/
     * 
     * @param bos
     * @param racine
     */
    public void encodeHuffmanTreemap(HuffmanNode racine, StringBuilder fileCompressed) {

        if (racine.isLeaf()) {
            fileCompressed.append("1");
            int result = racine.symbole & 0xff;
            String resultWithPadZero = String.format("%8s", Integer.toBinaryString(result)).replace(" ", "0");
            fileCompressed.append(resultWithPadZero);

        } else {
            fileCompressed.append("0");

            encodeHuffmanTreemap(racine.left, fileCompressed);
            encodeHuffmanTreemap(racine.right, fileCompressed);
        }
    }

    /**
     * La méthode createCode va permettre de créer la table de codes par symboles
     * pour la compression
     * 
     * @param noeud
     * @param s
     */
    public void createCode(HuffmanNode noeud, String s) {

        // base case; if the left and right are null
        // then its a leaf node and we print
        // the code s generated by traversing the tree.
        if (noeud.isLeaf()) {
            // on l'ajoute à la table de codes
            tableCodes.put(noeud.symbole, s);
            return;
        }

        // if we go to left then add "0" to the code.
        // if we go to the right add"1" to the code.

        // recursive calls for left and
        // right sub-tree of the generated tree.
        createCode(noeud.left, s + "0");
        createCode(noeud.right, s + "1");
    }

    // calcul le nombre de fois le byte est repeté
    public void addToFrequencyTable(Byte b) {

        if (!tableFrequences.containsKey(b)) {
            tableFrequences.put(b, 1);
        } else {
            tableFrequences.put(b, tableFrequences.get(b) + 1);
        }
    }

    /**
     * Méthode qui sert à décompresser un fichier Huffman
     */
    public void decompress() throws IOException {

        // sort les caractere de compress (met en bit)
        StringBuilder bitString = new StringBuilder();
        int b = 0;

        // lecture du fichier
        BitInputStream bis = new BitInputStream(this.fileInput);

        while (b != -1) {
            b = bis.readBit();
            if (b >= 0)
                bitString.append(b);
        }
        bis.close();

        // on va chercher le premier byte dédié à dire quand arrêter
        // de lire le fichier
        int eof = Integer.parseInt(bitString.substring(0, 8), 2);

        // on va lire le deuxième byte dédié au nb de char utilisé dans le treemap
        int nbchar = Integer.parseInt(bitString.substring(8, 16), 2);

        int nbBitTreeMap = (10 * nbchar) - 1;

        StringBuilder treemapCompressed = new StringBuilder(bitString.substring(16, 16 + nbBitTreeMap));
        HuffmanNode racine = readNode(treemapCompressed);

        // on prend le fichier compressé sans les derniers 0 en trop
        StringBuilder fileToDecompress = new StringBuilder(
                bitString.substring(16 + nbBitTreeMap, bitString.length() - eof));

        // on save le message décodé dans le out
        // le fichier compress passe à travers la racine jusqu'à trouver
        // son char. Lorqu'il le trouve, il revient à la racine
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileOutput)))) {

            int endoffile = 0;
            HuffmanNode currentNode = racine;
            while (endoffile != -1) {

                int value = Integer.parseInt(fileToDecompress.substring(0, 1), 2);

                fileToDecompress.deleteCharAt(0);

                if (value == 0) {
                    currentNode = currentNode.left;
                } else if (value == 1) {
                    currentNode = currentNode.right;
                }

                if (currentNode.isLeaf()) {
                    out.writeByte(currentNode.symbole);
                    currentNode = racine;
                }
                if (fileToDecompress.length() == 0)
                    endoffile = -1;
            }
        }
    }

    HuffmanNode readNode(StringBuilder reader) {
        char b = reader.charAt(0);
        reader.deleteCharAt(0);
        if (b == '1') {
            int value = Integer.parseInt(reader.substring(0, 8), 2);
            reader.delete(0, 8);
            return new HuffmanNode((byte) value, null, null);
        } else {
            HuffmanNode leftChild = readNode(reader);
            HuffmanNode rightChild = readNode(reader);
            return new HuffmanNode(leftChild, rightChild);
        }
    }

}
