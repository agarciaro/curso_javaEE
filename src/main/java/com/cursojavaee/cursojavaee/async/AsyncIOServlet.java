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
        final ServletInputStream inputStream = request.getInputStream();

        inputStream.setReadListener(new ReadListener() {
            byte buffer[] = new byte[4*1024];
            StringBuilder stringBuilder = new StringBuilder();

            @Override
            public void onDataAvailable() throws IOException {
                try{
                    do{
                        int length = inputStream.read(buffer);
                        stringBuilder.append(new String(buffer, 0, length));
                    } while(inputStream.isReady());
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void onAllDataRead() throws IOException {
                try{
                    asyncContext.getResponse().getWriter().write("Respuesta recibida con éxito");
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                finally {
                    asyncContext.complete();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error al leer la Request: " + throwable.getMessage());
            }
        });
    }
}
