package avl;

import java.io.*;
import java.util.*;


class Node {
    public String data;
    public int height = -1;
    public Node left;
    public Node right;
 
    public Node(String data) {
        this.data = data;
        height = 0;
        left = null;
        right = null;
    }
 
    //@Override
    //public String toString() {
        //return "Node [data=" + data + ", height=" + height + ", left=" + left
                //+ ", right=" + right + "]";
    //}
}
 
class AVLTree {
    public Node rootTree;
    
    public void insert(String data) {
        if (rootTree == null) {
            rootTree = new Node(data);
        } else {
            rootTree = insert(rootTree, data);
        }
    }
 
    public void remove(String data) {
        if (rootTree == null) {
            return;
        } else {
            rootTree = remove(rootTree, data);
        }
    }
    //printing AVL tree
    public void print(Node rootTree,int l){
    if (rootTree==null)
        return;
    print(rootTree.right,l+1);
    l++;
    if(l!=0){
     for(int i=0;i<l-1;i++)
            System.out.print("|\t");
            System.out.println("|---->"+rootTree.data); 
    }
    else
        System.out.println(rootTree.data);
    print(rootTree.left, l+1);
    
    }
    //searching AVL tree
    public int search(Node rootTree,String target ){
        if (rootTree==null)
        return 0;
        int count =0;
        Node cursor=rootTree;
        while(cursor!=null)
        {
        int compare=target.compareTo(cursor.data);
        
        if (compare==0){
        count++;
        cursor = cursor.right;
            }
            else if (compare<0){
               cursor  = cursor.left; 
            }
            else
               cursor = cursor.right;
        }
        System.out.println("Occurance count of searched element: "+count);
        return count;

    }
    // traversal of AVL Tree
    public String walk(PrintWriter write, Node rootTree){
        String ordered = new String();
    if (rootTree != null){
        walk(write, rootTree.left);
        //System.out.println(rootTree.data);
        write.println(rootTree.data);
        walk(write, rootTree.right);
    }
    return ordered;
    }
    
    //insertion for AVL tree
    private Node insert(Node subRoot, String data) {
        if (data.compareTo(subRoot.data)<0) {
            if (subRoot.left == null) {
                subRoot.left = new Node(data);
            } else {
                subRoot.left = insert(subRoot.left, data);
                if (Math.abs(
                        height(subRoot.left) - height(subRoot.right)) == 2) {
                    if (data.compareTo(subRoot.left.data) < 0 ) {
                        subRoot = llRotation(subRoot);
                    } else {
                        subRoot = lrRotation(subRoot);
                    }
                }
            }
        } else if (data.compareTo(subRoot.data)>=0) {
            if (subRoot.right == null) {
                subRoot.right = new Node(data);
            } else {
                subRoot.right = insert(subRoot.right, data);
                if (Math.abs(
                        height(subRoot.left) - height(subRoot.right)) == 2) {
                    if (data.compareTo(subRoot.right.data)>0) {
                        subRoot = rrRotation(subRoot);
                    } else {
                        subRoot = rlRotation(subRoot);
                    }
                }
            }
        } else {
        }
 
        subRoot.height = Math.max(height(subRoot.left), height(subRoot.right));
 
        return subRoot;
    }
    // Deletion for AVL Tree
    private Node remove(Node subRoot, String data) {
        if (data.compareTo(subRoot.data)<0) {
            if (subRoot.left == null) {
                return subRoot;
            } else {
                subRoot.left = remove(subRoot.left, data);
                if (subRoot.right != null && height(subRoot.right) - height(subRoot.left) == 2) {
                    Node rightNode = subRoot.right;
                    if (height(rightNode.left) <= height(rightNode.right)) {
                        subRoot = rrRotation(subRoot);
                    } else {
                        subRoot = rlRotation(subRoot);
                    }
                }
            }
        } else if (data.compareTo(subRoot.data)>0) {
            if (subRoot.right == null) {
                return subRoot;
            } else {
                subRoot.right = remove(subRoot.right, data);
                if (subRoot.left != null && height(subRoot.left) - height(subRoot.right) == 2) {
                    Node leftNode = subRoot.left;
                    if (height(leftNode.left) >= height(leftNode.right)) {
                        subRoot = llRotation(subRoot);
                    } else {
                        subRoot = lrRotation(subRoot);
                    }
                }
            }
        } else {
            if (subRoot.left == null || subRoot.right == null) {
                if (subRoot.left == null) {
                    subRoot = subRoot.right;
                } else if (subRoot.right == null) {
                    subRoot = subRoot.left;
                } else {
                    subRoot = null;
                }
            } else {
                Node inOrderSuccessor = minValueNode(subRoot.right);
                subRoot.data = inOrderSuccessor.data;
                subRoot.right = remove(subRoot.right, subRoot.data);
            }
        }
        if (subRoot != null) {
            subRoot.height = Math.max(height(subRoot.left),
                    height(subRoot.right));
        }
 
        return subRoot;
    }

    
    
