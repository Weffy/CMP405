
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String USER_NAME = "Krirk";
		String input = "##### " + USER_NAME + " ##### " + "192.168.1.2";
        String senderName = input.substring(6, input.length());
        int findHashtag = senderName.indexOf('#');
        senderName = senderName.substring(0, findHashtag-1);
		
        System.out.println(input.substring(0, 5));
	}

}
