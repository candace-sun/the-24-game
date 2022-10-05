import javax.swing.JFrame;
   public class The24GameDriver  {
      public static void main(String[] args) throws Exception {
         JFrame frame = new JFrame("The 24 Game");
         frame.setLocation(400, 50);
         frame.setSize(475, 580);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.getContentPane().add(new The24GamePanel());
         frame.setVisible(true);
      }
   }