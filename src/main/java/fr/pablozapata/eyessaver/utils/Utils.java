package fr.pablozapata.eyessaver.utils;

import fr.pablozapata.eyessaver.Main;
import lombok.var;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Utils {
    public static Utils instance;

    public static Utils getInstance() {
        if(instance == null){
            instance = new Utils();
        }
        return instance;
    }

    public int getInterval() {
        try (var is = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(is);
            return Integer.parseInt(prop.getProperty("interval"));
        } catch (IOException ex) {
            return 100;
        }
    }

    public void setInterval(int interval) throws IOException {
        Properties prop = new Properties();
        InputStream in = Main.class.getClassLoader().getResourceAsStream("config.properties");
        prop.load(in);
        prop.setProperty("interval", String.valueOf(interval));
        OutputStream out = new FileOutputStream(Main.class.getClassLoader().getResource("config.properties").getPath());
        prop.store(out, null);
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
