package ClientHeartRateTeam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientUI extends JPanel implements ActionListener {

  private static ClientUI instance = null;

  private final UIElement [] UIelements = new UIElement[5];
  
  public static ClientUI getInstance() {
    if (instance == null)
      instance = new ClientUI();

    return instance;
  }

  private ClientUI() {
    this.setBackground(Color.WHITE);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    
    for(int i=0 ;i<5;i++) {
      this.UIelements[i]=(new UIElement(new ClientSubscriber("localhost",1594)));
      this.add(UIelements[i]);
    }
}

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Client");
    frame.getContentPane().setLayout(new GridLayout(1, 1));
    frame.setLayout(new GridLayout(1, 1));
    frame.getContentPane().add(ClientUI.getInstance());
    frame.addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        //model.shutdown();
        System.exit(0);
      }
    });
    frame.pack();
    frame.setVisible(true);
  }
}
