package com.cursojavaee.cursojavaee.handler;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@WebServlet(urlPatterns = "/errorHandler")
public class ErrorHandlerServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html; charset=utf-8");
        try (PrintWriter writer = resp.getWriter()) {
            writer.write("<html><head><title>Error description</title></head><body>");
            writer.write("<h2>Error description</h2>");
            writer.write("<ul>");
            Arrays.asList(
                            RequestDispatcher.ERROR_STATUS_CODE,
                            RequestDispatcher.ERROR_EXCEPTION_TYPE,
                            RequestDispatcher.ERROR_MESSAGE)
                    .forEach(e ->
                            writer.write("<li>" + e + ":" + req.getAttribute(e) + " </li>")
                    );
            writer.write("</ul>");
            writer.write("</html></body>");
        }
    }
}
