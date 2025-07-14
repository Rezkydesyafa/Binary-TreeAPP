/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author Lenovo
 */

import tree.BinaryTree;
import tree.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

public class TreeGUI extends JFrame {
    private final BinaryTree tree = new BinaryTree();
    private final JTextField inputField = new JTextField(8);
    private final JTextArea outputArea = new JTextArea(4, 30);
    private final TreePanel panel = new TreePanel(null);
    private final JList<String> dataList = new JList<>(new String[]{"Int (32)", "String", "Char", "Float"});

    public TreeGUI() {
        super("Binary Tree App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        // Panel atas dengan input dan tombol
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Value:"));
        top.add(inputField);
        
        String[] btns = {"Insert", "Delete", "Search", "Update", "Inorder", "Preorder", "Postorder", "LevelOrder", "Stats", "SaveImg", "Clear"};
        for (String s : btns) {
            JButton b = new JButton(s);
            b.addActionListener(this::act);
            top.add(b);
        }
        add(top, BorderLayout.NORTH);

        // Area output
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        // Panel kiri dengan data type dan color chooser
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.add(treeDataPanel(), BorderLayout.NORTH);
        leftPanel.add(colorChooserPanel(), BorderLayout.SOUTH);
        add(leftPanel, BorderLayout.WEST);

        // Panel tengah untuk tree visualization
        add(panel, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel treeDataPanel() {
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createTitledBorder("Tree Data Type"));
        dataList.setVisibleRowCount(4);
        dataList.setSelectedIndex(0);
        dataList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        p.add(new JScrollPane(dataList));
        return p;
    }

    private JPanel colorChooserPanel() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 2));
        p.setBorder(BorderFactory.createTitledBorder("Colors"));

        JButton bg = new JButton("Background");
        JButton text = new JButton("Text");
        JButton line = new JButton("Lines");
        JButton fill = new JButton("Nodes Fill");

        bg.addActionListener(e -> chooseColor("Background", c -> panel.bgColor = c));
        text.addActionListener(e -> chooseColor("Text", c -> panel.textColor = c));
        line.addActionListener(e -> chooseColor("Line", c -> panel.lineColor = c));
        fill.addActionListener(e -> chooseColor("Fill", c -> panel.fillColor = c));

