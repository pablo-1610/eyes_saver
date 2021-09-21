package fr.pablozapata.eyessaver.tasks;

import fr.pablozapata.eyessaver.Main;
import fr.pablozapata.eyessaver.utils.SuperTask;

import java.awt.*;

public class Reminder extends SuperTask {
    private static int currentMin = 0;
    @Override
    public void run() {
        if (Main.getActive()) {
            Main.setTimeMin(Main.getTimeMin() + 1);
            if (Main.getTimeMin() >= 60) {
                Main.setTimeMin(0);
                Main.setTimeHour(Main.getTimeHour() + 1);
            }
            currentMin = currentMin + 1;
            if (currentMin >= Main.getInterval()) {
                currentMin = 0;
                Main.getTrayIcon().displayMessage(Main.getName(), "Il est l'heure de faire une pause !", TrayIcon.MessageType.NONE);
            }
        }
        Main.getTrayIcon().setToolTip("Eyes Saver\n——————————\nStatut: " + (Main.getActive() ? "Activé\nInterval: " + Main.getInterval() + "min\nProchaine alerte: " + (Main.getInterval()-currentMin) + "min\nActif depuis: " + Main.getTimeMin() + "min " + Main.getTimeHour() + "h" : "Désactivé") + "\n——————————\nBy Pablo Z");
    }
}
