package sample;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class UdpPackage {
    private Date date;
    private String name;
    private byte[] data;
    private InetAddress fromIp;
    private InetAddress toIp;
    private int fromPort;
    private int toPort;

    public UdpPackage(String data, InetAddress fromIp, InetAddress toIp, int formPort, int toPort) {
        this(data.getBytes(), fromIp, toIp,  formPort,  toPort);
    }

    public UdpPackage(String name, byte[] data, String fromIp, String toIp, int formPort, int toPort) throws UnknownHostException {
        this(data, InetAddress.getByName(fromIp), InetAddress.getByName(toIp),  formPort,  toPort);
    }

    public UdpPackage(byte[] data, InetAddress fromIp, InetAddress toIp, int formPort, int toPort) {
        this.data = data;
        this.fromIp = fromIp;
        this.toIp = toIp;
        this.fromPort = formPort;
        this.toPort = toPort;
        this.setDate(new Date(System.currentTimeMillis()));
    }

    public byte[] getDataAsBytes() {
        return data;
    }

    public String getDataAsString() {
        return new String(data);
    }

    public String getDataAsHex()
    {
        StringBuilder hex = new StringBuilder();
        for (byte b : data) {
            hex.append(String.format("%02X", (int)b & 0x0FFFFF));
            hex.append(":");
        }
        return hex.toString();
    }


    public void setData(String data) {
        this.data = data.getBytes();
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public InetAddress getFromIp() {
        return fromIp;
    }

    public void setFromIp(InetAddress fromIp) {
        this.fromIp = fromIp;
    }

    public InetAddress getToIp() {
        return toIp;
    }

    public void setToIp(InetAddress toIp) {
        this.toIp = toIp;
    }

    public int getFromPort() {
        return fromPort;
    }

    public void setFromPort(int fromPort) {
        this.fromPort = fromPort;
    }

    public int getToPort() {
        return toPort;
    }

    public void setToPort(int toPort) {
        this.toPort = toPort;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