        p.add(bg); 
        p.add(line); 
        p.add(text); 
        p.add(fill);
        return p;
    }

    private void chooseColor(String title, java.util.function.Consumer<Color> setter) {
        Color c = JColorChooser.showDialog(this, "Choose " + title, panel.bgColor);
        if (c != null) {
            setter.accept(c);
            panel.setColors(panel.bgColor, panel.textColor, panel.lineColor, panel.fillColor);
        }
    }

    private void act(ActionEvent e) {
        String cmd = e.getActionCommand();
        String v = inputField.getText().trim();
        inputField.setText("");
        
        try {
            switch (cmd) {
                case "Insert" -> {
                    if (v.isEmpty()) {
                        output("Error: Please enter a value to insert");
                        return;
                    }
                    Object val = parseValue(v);
                    tree.insert(val);
                    output("Inserted: " + val);
                }
                case "Delete" -> {
                    if (v.isEmpty()) {
                        output("Error: Please enter a value to delete");
                        return;
                    }
                    Object val = parseValue(v);
                    tree.delete(val);
                    output("Deleted: " + val);
                }
                case "Search" -> {
                    if (v.isEmpty()) {
                        output("Error: Please enter a value to search");
                        return;
                    }
                    Object val = parseValue(v);
                    boolean found = tree.search(val);
                    output("Search result for " + val + ": " + (found ? "Found" : "Not Found"));
                }
                case "Update" -> {
                    if (v.isEmpty()) {
                        output("Error: Please enter old value and new value separated by comma (e.g., 5,10)");
                        return;
                    }
                    String[] parts = v.split(",");
                    if (parts.length != 2) {
                        output("Error: Update format should be 'oldValue,newValue'");
                        return;
                    }
                    Object oldVal = parseValue(parts[0].trim());
                    Object newVal = parseValue(parts[1].trim());
                    tree.update(oldVal, newVal);
                    output("Updated: " + oldVal + " → " + newVal);
                }
                case "Inorder" -> {
                    List<Object> result = tree.inorder();
                    output("Inorder: " + (result.isEmpty() ? "Empty tree" : join(result)));
                }
                case "Preorder" -> {
                    List<Object> result = tree.preorder();
                    output("Preorder: " + (result.isEmpty() ? "Empty tree" : join(result)));
                }
                case "Postorder" -> {
                    List<Object> result = tree.postorder();
                    output("Postorder: " + (result.isEmpty() ? "Empty tree" : join(result)));
                }
                case "LevelOrder" -> {
                    List<Object> result = tree.levelOrder();
                    output("Level Order: " + (result.isEmpty() ? "Empty tree" : join(result)));
                }
                case "Stats" -> stats();
                case "SaveImg" -> {
                    if (tree.isEmpty()) {
                        output("Error: Cannot save empty tree");
                        return;
                    }
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Save Tree Image");
                    chooser.setSelectedFile(new java.io.File("tree.png"));
                    if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                        panel.saveAsImage(chooser.getSelectedFile().getAbsolutePath());
                        output("Image saved to: " + chooser.getSelectedFile().getAbsolutePath());
                    }
                }
                case "Clear" -> {
                    if (tree.isEmpty()) {
                        output("Tree is already empty");
                        return;
                    }
                    int result = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to clear the entire tree?",
                        "Confirm Clear",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );
                    if (result == JOptionPane.YES_OPTION) {
                        tree.clear();
                        output("Tree cleared successfully");
                    }
                }
            }
        } catch (NumberFormatException ex) {
            output("Error: Invalid number format for " + getSelectedDataType());
        } catch (IllegalArgumentException ex) {
            output("Error: " + ex.getMessage());
        } catch (Exception ex) {
            output("Error: " + ex.getMessage());
        }
        
        panel.setRoot(tree.root);
        panel.repaint();
    }

    private Object parseValue(String v) throws IllegalArgumentException {
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalArgumentException("Value cannot be empty");
        }
        
        String type = dataList.getSelectedValue();
        v = v.trim();
        
        try {
            if (type.startsWith("Int")) {
                return Integer.parseInt(v);
            } else if (type.equals("Float")) {
                return Float.parseFloat(v);
            } else if (type.equals("Char")) {
                if (v.length() != 1) {
                    throw new IllegalArgumentException("Char must be exactly one character");
                }
                return v.charAt(0);
            } else { // String
                return v;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format for " + type + ": " + v);
        }
    }

    private String getSelectedDataType() {
        return dataList.getSelectedValue();
    }

    private void stats() {
        if (tree.isEmpty()) {
            output("Statistics: Empty tree");
            return;
        }
        
        int total = tree.countNodes(tree.root);
        int leaves = tree.countLeaves(tree.root);
        int internal = tree.countInternal(tree.root);
        int height = tree.height(tree.root);
        boolean balanced = tree.isBalanced(tree.root);
        
        StringBuilder stats = new StringBuilder();
        stats.append("Tree Statistics:\n");
        stats.append("Total nodes: ").append(total).append("\n");
        stats.append("Leaf nodes: ").append(leaves).append("\n");
        stats.append("Internal nodes: ").append(internal).append("\n");
        stats.append("Height: ").append(height).append("\n");
        stats.append("Balanced: ").append(balanced ? "Yes" : "No");
        
        output(stats.toString());
    }

    private void output(String message) {
        outputArea.setText(message);
    }

    private String join(List<Object> list) {
        return list.stream().map(String::valueOf).collect(Collectors.joining(" → "));
    }

    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            // Use default look and feel
        }
        
        SwingUtilities.invokeLater(() -> {
            new TreeGUI();
        });
    }
}