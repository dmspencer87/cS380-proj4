
/************************************************************************************
 *	file: Ipv6Client
 *	author: Daniel Spencer
 *	class: CS 380 - computer networks
 *
 *	assignment: Project 4
 *	date last modified: 11/05/2017
 *
 *	purpose:    Applying the same techniques used in project 3 to implement Ipv6.
 *
 ************************************************************************************/
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class Ipv6Client{
    public static void main(String[] args)throws IOException{
        byte[] packet;
        try(Socket socket = new Socket("18.221.102.182", 38004)){
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            for(int i = 1; i < 13; i++){
                int data = (int)Math.pow(2.0, i);
                packet = new byte[(short)(40 + data)];
                packet[0] = 0b01100000;
                packet[1] = 0;
                packet[2] = 0;
                packet[3] = 0;
                packet[4] = (byte)((data & 0xFF00) >> 8);
                packet[5] = (byte)(data & 0x00FF);
                packet[6] = 17;
                packet[7] = 20;

                for(int j = 8; j < 18; j++)
                    packet[j] = 0;
                for(int j = 18; j < 20; j++)
                    packet[j] = (byte)0xFF;
                packet[20] = (byte) 0x12;
                packet[21] = (byte) 0x01;
                packet[22] = (byte) 0xAF;
                packet[23] = (byte) 0x12;
                for(int j = 24; j < 34; j++)
                    packet[j] = 0;
                for(int j = 34; j < 36; j++)
                    packet[j] = (byte)0xFF;
//                for(int j = 0; j < 4; j++)
//                    packet[j+36] = destSource[j];
//                for(int j = 24; j < 34; ++j)
//                    packet[j] = (byte) 0;
                packet[36] = (byte) 0x12;
                packet[37] = (byte) 0xdd;
                packet[38] = (byte) 0x66;
                packet[39] = (byte) 0xb6;
                out.write(packet);
                byte[] responseCode = new byte[4];
                in.read(responseCode);
                System.out.println("data length: " + data);
                System.out.print("Response: 0x");
                for(byte b: responseCode)
                    System.out.printf("%X", b);
                System.out.println("\n");
            }
        }
    }
}