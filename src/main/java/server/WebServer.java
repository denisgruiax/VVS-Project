package server;

import exceptions.WrongPortException;
import exceptions.WrongStatusException;
import exceptions.WrongWebPathException;

import java.util.ArrayList;

public class WebServer {
    private int port;
    private String webPath;
    private String serverStatus;
    private String request;
    public ArrayList<ArrayList<String>> pages = new ArrayList<>(10);

    public WebServer() {

    }

    public WebServer(int port, String webPath, String serverStatus) throws WrongPortException, WrongStatusException, WrongWebPathException {
        validateWebServerInputs(port, webPath, serverStatus);
        this.port = port;
        this.webPath = webPath;
        this.serverStatus = serverStatus;
        this.request = "";
        pages = new ArrayList<ArrayList<String>>();
    }

    public int getPort() {
        return port;

    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getWebPath() {
        return webPath;
    }

    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }

    public String getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void validateWebServerInputs(int port, String webPath, String serverStatus) throws WrongPortException, WrongStatusException, WrongWebPathException {
        if (port != 44055)
            throw new WrongPortException();
        if (!serverStatus.equals("Stopped"))
            throw new WrongStatusException();
        if (!webPath.equals("src/main/java/website")) {
            throw new WrongWebPathException();
        }
    }

    public void addL(ArrayList<String> L) {
        pages.add(L);
    }

    public void addPageOnL(String page, int i) {
        pages.get(i).add(page);
    }
}
