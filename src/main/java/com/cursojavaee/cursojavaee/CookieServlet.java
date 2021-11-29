package com.cursojavaee.cursojavaee;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CookieServlet", value = "/CookieServlet")
public class CookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //suponemos que el usuario visita por primera vez nuestro sitio
        boolean nuevoUsuario = true;

        //Obtenemos el arreglo de cookies
        Cookie[] cookies = request.getCookies();

        //buscamos si ya existe una cookie creada con anterioridad
        //llamada visitanteRecurrente
        if(cookies != null){
            for(Cookie c: cookies){
                if(c.getName().equals("visitanteRecurrente") && c.getValue().equals("si")){
                    //si ya existe la cookie es un usuario recurrente
                    nuevoUsuario = false;
                    break;
                }
            }
        }

        String mensaje = null;
        if(nuevoUsuario){
            Cookie visitanteCookie = new Cookie("visitanteRecurrente","si");
            response.addCookie(visitanteCookie);
            mensaje = "Gracias por visitar nuestro sitio por primera vez";
        }
        else{
            mensaje = "Gracias por visitar NUEVAMENTE nuestro sitio";
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("Mensaje:" + mensaje);
        out.close();
    }
}
