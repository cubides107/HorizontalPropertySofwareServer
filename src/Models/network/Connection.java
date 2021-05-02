package Models.network;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Connection {

    private final DataOutputStream outputChanel;
    private final DataInputStream inputChanel;

    public Connection(Socket socket) throws IOException {
        outputChanel = new DataOutputStream(socket.getOutputStream());
        inputChanel = new DataInputStream(socket.getInputStream());
    }

    public void sendMap(int[][] matrix) {
        try {
            outputChanel.writeUTF(Arrays.deepToString(matrix));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void writeUTF(String message) {
        try {
            outputChanel.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCoordinates(String coordinates) {
        try {
            outputChanel.writeUTF(coordinates);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readUTF() {
        String message = "";
        try {
            message = inputChanel.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public void writeBoolean(boolean value){
        try {
            outputChanel.writeBoolean(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean available() throws IOException {
        return inputChanel.available() > 0;
    }

    public int readInt(){
        int intAux = 0;
        try {
           intAux = inputChanel.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return intAux;
    }
    public byte[] readImageUser() throws IOException {

        int sizeFile = inputChanel.readInt();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputChanel);

        byte[] buffer = new byte[sizeFile];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (byte) bufferedInputStream.read();
        }
        return buffer;
    }

    public void writeInt(int number){
        try {
            outputChanel.writeInt(number);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendImage(byte[] imageBytes) throws IOException {
        try {
            outputChanel.writeInt(imageBytes.length);
            BufferedOutputStream bufferedOutput = new BufferedOutputStream(outputChanel);
            for (byte imageByte : imageBytes) {
                bufferedOutput.write(imageByte);
            }
            bufferedOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


