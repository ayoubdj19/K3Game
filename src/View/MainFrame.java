package View;
import Controller.GameController;
import Model.*;
import Model.Pion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Stack;
import java.util.jar.JarEntry;

import static javax.swing.GroupLayout.Alignment.CENTER;

public class MainFrame extends JFrame { // this class is the main frame of the game

    private JFrame mainFrame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private Pion[] pionSource = new Pion[1];
    private JLabel labelr;

    private Stack<Pion> history2Dtable = new Stack<Pion>();
    private Stack<Pion> historyPyramid = new Stack<Pion>();
    public MainFrame(Table2D table2DP1, Table2D table2DP2, Table2D baseK3, Pyramide p1Pyramide, Pyramide p2Pyramide, Pyramide K3) {
        mainFrame = new JFrame("K3");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1200, 800);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());

        // Create a panel to hold the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the image from a file
                Image backgroundImage = new ImageIcon("sources/Images/bg.png").getImage();
                // Draw the image on the panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JButton addButton = new JButton("Start Game");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel table2DP1Panel = tabel2DPanel(table2DP1);
                JPanel p1PyramidPanel = pyramidePanel(p1Pyramide, 1,0);
                JPanel baseK3Panel = baseK3(baseK3);
                //JPanel table2DP2Panel = tabel2DPanel(table2DP2);
                JPanel p2PyramidPanel = pyramidePanel(p2Pyramide,2,0);
                JPanel K3Panel = pyramidePanel(K3,1,1);
                JButton undoButton = undoButton(table2DP1, table2DP1Panel, p1PyramidPanel);
                JButton readyButton = readyButton(p2PyramidPanel, K3Panel, p1Pyramide);
                addPanel(Phase1(table2DP1Panel, p1PyramidPanel, baseK3Panel, undoButton, readyButton), "phase1");
                cardLayout.show(mainPanel, "phase1");

                addButton.setVisible(false);
            }
        });

        backgroundPanel.setLayout(new BorderLayout());

        // Add the background panel to the main frame
        mainFrame.setContentPane(backgroundPanel);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setLayout(cardLayout);
        mainPanel.setOpaque(false);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);

        // Add the button to the main frame
        mainFrame.add(addButton, BorderLayout.SOUTH);
    }

    public JPanel tabel2DPanel(Table2D table2D) {
        JPanel tablePanel = new JPanel(new GridLayout(table2D.getHeight(), table2D.getWidth(), 0, 0));
        int k = 0;
        //final int[] pionCount = {0};

        for (int i = 0; i < table2D.getHeight(); i++) {
            for (int j = 0; j < table2D.getWidth(); j++) {
                k++;
                Pion pion = table2D.getCases()[i][j];
                JLabel table2DLabel = new JLabel(pion.getImageIcon());
                if (k == table2D.getHeight() * table2D.getWidth()) {
                    table2DLabel.setVisible(false);
                } else {
                    tablePanel.add(table2DLabel);
                }
                int finalI = i;
                int finalJ = j;
                table2DLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        pionSource[0]=table2D.getPion(finalI, finalJ);
                        labelr = table2DLabel;
                        history2Dtable.push(pionSource[0]);
                    }
                });
            }
        }
        // transparent background
        tablePanel.setOpaque(false);
        //style of the panel
        return tablePanel;
    }

    public JPanel pyramidePanel(Pyramide pyramide , int s , int option) {
        JPanel pyramidePanel = new JPanel(new GridLayout(pyramide.getHight(), pyramide.getHight(), 0, 0));
        final int[] pionCount = {0};
        for (int i = 0; i < pyramide.getHight(); i++) {
            JPanel pionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            for (int j = 0; j <= i; j++) {
                Pion pion = pyramide.getPion(i, j);
                JLabel pyramideLabel = new JLabel(pion.getImageIcon());
                pionPanel.add(pyramideLabel);
                pionPanel.setOpaque(false);
                int finalI = i;
                int finalJ = j;
                pyramideLabel.addMouseListener((MouseListener) new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        switch (s){
                            case 1 : {
                                if (labelr != null) {
                                    Pion pionDestination = pyramide.getPion(finalI, finalJ);
                                        if(option ==1) {
                                            if(new GameController().testDeplacementPion(pionSource[0], pionDestination, pyramide)==true) {
                                                pyramideLabel.setIcon(labelr.getIcon());
                                                labelr.setVisible(false);
                                                labelr = null;
                                                pionDestination.setAccessible(false);
                                                pionCount[0]++;
                                            }
                                            }else{
                                            pionDestination.replacePion(pionSource[0]);
                                            pyramideLabel.setIcon(labelr.getIcon());
                                            pionCount[0]++;
                                            labelr.setVisible(false);
                                            labelr = null;
                                            pionDestination.setAccessible(false);
                                        }
                                        historyPyramid.push(pionDestination);

                                }
                                break;
                            }
                            case 2 : {
                                pionSource[0]=pyramide.getPion(finalI, finalJ);
                                labelr = pyramideLabel;
                            }
                        }
                    }
                });
                pyramidePanel.add(pionPanel);
            }
        }
        // transparent background
        pyramidePanel.setOpaque(false);
        return pyramidePanel;
    }

    public JPanel baseK3(Table2D table2){
        JPanel baseK3 = new JPanel(new GridLayout(table2.getHeight(), table2.getWidth(), 0, 0));
        for (int i = 0; i < table2.getHeight(); i++) {
            for (int j = 0; j < table2.getWidth(); j++) {
                Pion pion = table2.getCases()[i][j];
                JLabel table2DLabel = new JLabel(pion.getImageIcon());
                baseK3.add(table2DLabel);
            }
        }
        // transparent background
        baseK3.setOpaque(false);
        //style of the panel
        return baseK3;
    }


    public JPanel Phase1(JPanel pyramid, JPanel table2D, JPanel baseK3, JButton undoButton, JButton readyButton) {
        JPanel phase1 = new JPanel();
        phase1.setLayout(new BorderLayout(30, 30));
        phase1.add(table2D, BorderLayout.CENTER);
        phase1.add(pyramid, BorderLayout.NORTH);
        phase1.add(baseK3, BorderLayout.SOUTH);
        phase1.add(readyButton, BorderLayout.EAST);
        phase1.add(undoButton, BorderLayout.WEST);
        // transparent background
        phase1.setOpaque(false);
        return phase1;
    }

    public JPanel Phase2(JPanel p1Pyramide, JPanel p2Pyramide, JPanel K3){
        JPanel phase2 = new JPanel();
        phase2.setLayout(new GridLayout(1,3));
        JLabel feedbackLabelcenter = new JLabel(" ");
        JLabel feedbackLabelleft = new JLabel(" ");
        JButton quitter = new JButton("quitter");
        //size of the button
        quitter.setPreferredSize(new Dimension(50, 50));
        //width of button border
        quitter.setBorder(new LineBorder(Color.BLACK, 5));
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitter();
            }
        });
        //leftPanel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(feedbackLabelleft, BorderLayout.SOUTH);
        leftPanel.add(p1Pyramide, BorderLayout.NORTH);
        leftPanel.setOpaque(false);
        phase2.add(leftPanel, BorderLayout.WEST);
        //centerPanel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(feedbackLabelcenter, BorderLayout.SOUTH);
        centerPanel.add(K3, BorderLayout.NORTH);
        centerPanel.setOpaque(false);
        phase2.add(centerPanel, BorderLayout.WEST);
        //rightPanel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(quitter, BorderLayout.SOUTH);
        rightPanel.add(p2Pyramide, BorderLayout.NORTH);
        rightPanel.setOpaque(false);
        phase2.add(rightPanel, BorderLayout.WEST);
        // transparent background
        phase2.setOpaque(false);
        return phase2;
    }

    private void quitter() {
        System.exit(0);
    }


    public void addPanel(JPanel panelParam , String name) {
        // Create a new panel to hold the tabel2DPanel
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.add(panelParam);

        // Add the new panel to the main panel
        mainPanel.add(panel, name);
    }

    public JButton undoButton(Table2D table2D , JPanel tablePanel, JPanel pyramidPanel){
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tableX = history2Dtable.peek().getX();
                int tableY = history2Dtable.peek().getY();
                int pyramidX = historyPyramid.peek().getX();
                int pyramidY = historyPyramid.peek().getY();
                if (!history2Dtable.empty() && !historyPyramid.empty()) {
                    Pion pion2D = history2Dtable.pop();
                    Pion pionPyramid = historyPyramid.pop();
                    pionPyramid.resetPion();
                    table2D.getCases()[tableX][tableY].replacePion(pion2D);
                    pion2D.setAccessible(true);
                    // update the table's label with the pion's image icon
                    JLabel tableLabel = (JLabel) tablePanel.getComponent(tableX * table2D.getWidth() + tableY);
                    tableLabel.setIcon(pion2D.getImageIcon());
                    tableLabel.setVisible(true);
                    // update the pyramid's label with the pion's image icon
                    JPanel pionPanel = (JPanel) pyramidPanel.getComponent(pyramidX);
                    JLabel pyramidLabel = (JLabel) pionPanel.getComponent(pyramidY);
                    pyramidLabel.setIcon(pionPyramid.getImageIcon());
                }
            }
        });
        return undoButton;
    }
    public JButton readyButton(JPanel p2PyramidPanel, JPanel K3Panel, Pyramide p1Pyramide){
        Icon icon = new ImageIcon("sources/Images/READY.png");
        JButton readyButton = new JButton(icon);
        readyButton.setPreferredSize(new Dimension(122, 45));
        if(p1Pyramide.getNbPions()!=15){
            readyButton.setEnabled(true);
        }
        else{
        readyButton.setEnabled(false);
        readyButton.setVisible(false);}
        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //switch between the panels
                JPanel p1PyramidPanel = pyramidePanel(p1Pyramide, 2,1);
                addPanel(Phase2(p1PyramidPanel, p2PyramidPanel, K3Panel), "phase2");
                mainFrame.setSize(1920, 1080);
                cardLayout.show(mainPanel, "phase2");
            }
        });
        return readyButton;
    }
}