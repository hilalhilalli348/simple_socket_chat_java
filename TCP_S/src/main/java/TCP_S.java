import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Scanner;

public class TCP_S {

    public static void main(String[] args)   {


        final ServerSocket serverSocket;
        final Socket socket;
        final BufferedReader bufferedReader;
        final PrintWriter printWriter;
        final Scanner scanner = new Scanner(System.in);


        try {

            serverSocket = new ServerSocket(8000);
            socket = serverSocket.accept();
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));


            // mesajin gonderilmesi
            Thread sender = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    while(true){

                        msg = scanner.nextLine();
                        printWriter.write(msg+"\n");
                        printWriter.flush();

                    }
                }
            });

            sender.start();

            //mesajin qebul edilmesi/oxunulmasi

            Thread receive = new Thread(new Runnable() {

                @Override
                public void run() {

                    try {

                        String msg = bufferedReader.readLine();
                        while (msg!=null){
                            System.out.println("Client : "+msg);
                            msg = bufferedReader.readLine();
                        }

                        System.out.println("Client sohbetden cixdi");
                        bufferedReader.close();
                        socket.close();
                        serverSocket.close();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });

            receive.start();


        }catch (IOException ioException){
            ioException.printStackTrace();
        }

    }
}
