package com.spotify.mace;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.log4j.Logger;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class JobsHandler extends AbstractHandler
{
    private static Gson json = new GsonBuilder().create();
    private static Logger log = Logger.getLogger(JobsHandler.class);

    @Override
    public void handle(String target, Request baseRequest,
        HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("application/json;");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        log.info(request);
        response.getWriter().println("{'status':'OK'}");
    }
}
