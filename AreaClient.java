import java.io.*;
import java.net.*;
import java.util.Scanner;
public class AreaClient{
    private static final int PORT = 8000;
    private static final String SERVER = "194";
    public static void main ( String [] args ){
        String server = SERVER;
        int port = PORT;
        if ( args.length >= 1 )
            server = args[0];
        if (args.length >= 2)
            port = Integer.parseInt( args[1]);
        new AreaClient ( server, port );
    }
    public AreaClient( String server, int port ){
        DataInputStream inputFromServer;
        DataOutputStream outputToServer;
        try {
// create a socket to connect to the server
            Socket socket = new Socket( server, port );
// create data input/output streams
            inputFromServer = new DataInputStream( socket.getInputStream( ) );
            outputToServer = new DataOutputStream( socket.getOutputStream( ) );
// create Scanner object to read radius from the keyboard
            Scanner sc = new Scanner ( System.in );
            System.out.print("Enter radius = ");
            while ( sc.hasNextDouble( ) ){
// get a radius
                double radius = sc.nextDouble( );
// send radius to sever
                outputToServer.writeDouble( radius );
                outputToServer.flush( );
// get area from server
                double area = inputFromServer.readDouble( );
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
