import com.sun.source.tree.Tree;

import java.util.*;
public class SegmentTree {
    public class TreeNode{
        int value;
        int start;
        int end;
        TreeNode left;
        TreeNode right;
        public TreeNode(int val, int start, int end) {
            this.value = val;
            this.start = start;
            this.end = end;
        }
    }
    private TreeNode root;
    public void populate(int[] nums){
        root = insert(root, nums, 0, nums.length-1);
    }
    private TreeNode insert(TreeNode node, int[] nums, int start, int end){
        if(start>end){
            return null;
        }
        if(start == end){
            return new TreeNode(nums[start], start, start);
        }

        // This logic is not correct, no need to calculate sum for all intervals
//        int sum = 0;
//        for(int i=start; i<=end; i++){
//            sum = sum+nums[i];
//        }

        node = new TreeNode(0,start,end);
        node.left = insert(node.left, nums, start, (end+start)/2);
        node.right = insert(node.right, nums, (end+start)/2+1, end);
        node.value = node.left.value + node.right.value;
        return node;
    }

    public void display(){
        display(root, "Root Node: ");
    }
    private void display(TreeNode node, String details){
        System.out.print(details);
        System.out.println(node.value);
        if(node.left !=null)
            display(node.left, "Left Node of "+node.value+" is: ");
        if(node.right !=null)
            display(node.right, "Right Node of "+node.value+" is: ");
    }

    public int query(int q_start, int q_end){
        return query(root, q_start, q_end);
    }
    private int query(TreeNode root, int s, int e) {
        if (root == null) return 0;

        // No overlap
        if (root.end < s || root.start > e) return 0;

        // Total overlap
        if (root.start >= s && root.end <= e) return root.value;

        // Partial overlap
        return query(root.left, s, e) + query(root.right, s, e);
    }

    public void update(int num, int index){
        this.root.value = update(root, num, index);
    }
    private int update(TreeNode node, int num, int index){
        if(index >= node.start && index <= node.end){
            if(index == node.start && index == node.end) {
                node.value = num;
                return num;
            }
            else{
                int leftSum = update(node.left, num, index);
                int rightSum = update(node.right, num, index);
                node.value = leftSum+rightSum;
                return leftSum+rightSum;
            }
        }
        return node.value;
    }

    public static void main(String[] args){
        int[] nums = new int[]{3,8,7,6,-2,-8,4,9};
        SegmentTree tree = new SegmentTree();
        tree.populate(nums);
        tree.display();
        System.out.println("Query Output for 0 to 7");
        System.out.println(tree.query(0,7));
        System.out.println("Query Output for 2 to 6");
        System.out.println(tree.query(2,6));
        tree.update(14,3);
        tree.display();
    }
}
