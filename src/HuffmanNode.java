public class HuffmanNode {

    public HuffmanNode(byte key, Integer integer) {
        symbole = key;
        frequence = integer;
    }

    // utilisé lorsque le node n'est pas un leaf
    public HuffmanNode(Integer integer) {
        frequence = integer;
    }

    // utilisé lorsque le node n'est pas un leaf
    public HuffmanNode(HuffmanNode left, HuffmanNode right) {
        this.left = left;
        this.right = right;
    }

    public HuffmanNode(byte key, HuffmanNode left, HuffmanNode right) {
        symbole = key;
        this.left = left;
        this.right = right;
    }

    byte symbole;
    int frequence;

    HuffmanNode left;
    HuffmanNode right;

    public boolean isLeaf() {
        return left == null && right == null;
    }
}
