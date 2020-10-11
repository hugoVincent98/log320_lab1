

public class Stack {

    char aChar;
    int aPos;
    Stack left;
    Stack right;

    public Stack (int aPos){
        this.aPos = aPos;
        this.left = null;
        this.right = null;
    }
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

    
    public boolean isLeaf(){
        assert ((this.left == null) && (this.right == null)) || ((this.left != null) && (this.right != null));
        return (this.left == null) && (this.right == null);
    }
    

}
