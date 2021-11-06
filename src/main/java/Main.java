import java.util.ArrayList;

import exceptions.*;
import server.*;

public class Main {

    public static void main(String[] args) throws WrongPortException, WrongStatusException, WrongWebPathException {
        int port = 44055;
        String webPath = "src/main/java/website";
        String serverStatus = "Stopped";
        WebServer webServer = new WebServer(port, webPath, serverStatus);

        ArrayList<String> L = new ArrayList<String>();

        webServer.addL(L);
        webServer.addPageOnL("Page1.html", 0);
        webServer.addPageOnL("Page2.html", 0);
        webServer.addPageOnL("Page3.html", 0);

        ServerController serverController = new ServerController(webServer);

        webServer.setServerStatus("Running");

        while (true) {
            serverController.reqHandle();
        }
    }
}
