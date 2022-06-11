import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCP_C {
    public static void main(String[] args) {

        final Socket socket;
        final BufferedReader bufferedReader;
        final PrintWriter printWriter;
        final Scanner scanner = new Scanner(System.in);

        try {

            socket = new Socket("localhost", 8000);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));



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
                            System.out.println("Server : "+msg);
                            msg = bufferedReader.readLine();
                        }

                        System.out.println("Client sohbetden cixdi");
                        bufferedReader.close();
                        socket.close();


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });

            receive.start();


        }catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
