package it.itismeucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class MyThread extends Thread{
    Socket s;
    public MyThread (Socket s){
        this.s = s;
    }

    @Override
    public void run(){
        System.out.println("qualcuno si è collegato");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            ArrayList<String> note = new ArrayList<>();
            String scelta;
            String stringaRicevuta;
            do {
                scelta = in.readLine();
                switch (scelta) {
                    case "1":
                        stringaRicevuta = in.readLine();
                        note.add(stringaRicevuta);
                        out.writeBytes("OK" + "\n");
                        break;
                    case "?":
                        for(int i = 0; i<note.size(); i++){
                            out.writeBytes(note.get(i) + "\n");
                        }
                        out.writeBytes("@" + "\n");
                        break;
                    case "x":
                        if(note.remove(in.readLine())){
                            out.writeBytes("*" + "\n");
                        }else{
                            out.writeBytes("0" + "\n");
                        }
                        break;
                    case "!":
                        out.writeBytes("Disconnessione effettuata");
                        break;
                }
            } while (!scelta.equals("4"));
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
