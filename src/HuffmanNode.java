public class HuffmanNode {

    public HuffmanNode() {

    }

    public HuffmanNode(Integer integer,boolean isCombined) {
        frequence = integer;
        this.isCombined = isCombined;
    }

    public HuffmanNode(byte key, Integer integer, boolean isCombined) {
        symbole = key;
        frequence = integer;
        this.isCombined = isCombined;
    }

    boolean isCombined;
    byte symbole;
    int frequence;

    HuffmanNode left;
    HuffmanNode right;
}
