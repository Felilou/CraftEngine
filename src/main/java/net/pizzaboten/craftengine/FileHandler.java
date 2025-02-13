package net.pizzaboten.craftengine;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHandler {

    private final File dataFile = new File("./commands.txt");

    //writes the given text to the file without overwriting the file
    public void saveCommand(String text) {
        try {
            if(Arrays.asList(getCommands()).contains(text)){
                return;
            }
            FileWriter fw = new FileWriter(dataFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //reads the file and returns the text row by row using a array list
    public String[] getCommands() {
        List<String> lines = new ArrayList<>();
        try {
            FileReader fr = new FileReader(dataFile);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines.toArray(new String[0]);
    }

    public void deleteCommand(String text) {
        String[] commands = getCommands();
        try {
            FileWriter fw = new FileWriter(dataFile);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String command : commands) {
                if (!command.equals(text)) {
                    bw.write(command);
                    bw.newLine();
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}