import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChatFrame extends JFrame implements ActionListener {
	
	DatagramSocket sendSocket;
	InetAddress otherIP;
	int otherPort;
	TextField myTextField;
	private TextArea myTextArea;
	
	public static int BUFFER_SIZE = 2048;

	
	public ChatFrame(DatagramSocket sendSocket, final InetAddress otherIP, final int otherPort, String name ) {

		super();

		// save the socket
		// save the IP address of the user I am chatting with
		// save the port number of the user I am chatting with
		// All this is used when I send the message in the action listener

		this.sendSocket = sendSocket;
		this.otherIP = otherIP;
		this.otherPort = otherPort;

		setSize(600, 400);

		setResizable(true);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// this code is used when a ChatFrame is closed.
		// I want to remove the closed ChatFrame from my
		// from my collection of ChatFrames.

		addWindowListener(new WindowAdapter() {


		    public void windowClosing(WindowEvent e) {
		        DatagramSnR.removeChatFrame(otherIP, otherPort);
		    }
		});

		// set the title of the ChatFrame
		setTitle("Name: " + name + " IP: " + otherIP.getHostAddress());
		
		// create and format a JPanel for input field and the Send button
		JPanel textFieldPanel = new JPanel(new BorderLayout());

		// create the TextField
		// set the action listener that gets called when you press Enter
		// add the TextField to the JPanel

		this.myTextField = new TextField();
		myTextField.addActionListener(this);
		textFieldPanel.add(myTextField, BorderLayout.CENTER);

		// create the Send button
		// set the action listener for when it is pressed
		// the action listener should be the same as when the Enter is
		// pressed for the TextField
		// add the Send button to the JPanel
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(this);
		textFieldPanel.add(sendButton, BorderLayout.EAST);
		add(textFieldPanel, BorderLayout.SOUTH);

		// create the TextArea
		// add the TextArea to the ChatWindow
		this.myTextArea = new TextArea();
		this.myTextArea.setEditable(false);

		add(myTextArea, BorderLayout.CENTER);
		setVisible(true);

		}
//	places the message into a byte array
	public static byte[] createMessage(String msg) {
		//construct message
		byte[] message = new byte[BUFFER_SIZE];
		message = msg.getBytes();

		return message;
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {

		// Setup outbound message
		String outMessage = myTextField.getText().toString();
		byte[] sendMsg = new byte[BUFFER_SIZE];
		sendMsg = createMessage(outMessage);		
		
		DatagramPacket msgPacket = new DatagramPacket(sendMsg, sendMsg.length, otherIP, otherPort);
		
		myTextField.setText("");
		
		try {
			sendSocket.send(msgPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myTextArea.append("\n" + outMessage);
	}
	
	public void msgRec(String message) {
		myTextArea.append("\n" + message);
	}
	
	public InetAddress getAddress() {
		// TODO Auto-generated method stub
		return otherIP;
	}
	public int getPort() {
		// TODO Auto-generated method stub
		return otherPort;
	}


}