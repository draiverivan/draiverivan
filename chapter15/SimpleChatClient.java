/*To run the chat client, you need two 
terminals. First, launch server 
from one terminal, then launch the 
client from another terminal
*/

/* You can use this server code for both versions of the Chat Client. Every possible 
disclaimer ever disclaimed is in effect here. To keep the code stripped down to the 
bare essentials, we took out a lot of parts that you’d need to make this a real server. 
In other words, it works, but there are at least a hundred ways to break it. */

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClient {
	JTextArea incoming;
	JTextField outgoing;
	BufferedReader reader;
	PrintWriter writer;
	Socket sock;

	public static void main(String[] args) {
		SimpleChatClient client = new SimpleChatClient();
		client.go();
	}

	public void go() {
		JFrame frame = new JFrame("Ludicrously Simple Chat Client");
		JPanel mainPanel = new JPanel();
		incoming = new JTextArea(15, 50);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outgoing = new JTextField(20);
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());
		mainPanel.add(qScroller);
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		setUpNetworking();

		/*
		 * We’re starting a new thread, using a new inner class as the Runnable (job)
		 * for the thread. The thread’s job is to read from the server’s socket stream,
		 * displaying any incoming messages in the scrolling text area
		 */
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();

		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(800, 500);
		frame.setVisible(true);

	} // close go

	/*
	 * We’re using the socket to get the input and output streams. We were already
	 * using the output stream to send to the server, but now we’re using the input
	 * stream so that the new ‘reader’ thread can get messages from the server.
	 */
	private void setUpNetworking() {
		try {
			sock = new Socket("127.0.0.1", 5000);
			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("networking established");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	} // close setUpNetworking

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
	} // close inner class

	/*
	 * This is what the thread does!! In the run() method, it stays in a loop (as
	 * long as what it gets from the server is not null), reading a line at a time
	 * and adding each line to the scrolling text area (along with a new line
	 * character).
	 */
	public class IncomingReader implements Runnable {
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("read: " + message);
					incoming.append(message + "\n");

				} // close while
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} // close run
	} // close inner class

} // close outer class