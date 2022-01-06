import java.util.ArrayList;

import exceptions.*;
import server.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    public static WebServer webServer;
    public static ServerController serverController;

    JPanel panel = new JPanel();

    JButton startButton;
    JButton maintenanceButton;
    JButton stopButton;

    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();
    JLabel label3 = new JLabel();

    public Main() {
        super();

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(0, 1));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel);
        this.setSize(500, 500);
        this.setResizable(true);

        this.label1.setText("44055");
        this.label2.setText("src/main/java/website");
        this.label3.setText("Server status: " + webServer.getServerStatus());

        startButton = new JButton("Start Server");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webServer.setServerStatus("Running");
                label3.setText("Server status: Running");
            }
        });

        stopButton = new JButton("Stop server");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webServer.setServerStatus("Stopped");
                label3.setText("Server status: Stopped");
            }
        });

        maintenanceButton = new JButton("Maintenance server");
        maintenanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webServer.setServerStatus("Maintenance");
                label3.setText("Server status: Maintenance");
            }
        });


        panel.add(startButton);
        panel.add(maintenanceButton);
        panel.add(stopButton);
        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
    }

    public static void main(String[] args) throws WrongPortException, WrongStatusException, WrongWebPathException {
        int port = 44055;
        String webPath = "src/main/java/website";
        String serverStatus = "Stopped";

        webServer = new WebServer(port, webPath, serverStatus);

        ArrayList<String> L = new ArrayList<String>(10);

        webServer.addL(L);
        webServer.addPageOnL("Page1.html", 0);
        webServer.addPageOnL("Page2.html", 0);
        webServer.addPageOnL("Page3.html", 0);

        serverController = new ServerController(webServer);
        webServer.setServerStatus("Running");

        JFrame frame = new Main();
        frame.setVisible(true);

        while (true) {
            serverController.reqHandle();
        }
    }
}
