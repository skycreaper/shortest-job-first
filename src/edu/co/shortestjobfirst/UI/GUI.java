package edu.co.shortestjobfirst.UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Davidssantoss
 * @author Juancsr
 */
public class GUI extends JFrame{
    public Color c1 = new Color(22, 160, 133);  // rgb(22, 160, 133)
    public Color c2 = new Color(26, 188, 156);  // rgb(26, 188, 156)
    public Color c3 = new Color(223, 249, 251);
    public Font font = new Font("Agency FB", Font.BOLD, 34);
    public Font font2 = new Font("Agency FB", Font.BOLD, 20);
    
    public JPanel pnlHeader = new JPanel();
    public JPanel pnlSubHeader = new JPanel();
    public JPanel pnlContent = new JPanel();
    
    public JLabel lblTitle = new JLabel();
    public JLabel lblAutorD = new JLabel();
    public JLabel lblAutorJ = new JLabel();
    public JButton btnStart = new JButton();
    public JTable tblProcess;
    public JScrollPane scrollTableProcess;
    
    private int screenWidth = 1024;
    private int screenHeight = 720;
    
    public GUI() {
        Container c = getContentPane();
        c.setLayout(null);
        this.setTitle("SHORTEST JOB FIRST");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawHeader();
        drawSubHeader();
        drawContent();
        
        setSize(screenWidth,screenHeight);
        setVisible(true);
    }
    public void drawHeader(){
        add(pnlHeader);
        pnlHeader.setBounds(0, 0, screenWidth, 150);
        pnlHeader.setBackground(c1);
        pnlHeader.setLayout(null);
        pnlHeader.add(lblTitle);
        lblTitle.setBounds(screenWidth / 3 + 50, 55, 350, 40);
        lblTitle.setFont(font);
        lblTitle.setText("SHORTEST JOB FIRST");
        lblTitle.setForeground(c3);
    }
    public void drawSubHeader(){
        add(pnlSubHeader);
        pnlSubHeader.setBounds(0, 150, screenWidth, 75);
        pnlSubHeader.setBackground(c2);
        pnlSubHeader.setLayout(null);
        pnlSubHeader.add(lblAutorD);
        lblAutorD.setBounds(774, 10, 250, 25);
        lblAutorD.setFont(font2);
        lblAutorD.setText("DAVID STEVEN SANTOS SANTOS");
        lblAutorD.setForeground(c3);
        pnlSubHeader.add(lblAutorJ);
        lblAutorJ.setBounds(774, 35, 250, 25);
        lblAutorJ.setFont(font2);
        lblAutorJ.setText("JUAN CAMILO SARMIENTO REYES");
        lblAutorJ.setForeground(c3);
    }
    public void drawContent(){
        add(pnlContent);
        pnlContent.setLayout(null);
        pnlContent.setBounds(0, 225, screenWidth, 250);
        pnlContent.setBackground(c2);
    }
    public void drawTable(){
        
    }
    
}
