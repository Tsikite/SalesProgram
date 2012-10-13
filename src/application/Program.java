package application;

import application.common.exceptions.DataManagerInitExcption;
import application.managers.data.DataManager;
import application.managers.data.SalesManager;
import application.managers.log.LoggerManager;
import application.ui.MainFrame;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;

public class Program {

    static DataManager dataManager;

    public static void main(String[] args) {

        try {


            DataManager.Init();

            logToProgram(Level.INFO, "Application was created.");

        } catch (DataManagerInitExcption e) {
            e.printStackTrace();
        }

        // Creating sales

         //Part one
//         SalesManager.start(true);
//            
//         DataManager.allUsers.get(0).start();
//         DataManager.allUsers.get(1).start();
//         DataManager.allUsers.get(2).start();
//         DataManager.allUsers.get(3).start();
         

        //Part two
        SalesManager.start(false);

        MainFrame mainFrame = new MainFrame();
        
        
        
    }

    public static void logToProgram(Level level, String message) {
        LoggerManager.getInstance().getProgramLogger().log(level, "Program:" + message);
    }

    public static String getCurrentTime() {
        return (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());
    }
}
