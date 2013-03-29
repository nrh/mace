package com.spotify.mace;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.Server;

//import com.spotify.mace.storage;

public class MaceServer {
  public static void main(String[] args) throws Exception {
    /* we need a couple of threads here:
     * main thread
     * HTTP Server
     * Storage
     * Task Runner
     */

//    Server server = new Server(8080);
//    server.setHandler(new HelloWord());
//    server.start();
//    server.join();
  }
}

