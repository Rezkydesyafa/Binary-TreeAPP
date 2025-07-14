/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tree;

/**
 *
 * @author Lenovo
 */

import java.io.Serializable;
import java.util.*;

public class BinaryTree implements Serializable {
    private static final long serialVersionUID = 1L;
    public Node root;
    private Comparator<Object> comparator;

    public BinaryTree() {
        this.comparator = Comparator.comparing(Object::toString);
    }

    public void setComparator(Comparator<Object> comp) {
        this.comparator = comp;
    }

    public void insert(Object data) {
        root = insertRec(root, data);
    }
    
    private Node insertRec(Node node, Object data) {
        if (node == null) return new Node(data);
        if (comparator.compare(data, node.data) < 0) node.left = insertRec(node.left, data);
        else if (comparator.compare(data, node.data) > 0) node.right = insertRec(node.right, data);
        return node;
    }

    public boolean search(Object data) {
        clearHighlight(root);
        return searchRec(root, data);
    }
    
    private boolean searchRec(Node node, Object data) {
        if (node == null) return false;
        node.highlight = true;
        if (node.data.equals(data)) return true;
        return comparator.compare(data, node.data) < 0 ? searchRec(node.left, data) : searchRec(node.right, data);
    }

    public void delete(Object data) {
        root = deleteRec(root, data);
    }
    
    private Node deleteRec(Node node, Object data) {
        if (node == null) return null;
        if (comparator.compare(data, node.data) < 0) node.left = deleteRec(node.left, data);
        else if (comparator.compare(data, node.data) > 0) node.right = deleteRec(node.right, data);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            node.data = minValue(node.right);
            node.right = deleteRec(node.right, node.data);
        }
        return node;
    }
    
    private Object minValue(Node node) {
        Object min = node.data;
        while (node.left != null) {
            min = node.left.data;
            node = node.left;
        }
        return min;
    }

    public void update(Object oldVal, Object newVal) {
        delete(oldVal);
        insert(newVal);
    }

    // Fungsi untuk clear/reset tree
    public void clear() {
        root = null;
    }

    public List<Object> inorder() {
        List<Object> res = new ArrayList<>();
        inorderRec(root, res);
        return res;
    }
    
    private void inorderRec(Node node, List<Object> res) {
        if (node != null) {
            inorderRec(node.left, res);
            res.add(node.data);
            inorderRec(node.right, res);
        }
    }

    public List<Object> preorder() {
        List<Object> res = new ArrayList<>();
        preorderRec(root, res);
        return res;
    }
    
    private void preorderRec(Node node, List<Object> res) {
        if (node != null) {
            res.add(node.data);
            preorderRec(node.left, res);
            preorderRec(node.right, res);
        }
    }

    public List<Object> postorder() {
        List<Object> res = new ArrayList<>();
        postorderRec(root, res);
        return res;
    }
    
    private void postorderRec(Node node, List<Object> res) {
        if (node != null) {
            postorderRec(node.left, res);
            postorderRec(node.right, res);
            res.add(node.data);
        }
    }

    public List<Object> levelOrder() {
        List<Object> list = new ArrayList<>();
        if (root == null) return list;
        Queue<Node> q = new LinkedList<>(); 
        q.add(root);
        while (!q.isEmpty()) {
            Node n = q.poll(); 
            list.add(n.data);
            if (n.left != null) q.add(n.left);
            if (n.right != null) q.add(n.right);
        }
        return list;
    }

    public int height(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }
    
    public int countNodes(Node node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
    
    public int countLeaves(Node node) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) return 1;
        return countLeaves(node.left) + countLeaves(node.right);
    }
    
    public int countInternal(Node node) {
        if (node == null || (node.left == null && node.right == null)) return 0;
        return 1 + countInternal(node.left) + countInternal(node.right);
    }
    
    public boolean isBalanced(Node node) {
        return balance(node) != -1;
    }
    
    private int balance(Node node) {
        if (node == null) return 0;
        int l = balance(node.left); 
        if (l == -1) return -1;
        int r = balance(node.right); 
        if (r == -1) return -1;
        if (Math.abs(l - r) > 1) return -1;
        return Math.max(l, r) + 1;
    }
    
    public void clearHighlight(Node node) {
        if (node != null) {
            node.highlight = false;
            clearHighlight(node.left);
            clearHighlight(node.right);
        }
    }
    
    // Method untuk mengecek apakah tree kosong
    public boolean isEmpty() {
        return root == null;
    }
}