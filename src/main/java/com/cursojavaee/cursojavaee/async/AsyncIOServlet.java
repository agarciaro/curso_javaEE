package com.cursojavaee.cursojavaee.async;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/AsyncIOServlet", asyncSupported = true)
public class AsyncIOServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final AsyncContext asyncContext = request.startAsync();
        final ServletInputStream input = request.getInputStream();

        input.setReadListener(new ReadListener() {
            byte buffer[] = new byte[4*1024];
            StringBuilder sbuilder = new StringBuilder();
            @Override
            public void onDataAvailable() {
                try {
                    do {
                        int length = input.read(buffer);
                        sbuilder.append(new String(buffer, 0, length));
                    } while(input.isReady());
                } catch (IOException ex) { ex.printStackTrace(); }
            }
            @Override
            public void onAllDataRead() {
                try {
                    asyncContext.getResponse().getWriter()
                            .write("Respuesta recibida con éxito");
                } catch (IOException ex) { ex.printStackTrace(); }
                asyncContext.complete();
            }
            @Override
            public void onError(Throwable t) {
                System.out.println("Error al leer la Request: " + t.getMessage());
            }
        });
    }
}
