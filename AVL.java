import java.util.*;
public class AVL {
    public class TreeNode{
        int val;
        int height;
        TreeNode left;
        TreeNode right;
        TreeNode(int val){
            this.val = val;
        }
    }
    private int getHeight(TreeNode node) {
        return node == null ? 0 : node.height;
    }
    private TreeNode root;
    public void populate(int[] nums){
        for(int i=0; i<nums.length; i++)
            this.insert(nums[i]);
    }
    public void insert(int value){
        root = this.insert(value, root);
    }

    private TreeNode insert(int value, TreeNode node){
        if(node == null){
            node = new TreeNode(value);
        }
        else if(node.val < value){
            node.right = insert(value, node.right);
        }
        else if(node.val > value){
            node.left = insert(value, node.left);
        }
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        return rotate(node);
    }

    private TreeNode rotate(TreeNode node){
        if(getHeight(node.left)-getHeight(node.right)>1){
            // Tree is Left Heavy for this node, this case will have two subcases
            if((getHeight(node.left.left)-getHeight(node.left.right))>0){
                // Left-Left Case Right rotate on node
                return rightRotate(node);
            }
            if(getHeight(node.left.left)-getHeight(node.left.right)<0) {
                // Left-Right case, first left rotate Child node (node.left) then right rotate node(P)
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        if(getHeight(node.left)-getHeight(node.right)<-1){
            // Tree is Right Heavy for this node
            if(getHeight(node.right.right)-getHeight(node.right.left)>0){
                return leftRotate(node);
            }
            if(getHeight(node.right.right)-getHeight(node.right.left)<0){
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        return node;
    }

    private TreeNode rightRotate(TreeNode node){
        TreeNode C = node.left;
        TreeNode t2 = C.right;
        C.right = node;
        node.left = t2;
        // Update the two heights that change for this rotation, note-: subtree heights will not change at all
        node.height = Math.max(getHeight(node.left),getHeight(node.right))+1;
        C.height = Math.max(getHeight(C.left),getHeight(C.right))+1;
        return C;
    }

    private TreeNode leftRotate(TreeNode c){
        System.out.println("Left Rotating over node "+c.val);
        TreeNode p = c.right;
        TreeNode t2 = p.left;
        p.left = c;
        c.right = t2;
        c.height = 1 + Math.max(getHeight(c.left), getHeight(c.right));
        p.height = 1 + Math.max(getHeight(p.left), getHeight(p.right));
        return p;
    }

    public void display(){
        display(root, "Root Node: ");
    }
    private void display(TreeNode node, String details){
        System.out.print(details);
        System.out.println(node.val);
        if(node.left !=null)
            display(node.left, "Left Node of "+node.val+" is: ");
        if(node.right !=null)
            display(node.right, "Right Node of "+node.val+" is: ");
    }

    public boolean balanced(){
        return balanced(root);
    }
    private boolean balanced(TreeNode node){
        if(node == null)
            return true;
        // Check if for this node the diff in height of left and right nodes are less than equal to 1
        // ALso that the left and right subtrees are balanced
        return Math.abs(getHeight(node.left)-getHeight(node.right))<=1 && balanced(node.left) && balanced(node.right);
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        AVL tree = new AVL();
        int[] nums = new int[]{5,2,7,1,4,6,9,8,3,10};
        int[] nums2 = new int[]{1,2,3,4,5,6,7,8,9,10};
        tree.populate(nums2);
        tree.display();
        System.out.println("height of the tree is");
        System.out.println(tree.root.height);
    }

}
