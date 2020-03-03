package edu.co.shortestjobfirst.UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import edu.co.shortestjobfirst.models.Process;
import java.util.Random;

/**
 *
 * @author Davidssantoss
 * @author juancsr
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
    public JPanel pnlDiagram = new JPanel();
    
    public JLabel lblTitle = new JLabel();
    public JLabel lblAutorD = new JLabel();
    public JLabel lblAutorJ = new JLabel();
    public JButton btnStart = new JButton("INICIAR");
    
    private JTable tblProcess;
    private JTable diagram;
    public JScrollPane scrollTableProcess;
    
    
    private final int screenWidth = 1024;
    private final int screenHeight = 720;
    
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
        
        pnlSubHeader.add(btnStart);
        btnStart.setBounds(850, 60, 100, 40);
        btnStart.setForeground(c1);
        btnStart.setFont(font2);
    }
    
    public void drawContent(){
        add(pnlContent);
        pnlContent.setLayout(null);
        pnlContent.setBounds(0, 225, screenWidth, 250);
        pnlContent.setBackground(c2);
    }
    
    public void drawTable(List<Process> processes){
        tblProcess = new JTable(processes.size(), 7);
        
        for (int i = 0; i < processes.size(); i++) {
            for (int j = 0; j < 3; j++) {
                tblProcess.setValueAt(processes.get(i).getProcessName(), i, 0);
                tblProcess.setValueAt(processes.get(i).getArriveTime(), i, 1);
                tblProcess.setValueAt(processes.get(i).getExecutionTime(), i, 2);
            }
        }
        
        tblProcess.setPreferredScrollableViewportSize(new Dimension(700, 150));
        tblProcess.setFillsViewportHeight(true);
        tblProcess.setBackground(c2);
        tblProcess.setForeground(Color.BLACK);
        tblProcess.setFont(font2);
        tblProcess.setEnabled(false);
        
        scrollTableProcess = new JScrollPane(tblProcess);
        pnlContent.add(scrollTableProcess);
        
        scrollTableProcess.setBounds(10, 10, 700, 200);
        scrollTableProcess.setBackground(c2);
        scrollTableProcess.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        changeColumNames();
    }
    
    /**
     * 
     * @param value valor de la casilla
     * @param row fila
     * @param column  columna
     */
    public void insertValueProcesses(String value, int row, int column) {
        tblProcess.setValueAt(value, row, column);
        tblProcess.repaint();
    }
    
    /**
     * @param process       Nombre del proceso
     * @param arriveTime    Hora de llegada
     * @param executeTime   Tiempo de rafaga
     * @param startTime     Tiempo de comienzo
     * @param endTime       Tiempo de finalización
     * @param returnedTime  Tiempo de retorno
     * @param waitTime      Tiempo de espera
     */
    public void addTableRow(String process, int arriveTime, int executeTime, 
            int startTime, int endTime, int returnedTime, int waitTime) {
        
        DefaultTableModel model = (DefaultTableModel) tblProcess.getModel();
        model.addRow(new Object[]{process, arriveTime, executeTime, startTime,
            returnedTime, endTime, waitTime});
    }
    
    public void addDiagramRow(String processName) {
        
        DefaultTableModel model = (DefaultTableModel) diagram.getModel();
        String []row = new String[diagram.getColumnCount()];
        row[0] = processName;
        for (int i = 1; i < diagram.getColumnCount(); i++) {
            row[i] = Integer.toString(i);
        }
        model.addRow(row);
    }
    
    
    public void drawDiagram() {
        add(pnlDiagram);
        pnlDiagram.setBackground(c2);
        pnlDiagram.setLayout(null);
        pnlDiagram.setBounds(0, 475, screenWidth, 225);
        pnlDiagram.setBorder(BorderFactory.createLineBorder(Color.black));
        
        diagram = new JTable(0, 10);
        diagram.setEnabled(false);
        diagram.setBackground(c2);
        diagram.setPreferredScrollableViewportSize(new Dimension(screenWidth - 50, 150));
        diagram.setFillsViewportHeight(true);
        
        JScrollPane scroll = new JScrollPane(diagram);
        scroll.setBounds(10, 10, screenWidth - 200, 150);
        
        pnlDiagram.add(scroll);
    }
    
    public Color getRandomColor() {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }
    
    public void paintCell(int column, int row, Color color) {
        ColorColumnRenderer cellRender = new ColorColumnRenderer();
        try {
            System.out.println("Pintando: "+column+","+row);
            if (column > 9) {
                System.out.println("Agregando columna");
                addDiagramColumn();
            }
            TableColumn tableColumn = diagram.getColumnModel().getColumn(column);
            cellRender.setRowToColor(row);
            cellRender.setColor(color);
            tableColumn.setCellRenderer(cellRender);
        } catch(Exception e) {
            System.out.println("column: "+column+" row: "+row);
            System.out.println("Error en GUI.paintCell(): "+e.getMessage());
        } finally {
            diagram.repaint();
        }
    }
    
    void addDiagramColumn() {
        DefaultTableModel model = (DefaultTableModel) diagram.getModel();
        model.addColumn(model.getColumnCount());
    }
    
    private void changeColumNames() {
        JTableHeader tblHeader = tblProcess.getTableHeader();
        TableColumnModel tcm = tblHeader.getColumnModel();
        TableColumn tblColumn = tcm.getColumn(0);
        tblColumn.setHeaderValue("Proceso");
        TableColumn tblColumn2 = tcm.getColumn(1);
        tblColumn2.setHeaderValue("T. Llegada");
        TableColumn tblColumn3 = tcm.getColumn(2);
        tblColumn3.setHeaderValue("T. Rafaga");
        TableColumn tblColumn4 = tcm.getColumn(3);
        tblColumn4.setHeaderValue("T. Comienzo");
        TableColumn tblColumn5 = tcm.getColumn(4);
        tblColumn5.setHeaderValue("T. Final");
        TableColumn tblColumn6 = tcm.getColumn(5);
        tblColumn6.setHeaderValue("T. Retorno");
        TableColumn tblColumn7 = tcm.getColumn(6);
        tblColumn7.setHeaderValue("T. Espera");
        tblHeader.repaint();
    }
}
