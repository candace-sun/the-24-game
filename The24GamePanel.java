import java.awt.*;
import java.awt.event.*;
import java.io.*;       
import java.util.*;  
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class The24GamePanel extends JPanel {
   private The24Game gameBrain;
   private JPanel numPanel, buttonPanel;
   private JButton cardButton, solutionButton;
   
   public The24GamePanel() throws Exception {
      setLayout(new BorderLayout());
      gameBrain = new The24Game();
      
      buttonPanel = new JPanel();
      buttonPanel.setLayout(new FlowLayout());
      buttonPanel.setBackground(Color.WHITE);
           
      solutionButton = new JButton("Get Solution");
      solutionButton.setBackground(new Color(255, 198, 48));
      solutionButton.setForeground(Color.BLACK);
      solutionButton.setFont(new Font("Courier", Font.BOLD, 13)); 
      solutionButton.setEnabled(true);
      solutionButton.addActionListener(new solutionListener());
      buttonPanel.add(solutionButton);
      
      cardButton = new JButton("Get New Card");
      cardButton.setBackground(new Color(255, 198, 48));
      cardButton.setForeground(Color.BLACK);
      cardButton.setFont(new Font("Courier", Font.BOLD, 13)); 
      cardButton.setEnabled(true);
      cardButton.addActionListener(new cardListener());
      buttonPanel.add(cardButton);
      
      add(buttonPanel, BorderLayout.SOUTH);
   }
   
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      setBackground(Color.WHITE);
      g.setColor(Color.BLACK);
      BufferedImage card = null;
      try {
         card = ImageIO.read(new File("24GameCardEdit3.png"));
      } catch (IOException e) {
         System.exit(0);
      }
      g.drawImage(card, 29, 60, null);      
      drawNums(g);
   }
   public void drawNums(Graphics g) {
      g.setFont(new Font("Serif", Font.BOLD, 90)); 
      gameBrain = new The24Game();
      int[] nums = gameBrain.getNums();
      g.drawString(""+nums[0], 206, 185);
      g.drawString(""+nums[1], 206, 391);
      g.drawString(""+nums[2], 92, 288);
      g.drawString(""+nums[3], 315, 288);
      
      g.setFont(new Font("Courier", Font.BOLD, 16)); 
      g.drawString("Get to 24 using only basic operations!", 53, 40);
   
   }
   
   private class cardListener implements ActionListener
   {   
      public void actionPerformed(ActionEvent e)
      {
         repaint();
         drawNums(getGraphics());
      } 
   }
   
   private class solutionListener implements ActionListener
   {   
      public void actionPerformed(ActionEvent e)
      {
         String s = gameBrain.getSolution();
         Graphics g = getGraphics();
         g.setFont(new Font("Courier", Font.ITALIC, 11)); 
         g.drawString(s, 40, 483);
      }
   }
}
