import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class MenuFrame extends JFrame implements ActionListener {
	
	DatagramSocket mSocket;
	InetAddress iNetAddress;
	JTextField tfIP;
	JTextField tfPort;
	int portNum;
	
	public MenuFrame(DatagramSocket mSocket) {
		
		super();
		
		this.mSocket = mSocket;
		
		//IP Address & Port menu
		JFrame menu = new JFrame("Chat++");
		menu.setPreferredSize( new Dimension(600, 150) );
		
		JPanel top = new JPanel();
		
		JLabel txtIP = new JLabel("IP Address");
		this.tfIP = new JTextField("",18);
		top.add(txtIP, BorderLayout.EAST);
		top.add(tfIP, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel();
		
		JLabel txtPort = new JLabel("Port No.");
		this.tfPort = new JTextField("",20);
		bottom.add(txtPort,  BorderLayout.EAST);
		bottom.add(tfPort, BorderLayout.CENTER);
				
		JButton btnAccept = new JButton("Accept");
        btnAccept.addActionListener(this);

		menu.add(top, BorderLayout.NORTH);
		menu.add(bottom, BorderLayout.CENTER);		
		menu.add(btnAccept, BorderLayout.SOUTH);
		
		menu.pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		menu.setVisible(true);
		
//		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String name = "temporary place holder";
		try {
			String address = tfIP.getText();
			InetAddress senderAddress = DatagramSnR.createInetObj(address);
		
			int senderPort = Integer.parseInt(tfPort.getText());
		
			ChatFrame mFrame = new ChatFrame(mSocket, senderAddress, senderPort, name);
			DatagramSnR.addToList(mFrame);
		}
		catch (NullPointerException nl) {
			nl.printStackTrace();
		}

	}

}
