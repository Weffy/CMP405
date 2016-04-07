import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menu2Frame extends JFrame implements ActionListener {

	
	JTextField tfName;
	DatagramSocket mSocket;
	String USER_NAME = "Krirk";
	
	public Menu2Frame(DatagramSocket mSocket) {
		
		this.mSocket = mSocket;
		
		
		JFrame menu = new JFrame("Chat++");
		menu.setPreferredSize( new Dimension(600, 150) );

		JPanel top = new JPanel();

		JLabel jlName = new JLabel("Name: ");
		this.tfName = new JTextField("", 20);
		tfName.addActionListener(this);
		
		top.add(jlName, BorderLayout.EAST);
		top.add(tfName, BorderLayout.CENTER);

		JButton btnBroadcast = new JButton("Send Broadcast");
		btnBroadcast.addActionListener(this);
		
		menu.add(top, BorderLayout.CENTER);
		menu.add(btnBroadcast, BorderLayout.SOUTH);
		
		menu.pack();
		
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		menu.setVisible(true);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String name = tfName.getText().toString();
		DatagramSnR.broadcastProtocol(mSocket, name);
		tfName.setText("");
	}

}
