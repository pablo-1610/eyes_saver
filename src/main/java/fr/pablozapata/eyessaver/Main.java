package fr.pablozapata.eyessaver;

import fr.pablozapata.eyessaver.tasks.Reminder;
import fr.pablozapata.eyessaver.utils.Utils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.var;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {
    @Getter
    public final static String name = "Eyes Saver";
    @Getter
    public static URL logoUrl;
    @Getter
    private static TrayIcon trayIcon;
    @Getter
    private static SystemTray tray;
    @Getter
    @Setter
    private static Boolean active = true;
    @Getter
    @Setter
    private static int timeMin = 0;
    @Getter
    @Setter
    private static int timeHour = 0;
    @Getter
    @Setter
    private static int interval;


    public static void main(String[] args) throws AWTException, IOException {
        interval = Utils.getInstance().getInterval();
        logoUrl = new URL("https://upload.wikimedia.org/wikipedia/commons/b/b4/Blue_eye_icon.png");
        tray = SystemTray.getSystemTray();
        Main.setupTrayIcon();
        final Reminder reminder = new Reminder();
        reminder.scheduleAsyncRepeatingTask(0, 1, TimeUnit.MINUTES);
    }

    public static void setupTrayIcon() throws MalformedURLException, AWTException {
        SystemTray tray = Main.getTray();
        Image image = Toolkit.getDefaultToolkit().createImage(Main.getLogoUrl());
        PopupMenu popup = new PopupMenu();
        popup.add("Activer / Désactiver");
        popup.add("Définir l'intervalle");
        popup.add("Quitter");
        popup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equalsIgnoreCase("Activer / Désactiver")) {
                    Main.changeStatut();
                } else if (e.getActionCommand().equalsIgnoreCase("Définir l'intervalle")) {
                    try {
                        Main.setInterval();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (e.getActionCommand().equalsIgnoreCase("Quitter")) {
                    System.exit(0);
                }
            }
        });

        TrayIcon trayIcon = new TrayIcon(image, "Eyes Saver", popup);
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Eyes Saver\n——————————\nInitialisation...\n——————————\nBy Pablo Z.");
        tray.add(trayIcon);
        Main.trayIcon = trayIcon;
    }

    private static void setInterval() throws IOException {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(Main.getName());
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage(Main.getLogoUrl()));
        String min = JOptionPane.showInputDialog("Nouvel interval (en minutes)");
        if(!Utils.getInstance().isNumeric(min)) {
            JOptionPane.showMessageDialog(frame, "La valeur entrée n'est pas un chiffre", Main.getName(), 0);
            return;
        }
        int minutes = Integer.parseInt(min);
        if(minutes <= 0) {
            JOptionPane.showMessageDialog(frame, "Vous ne pouvez pas mettre une valeur inférieure ou égale à 0", Main.getName(), 0);
            return;
        }
        JOptionPane.showMessageDialog(frame, "Interval défini à " + min + " minute" + (minutes > 1 ? "s" : ""), Main.getName(), 3);
        Utils.getInstance().setInterval(minutes);
        Main.setInterval(minutes);
    }

    private static void changeStatut() {
        Main.setActive(!Main.getActive());
        Main.getTrayIcon().displayMessage(name, name + (Main.getActive() ? " est désormais activé" : " est désormais désactivé"), Main.getActive() ? TrayIcon.MessageType.INFO : TrayIcon.MessageType.WARNING);
    }

}
