/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jtimecounter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author brian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String fileName;
        if(args.length > 0){
            fileName = args[0];
        }else{
            fileName = "arq1.txt";
        }

        String line;
        String horario = "";
        long horarioLong = 0;
        long anterior = 0;
        long total = 0;
        HashMap<String, Long> tempoTarefas = new HashMap<String, Long>();

        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");

            while ((line = in.readLine()) != null) {
                if (line.startsWith("-") || line.startsWith("#")) {
                    horario = line.substring(1, 4) + ":" + line.substring(4, 6);


                    if (line.startsWith("-")) {
                        //System.out.print(horario + " - ");
                        //System.out.println(df.parse(horario).getTime());
                        horarioLong = df.parse(horario).getTime();


                        int fimString = line.indexOf('%');
                        if(fimString == -1){
                            fimString = line.length();
                        }

                        String key = line.substring(7, fimString);
                        long tempoTarefa = tempoTarefas.get(key) != null ? tempoTarefas.get(key) : 0;
                        tempoTarefas.put(key, horarioLong - anterior + tempoTarefa);
                        System.out.println(" - " + key + "->" + (horarioLong - anterior)/1000/60);
                        anterior = horarioLong;

                    } else if (line.startsWith("#")) {
                        anterior = df.parse(horario).getTime();
                    }


                    //total += horarioLong - anterior;



                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("==========================");

        int somateste = 0;

        Iterator it = tempoTarefas.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            Long val = (Long) tempoTarefas.get(key);

            System.out.println(key.toString() + " - " + val / 1000 / 60);
            somateste += val / 1000 / 60;

        }

        System.out.println("========================== total: " + somateste + "minutos");
        //System.out.println("\nTotal: " + total / 1000 / 60);

    }
}
