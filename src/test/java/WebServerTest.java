import exceptions.WrongPortException;
import exceptions.WrongStatusException;
import exceptions.WrongWebPathException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import server.ServerController;
import server.WebServer;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class WebServerTest {
    static WebServer webServer;

    @BeforeClass
    public static void before() throws WrongPortException, WrongStatusException, WrongWebPathException {
        try {
            webServer = new WebServer(44055, "src/main/java/website", "Stopped");

        } catch (WrongPortException wrongPortException) {
            System.out.println("Wrong port");
        } catch (WrongStatusException wrongStatusException) {
            System.out.println("Wrong status");
        } catch (WrongWebPathException wrongWebPathException) {
            System.out.println("Wrong webPath");
        }
    }

    @Test
    public void checkServerNotNull() {
        assertNotNull(webServer);
    }

    @Test
    public void PortOk() {
        assertEquals(webServer.getPort(), 44055);
    }

    @Test(expected = WrongPortException.class)
    public void PortNotOk() throws WrongPortException, WrongStatusException, WrongWebPathException {
        WebServer webServer = new WebServer(10008, "src/main/java/website", "Stopped");
        assertNotSame(webServer.getPort(), 44055);
    }

    @Test
    public void webPathOk() {
        assertEquals(webServer.getWebPath(), "src/main/java/website");
    }

    @Test(expected = WrongWebPathException.class)
    public void webPathNotOk() throws WrongPortException, WrongStatusException, WrongWebPathException {
        WebServer webServer = new WebServer(44055, "src/main/java/server", "Stopped");
        assertNotSame(webServer.getWebPath(), "src/main/java/website");
    }

    @Test
    public void serverStatusOk() {
        assertEquals(webServer.getServerStatus(), "Stopped");
    }

    @Test
    public void getAndSetPortOk() {
        WebServer webServer = new WebServer();
        webServer.setPort(44055);
        assertEquals(44055, webServer.getPort());
    }

    @Test
    public void getAndSetWebPathOk() {
        WebServer webServer = new WebServer();
        webServer.setWebPath("src/main/java/website");
        assertEquals("src/main/java/website", webServer.getWebPath());
    }

    @Test
    public void getAndSetStatusOk() {
        WebServer webServer = new WebServer();
        webServer.setServerStatus("Running");
        assertEquals("Running", webServer.getServerStatus());
    }

    @Test
    public void getAndSetReqOk() {
        WebServer webServer = new WebServer();
        webServer.setRequest("Request");
        assertEquals("Request", webServer.getRequest());
    }

    @Test(expected = WrongStatusException.class)
    public void serverStatusNotOk() throws WrongPortException, WrongStatusException, WrongWebPathException {
        WebServer webServer = new WebServer(44055, "src/main/java/website", "Running");
        assertNotSame(webServer.getServerStatus(), "Stopped");
    }

    @Test
    public void addLvlIsOk() throws WrongPortException, WrongStatusException, WrongWebPathException {
        WebServer webServer = new WebServer(44055, "src/main/java/website", "Stopped");
        assertEquals(0, webServer.pages.size());
        ArrayList<String> test = new ArrayList<String>();
        webServer.addL(test);
        assertEquals(1, webServer.pages.size());

    }

    @Test
    public void addPageOnLvlIsOk() throws WrongPortException, WrongStatusException, WrongWebPathException {
        WebServer webServer = new WebServer(44055, "src/main/java/website", "Stopped");
        ArrayList<String> testlvl = new ArrayList<String>();
        webServer.addL(testlvl);
        assertEquals(0, webServer.pages.get(0).size());
        String test = new String();
        webServer.pages.get(0).add(test);
        assertEquals(1, webServer.pages.get(0).size());
    }
}