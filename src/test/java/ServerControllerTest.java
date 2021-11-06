import exceptions.WrongPortException;
import exceptions.WrongStatusException;
import exceptions.WrongWebPathException;
import org.junit.Test;
import server.ServerController;
import server.WebServer;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServerControllerTest {
    private int port = 44055;
    private String webFilePath = "src/main/java/website";
    private ArrayList<String> status = new ArrayList<String>(Arrays.asList("Stopped", "Running", "Maintenance"));


    @Test
    public void testNewServerSocketOk() throws WrongWebPathException, WrongPortException, WrongStatusException {
        WebServer webServer = new WebServer(port, webFilePath, status.get(0));
        webServer.setServerStatus(status.get(1));
        try {
            ServerSocket socket = ServerController.newServerSocket(port);
            assertTrue(socket.isBound());
            socket.close();
        } catch (BindException bindException) {
            bindException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestServerSocketPortIsntOk() throws WrongPortException, WrongStatusException, WrongWebPathException {
        WebServer webServer = new WebServer(port, webFilePath, status.get(0));
        webServer.setServerStatus(status.get(1));

        try {
            ServerSocket socket = ServerController.newServerSocket(65540);
        } catch (BindException bindException) {

        }

    }

    @Test(expected = BindException.class)
    public void TestPortNotAvailabe() throws IOException {
        ServerSocket s1 = ServerController.newServerSocket(port);
        ServerSocket s2 = ServerController.newServerSocket(port);
        assertTrue(s1.isClosed());
    }

    @Test(expected = NullPointerException.class)
    public void closeServerNotWorking() throws IOException, WrongPortException, WrongStatusException, WrongWebPathException {
        ServerController.closeServerSocket(null);

    }

    @Test
    public void CloseServerWorking() {
        try {
            ServerSocket s = ServerController.newServerSocket(port);
            ServerController.closeServerSocket(s);
            assertTrue(s.isClosed());
        } catch (BindException bindException) {
            bindException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Test
    public void testNewCSOK() throws Exception {
        try {
            ServerSocket ss = ServerController.newServerSocket(port);
            Socket cs = ServerController.newSocket(ss);
            assertTrue(cs.isBound());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testNewCSNotOk() {
        try {
            Socket cs = ServerController.newSocket(null);
            assertTrue(cs.isClosed());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testCloseCSIsWorking() throws IOException {
        try {
            ServerSocket s = ServerController.newServerSocket(port);
            Socket cs = ServerController.newSocket(s);
            ServerController.closeSocket(cs);
            assertTrue(cs.isClosed());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test(expected = NullPointerException.class)
    public void TestCloseCSIsNotWorking() throws IOException {
        try {
            ServerController.closeSocket(null);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }


    @Test
    public void reqHandlerServerStopped() throws WrongPortException, WrongStatusException, WrongWebPathException {
        WebServer webServer = new WebServer(port, webFilePath, status.get(0));
        ServerController serverController = new ServerController(webServer);
        serverController.reqHandle();
    }

    @Test
    public void reqHandlerServerRuns() throws WrongPortException, WrongStatusException, WrongWebPathException {
        try {
            WebServer webServer = new WebServer(port, webFilePath, status.get(0));
            webServer.setServerStatus(status.get(1));
            ServerController serverController = new ServerController(webServer);
            serverController.reqHandle();
        } catch (WrongPortException wrongPortException) {
            wrongPortException.printStackTrace();
        } catch (WrongStatusException wrongStatusException) {
            wrongStatusException.printStackTrace();
        } catch (WrongWebPathException wrongWebPathException) {
            wrongWebPathException.printStackTrace();
        }
    }

    @Test
    public void reqHandlerInMaintenance() throws WrongPortException, WrongStatusException, WrongWebPathException {

        WebServer webServer = new WebServer(port, webFilePath, status.get(0));
        webServer.setServerStatus(status.get(2));
        ServerController serverController = new ServerController(webServer);
        serverController.reqHandle();
    }

    @Test
    public void AcceptWorking() {
        try {
            ServerSocket serverSocket = ServerController.newServerSocket(port);
            Socket socket = ServerController.newSocket(serverSocket);
            assertTrue(serverSocket.isBound());
            assertTrue(socket.isBound());
            serverSocket.close();
            socket.close();
        } catch (BindException bindException) {
            bindException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test(expected = NullPointerException.class)
    public void AcceptNotWorking() throws Exception {
        ServerSocket serverSocket = ServerController.newServerSocket(port);
        Socket socket = ServerController.newSocket(null);
        assertTrue(!socket.isBound());
    }

    @Test
    public void clientHandlerNotWorking() throws WrongPortException, WrongStatusException, WrongWebPathException {
        WebServer webServer = new WebServer(port, webFilePath, status.get(0));
        ServerController serverController = new ServerController(webServer);
        serverController.clientHandle(null);
    }

    @Test
    public void clientHandlerWorking() throws Exception {
        WebServer webServer = new WebServer(port, webFilePath, status.get(0));
        ServerController serverController = new ServerController(webServer);
        ServerSocket serverSocket = ServerController.newServerSocket(port);
        Socket socket = ServerController.newSocket(serverSocket);
        serverController.clientHandle(socket);

    }
}

