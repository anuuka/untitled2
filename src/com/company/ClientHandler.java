package com.company;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable {
    private static Socket clientDialog;
    public ClientHandler(Socket client){
        this.clientDialog = client;

    }
    @Override
    public void run(){
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut=null;
        String fileRequested = null;

        try {

                in = new BufferedReader(new InputStreamReader(clientDialog.getInputStream()));
                out = new PrintWriter(clientDialog.getOutputStream());
                dataOut = new BufferedOutputStream(clientDialog.getOutputStream());
                String input = in.readLine();
                StringTokenizer parse = new StringTokenizer(input);
                String method = parse.nextToken().toUpperCase();
                fileRequested = parse.nextToken().toLowerCase();
                System.out.println("Method:" + method);
                System.out.println("Request:" + fileRequested.substring(1));

                if (method.equals("GET") || method.equals("HEAD")) {
                    String content = getContentType(fileRequested);
                    String body = getBody(fileRequested.substring(1));

                    if (method.equals("GET")) {
                        out.println("Http");
                        out.println("Server: Java Http Server");
                        out.println("Date:" + new Date());
                        out.println("Content-type:" + content);

                        out.println("Content-length:" + body.length());
                        out.println();

                        out.flush();
                        dataOut.write(body.getBytes(), 0, body.length());
                        dataOut.flush();
                    }
                    System.out.println("Result sent: " + body);
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getContentType(String fileRequested) {
        return "text/html";
    }
    private String function(String input) throws Exception{
        String output = "";
        int count;
        input = input.toLowerCase();
        String words[] = input.split(" ");
        try{
            for(int i=0; i<words.length; i++){
                count = 1;
                for (int j=i+1; j< words.length; j++){
                    if(words[i].equals(words[j])){
                        count++;
                        words[j] = "0";
                    }
                }
                if(count>1 && words[i]!= "0")
                    output = output.concat(words[i]).concat(Integer.toString(count)).concat("\n");

            }
        }
        catch (Exception e){
            throw new Exception("Строка ввода недействительна");
        }
        return output;
    }
    private String getBody(String request) throws Exception {
        System.out.println("request: "+ request);

        return "<!DOCTYPE html>\n" +
                "<html lang = \" Russian\">\n" +
                "<head\n"
                + "\t<meta charset=\"utf-8\"/>\n"
                + "</head>\n"
                + "<body>\n"
                + "\t<h1>Full name : Orgilmaa Anudari</h1>\n"
                + "\t<h1>Group: IKB0-13-18</h1>\n"
                + "\t<h1>Individual assignment number: 18</h1>\n"
                + "\t<h2>The text of the individual task:" +
                " Counting identical ones\"</h2>\n"
                + "<h3>"
                + "Input : "
                + request + "</h3>\n"
                + "<h3>Output: <br>" + function(request) + "</h3>\n"
                +"</body>\n"
                + "</html>";
    }

}
