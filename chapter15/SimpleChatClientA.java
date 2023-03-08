import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClientA {

  JTextField outgoing;
  PrintWriter writer;
  Socket sock;

  public void go() {
    JFrame frame = new JFrame("Ludicrously Simple Chat Client");
    JPanel mainPanel = new JPanel();
    outgoing = new JTextField(20);
    JButton sendButton = new JButton("Send");
    sendButton.addActionListener(new SendButtonListener());
    mainPanel.add(outgoing);
    mainPanel.add(sendButton);
    frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
    setUpNetworking();
    frame.setSize(400, 500);
    frame.setVisible(true);
  } // close go

  /*
   * This is where we make the Socket and the PrintWriter (it’s called from the
   * go() method right before displaying the app GUI)
   */
  private void setUpNetworking() {
    try {
      /*
       * we’re using localhost so you can test the client and server on one machine
       */
      sock = new Socket("127.0.0.1", 5000);
      writer = new PrintWriter(sock.getOutputStream());
      System.out.println("networking established");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  } // close setUpNetworking

  /*
   * Now we actually do the writing. Remember, the writer is chained to the output
   * stream from the Socket, so whenever we do a println(), it goes over the
   * network to the server!
   */
  public class SendButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      try {
        writer.println(outgoing.getText());
        writer.flush();

      } catch (Exception ex) {
        ex.printStackTrace();
      }
      outgoing.setText("");
      outgoing.requestFocus();
    }
  } // close SendButtonListener inner class

  public static void main(String[] args) {
    new SimpleChatClientA().go();
  }
} // close outer class