    private Node minValueNode(Node node) {
        Node current = node;
 
        while (current.left != null) {
            current = current.left;
        }
 
        return current;
    }
//additional balancing fucntions
    private Node llRotation(Node subRoot) {
        Node leftNode = subRoot.left;
        subRoot.left = leftNode.right;
        leftNode.right = subRoot;
        if (leftNode != null) {
            recalculateHeight(leftNode);
        }
        return leftNode;
    }
 
    private Node rrRotation(Node subRoot) {
        Node rightNode = subRoot.right;
        subRoot.right = rightNode.left;
        rightNode.left = subRoot;
        if (rightNode != null) {
            recalculateHeight(rightNode);
        }
        return rightNode;
    }
 
    private Node lrRotation(Node subRoot) {
        subRoot.left = rrRotation(subRoot.left);
        return llRotation(subRoot);
    }
 
    private Node rlRotation(Node subRoot) {
        subRoot.right = llRotation(subRoot.right);
        return rrRotation(subRoot);
    }
 
    private void recalculateHeight(Node node) {
        if (node.left != null) {
            node.left.height = Math.max(height(node.left.left),
                    height(node.left.right));
        }
        if (node.right != null) {
            node.right.height = Math.max(height(node.right.left),
                    height(node.right.right));
        }
 
    }
 
    private int height(Node node) {
        if (node == null) {
            return 0;
        } else {
            return node.height + 1;
        }
    }
 
    @Override
    public String toString() {
        return rootTree.toString();
    }
}
 
public class Main {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        AVLTree tree2 = new AVLTree();
        PrintWriter output= new PrintWriter(System.out,true);
 
        tree.insert("MIT");
        tree.insert("Yale");
        tree.insert("Harvard");
        tree.insert("UPENN");
        tree.insert("Temple");
        tree.insert("CSU");
        tree.insert("NYU");
        tree.insert("CalTech");
        tree.insert("USC");
        tree.insert("UTAustin");
        tree.insert("OSU");
        tree.insert("Drexel");
 
        System.out.println("tree after insertion");
        tree.print(tree.rootTree, 0);
        //tree.walk(output, tree.rootTree);
        
        tree.remove("Drexel");
        
        System.out.println("tree after deletion");
        tree.print(tree.rootTree, 0);
        //System.out.println(tree);
        //tree.search(tree.rootTree, "MIT");
        
        Scanner op_num = new Scanner(System.in);
        System.out.println("*******USER CHOICES FOR NEW TREE*******\n");
        System.out.println("Tree Insert:1 \n");
        System.out.println("Tree Delete:2 \n");
        System.out.println("Tree Search:3 \n");
        System.out.println("Tree Print:4 \n");
        System.out.println("Tree Walk:5 \n");
        
        System.out.println("Enter a number: ");
        int op = op_num.nextInt();
        op_num.close();
        
        
        if (op==1){
        tree2.insert("Microsoft");
        tree2.insert("Google");
        tree2.insert("IBM");
        tree2.insert("DELL");
        tree2.insert("Facebook");
        tree2.insert("Amazon");
        System.out.println("Elements inserted into the tree \n");
        }
        if (op==2){
        tree2.insert("Microsoft");
        tree2.insert("Google");
        tree2.insert("IBM");
        tree2.insert("DELL");
        tree2.insert("Facebook");
        tree2.insert("Amazon");
        
        tree2.remove("Dell");
        System.out.println("Element deleted from the tree \n");
            
        }
           
        if (op==3){
        tree2.insert("Microsoft");
        tree2.insert("Google");
        tree2.insert("IBM");
        tree2.insert("DELL");
        tree2.insert("Facebook");
        tree2.insert("Amazon");
        
        tree2.search(tree2.rootTree,"Google");
        }
        if (op==4){
        tree2.insert("Microsoft");
        tree2.insert("Google");
        tree2.insert("IBM");
        tree2.insert("DELL");
        tree2.insert("Facebook");
        tree2.insert("Amazon");
        
        System.out.println("The Tree \n"); 
        tree2.print(tree2.rootTree,0);
        }
        if (op==5){
        tree2.insert("Microsoft");
        tree2.insert("Google");
        tree2.insert("IBM");
        tree2.insert("DELL");
        tree2.insert("Facebook");
        tree2.insert("Amazon");
        
        System.out.println("Tree elements:");
        tree2.walk(output,tree2.rootTree);
        }

        
        
    }
}