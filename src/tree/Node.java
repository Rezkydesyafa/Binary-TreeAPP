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

public class Node implements Serializable {
    public Object data;
    public Node left, right;
    public boolean highlight = false;

    public Node(Object data) {
        this.data = data;
        this.left = this.right = null;
    }
}