import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DatagramSnR extends Thread {
	
	//putting global vars here to make testing easier
//	public static String IPADDRESS_OUT = "192.168.1.155";
	public static int PORT = 64000;
	public static int BUFFER_SIZE = 2048;
	public static String USER_NAME = "Krirk";
	static DatagramSocket mSocket;
	static ArrayList<ChatFrame> mChats = new ArrayList<ChatFrame>();



	public void run() {
		//clear buffer
		byte[] incMsg = new byte[BUFFER_SIZE];
		clearArray(incMsg);
	
		//listen for messages
		while (true) {			
//            System.out.println("Listening . . .");
            //create receive packet
            DatagramPacket incPacket = new DatagramPacket(incMsg, incMsg.length);
            //attempt to receive the packet
            try {
				mSocket.receive(incPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
            
            /*
             * Extracting information from the packet
             */
            //not sure if still needed
            //ChatFrame class may be doing the same thing...
            String incMessage = new String(incPacket.getData());
            int senderPort = incPacket.getPort();
            InetAddress senderAddress = incPacket.getAddress();
            
        	clearArray(incMsg);
              
        	//checks ArrayList to see if the chat exists
        	boolean chat_exists = false;
        	int index = -1;
        	System.out.print("about to search");
        	for (int i = 0; i < mChats.size(); i++) {
        		InetAddress chkAddress = mChats.get(i).getAddress();
        		int chkPort = mChats.get(i).getPort();
        		System.out.println("chk: " + chkPort + " send: " + senderPort);
        		
        		if (chkAddress.equals(senderAddress) && chkPort == senderPort ) {
        			chat_exists = true;
        			index = i;
        		}
        	} //end for loop
            
            
        	System.out.println(incMessage);
            
        	if (chat_exists == true) {
        		//chat exists...use existing frame
        		mChats.get(index).msgRec(incMessage);
              	
        	} else if ( incMessage.substring(0, 11).equals("????? " + USER_NAME) ) {
        		//handles broadcasts directed at me
        		// ex: ????? Krirk

        		//figure out how to get the user name correct here
//                String senderName = extractName(incMessage);
        		System.out.println("print something");
                String name = "PlaceHolder";
            	broadcastReply( senderAddress, mSocket );
        		ChatFrame mFrame = new ChatFrame(mSocket, senderAddress, senderPort, name);
        		mFrame.msgRec(incMessage);
        		addToList(mFrame);
        	} else if (incMessage.substring(0,  5).equals("#####")) {
        		//what about response to my broadcast??????
            	//##### User's Name
        		System.out.println("lskjdfklsdjf");
        		String name = extractName(incMessage);
        		ChatFrame mFrame = new ChatFrame(mSocket, senderAddress, senderPort, name);
        		mFrame.msgRec(incMessage);
        		addToList(mFrame);
            	
        	}         	
        	


		} //end while loop for listening
	}



	public static void main(String[] args) {
		//create socket object
		DatagramSocket socket = createSocket();
		
		//opens initial menu frame
//		MenuFrame menu = new MenuFrame(socket);
		Menu2Frame menu = new Menu2Frame(socket);
		
		//starts listening for incoming messages
		Thread thread = new DatagramSnR();
		thread.start();


	}
    //create socket for incoming messages
	private static DatagramSocket createSocket() {
		try {
			mSocket = new DatagramSocket(PORT);

		} catch (SocketException e) {
			e.printStackTrace();
		}
		return mSocket;
	}
	//add the frame to the arraylist
	public static void addToList(ChatFrame mFrame) {
		mChats.add(mFrame);
	}
	//clear out byte array (buffer)
	private static byte[] clearArray(byte[] message) {
		
		for (int i = 0; i < message.length; i++) {
			message[i] = 0;
		}
		return message;
	}
	//creates InetAddress Object
	public static InetAddress createInetObj(String ipAddress) {
		InetAddress address = null;
		try {
			address = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return address;
		
	}
	
//	places the message into a byte array
	private static byte[] createMessage() {
		//construct message
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter a message: ");

		String msgToReceiver = null;
		try {
			msgToReceiver = input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] message = new byte[BUFFER_SIZE];
		message = msgToReceiver.getBytes();

		return message;
		
	}

	//on close, removes the object from the array
	public static void removeChatFrame(InetAddress otherIP, int otherPort) {
		int index = -1;
		boolean chat_exists = false;
		for (int i = 0; i < mChats.size(); i++) {
			InetAddress chkAddress = mChats.get(i).getAddress();
			int chkPort = mChats.get(i).getPort();
			if (chkAddress.equals(otherIP) && chkPort == otherPort ) {
				chat_exists = true;
				index = i;
			}
		}
		if (chat_exists == true) {
			mChats.remove(index);
		}
	}
	
	//method to extract name from hashtags
	public String extractName(String input) {
        //extracting the name from the hash tags.
        String senderName = input.substring(6, input.length());
        int findHashtag = senderName.indexOf('#');
        senderName = senderName.substring(0, findHashtag-1);
		return senderName;
	}
// broadcast protocol when we want to initiate a chat session with a user
	public static void broadcastProtocol(DatagramSocket mSocket, String name) {
		String strSearch = "????? " + name;
		
		String ipAddress = "255.255.255.255";
		InetAddress address = createInetObj(ipAddress);
		
		byte[] msg = ChatFrame.createMessage(strSearch);
		DatagramPacket bpPacket = new DatagramPacket(msg, msg.length, address, PORT);
		
		try {
			mSocket.send(bpPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//protocol when I am on the receiving end of a broadcast message requesting my IP
	public static void broadcastReply(InetAddress senderAddress, DatagramSocket mSocket) {
		String strReply = "##### " + USER_NAME + " ##### " + senderAddress.getHostAddress();
		byte[] reply = ChatFrame.createMessage(strReply);
		DatagramPacket replyPacket = new DatagramPacket(reply, reply.length, senderAddress, PORT);
		
		try {
			mSocket.send(replyPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}