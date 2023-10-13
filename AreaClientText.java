import java.io.*;
import java.net.*;
import java.util.Scanner;

public class AreaClientText{
   private static final int PORT = 8000;
	private static final String SERVER = "localhost";
	
	public static void main ( String [] args ){
	   String server = SERVER;
	   int port = PORT;
		
		if ( args.length >= 1 )
		  server = args[0];
		if (args.length >= 2)
		  port = Integer.parseInt( args[1]);
		
		new AreaClientText ( server, port ); 
	}
	
	public AreaClientText( String server, int port ){
	  BufferedReader inputFromServer;
	  PrintStream outputToServer;
	  
	  try {
	    // create a socket to connect to the server
	    Socket socket = new Socket( server, port );
		 
		 // create data input/output streams
		 inputFromServer = new BufferedReader( new InputStreamReader( socket.getInputStream( ) ));
		 outputToServer = new PrintStream( new BufferedOutputStream( socket.getOutputStream( ) ));
		 
		 // create Scanner object to read radius from the keyboard
		 Scanner sc = new Scanner ( System.in );
		 System.out.print("Enter radius = ");
		 while ( sc.hasNextDouble( ) ){
		    // get a radius
			 double radius = sc.nextDouble( );
			 if ( radius < 0.0){
			    System.out.println("negative radius. Terminate the program");
				break;
			 }
			 // send radius to sever
			 String line = radius + "\r\n";
			 outputToServer.print( line );
			 outputToServer.flush( );
			 System.out.print(line);
			 // get area from server
			 String area = inputFromServer.readLine( );
			 
			 System.out.println("Radius is " + radius + " Area is " + area);
			 System.out.print("Enter radius = ");
		 }
		 inputFromServer.close();
		 outputToServer.close();
		 socket.close( );
		} catch ( IOException e ){
		  System.err.println( e );
		}
	
	}
	
}