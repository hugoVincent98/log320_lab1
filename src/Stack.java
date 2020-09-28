public class Stack {

    char aChar;
    int aPos;
    Stack left = null;
    Stack right = null;

    public Stack(char aChar, int aPos){
        this.aChar = aChar;
        this.aPos = aPos;
    }

    public Stack(char aChar, int aPos, Stack left, Stack right){
        this.aChar = aChar;
        this.aPos = aPos;
        this.left = left;
        this.right = right;
    }
}
