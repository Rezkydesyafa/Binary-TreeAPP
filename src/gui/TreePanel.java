/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import tree.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class TreePanel extends JPanel {
    private Node root;
    public Color bgColor = Color.WHITE;
    public Color textColor = Color.BLACK;
    public Color lineColor = Color.BLACK;
    public Color fillColor = new Color(70, 130, 180);
    
    private final Font nodeFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);

    public TreePanel(Node root) {
        this.root = root;
        setPreferredSize(new Dimension(800, 400));
        setBackground(bgColor);
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void setColors(Color bg, Color txt, Color line, Color fill) {
        this.bgColor = bg;
        this.textColor = txt;
        this.lineColor = line;
        this.fillColor = fill;
        setBackground(bgColor);
        repaint();
    }

    private void drawTree(Graphics2D g, Node n, int x, int y, int dx) {
        if (n == null) return;

        // Gambar garis terlebih dahulu agar berada di belakang node
        g.setColor(lineColor);
        g.setStroke(new BasicStroke(2));
        
        if (n.left != null) {
            g.drawLine(x, y, x - dx, y + 70);
        }
        if (n.right != null) {
            g.drawLine(x, y, x + dx, y + 70);
        }

        // Gambar node (lingkaran)
        g.setColor(n.highlight ? Color.GREEN : fillColor);
        g.fillOval(x - 20, y - 20, 40, 40);
        
        // Gambar border lingkaran
        g.setColor(lineColor);
        g.drawOval(x - 20, y - 20, 40, 40);

        // Gambar teks di tengah lingkaran
        g.setColor(textColor);
        g.setFont(nodeFont);
        String text = n.data != null ? n.data.toString() : "";
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g.drawString(text, x - textWidth / 2, y + textHeight / 2 - 2);

        // Rekursi untuk menggambar subtree
        if (n.left != null) {
            drawTree(g, n.left, x - dx, y + 70, dx / 2);
        }
        if (n.right != null) {
            drawTree(g, n.right, x + dx, y + 70, dx / 2);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Enable antialiasing untuk gambar yang lebih smooth
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        if (root != null) {
            drawTree(g2d, root, getWidth() / 2, 50, getWidth() / 4);
        } else {
            // Tampilkan pesan jika tree kosong
            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 16));
            String emptyMessage = "Empty Tree";
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(emptyMessage);
            g2d.drawString(emptyMessage, 
                         (getWidth() - textWidth) / 2, 
                         getHeight() / 2);
        }
    }

    public void saveAsImage(String path) throws Exception {
        if (!path.toLowerCase().endsWith(".png")) {
            path += ".png";
        }
        
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        
        // Enable antialiasing untuk gambar yang disimpan
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Paint component ke image
        paint(g2);
        g2.dispose();
        
        // Simpan image
        ImageIO.write(img, "png", new File(path));
    }
}