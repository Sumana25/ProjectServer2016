package com.sumanas.projectserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Karthik A on 03-05-16.
 */
public class ServerDiscoverer {
    public void respondToDiscovery() throws Exception
    {
        DatagramSocket socket = new DatagramSocket(Constants.DISCOVERY_PORT, InetAddress.getByName("0.0.0.0"));
        socket.setBroadcast(true);
        //System.out.println("Listen on " + socket.getLocalAddress() + " from " + socket.getInetAddress() + " port " + socket.getBroadcast());
        byte[] buf = new byte[512];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        while (true) {
            System.out.println("Waiting for discovery data");
            socket.receive(packet);
            System.out.println("Data received");
            String s = new String(packet.getData(), packet.getOffset(), packet.getLength());
            if(s.startsWith(Constants.DISCOVERY_MESSAGE)) {
                String data = Constants.DISCOVERY_MESSAGE_RESPONSE + ";"+Constants.SERVER_NAME;
                DatagramPacket spacket = new DatagramPacket(data.getBytes(), data
                        .length(), packet.getAddress(), Constants.DISCOVERY_PORT);
                socket.send(spacket);
                System.out.println("Sent response");
            }

        }
    }
}
