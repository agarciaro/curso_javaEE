package com.cursojavaee.cursojavaee.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@WebFilter(urlPatterns = {"/*"})
public class HeaderLogFilter implements Filter {
    @Override
    public void init (FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter (ServletRequest request, ServletResponse response,
                          FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;

        System.out.println("----- Request ---------");
        Collections.list(req.getHeaderNames())
                .forEach(n -> System.out.println(n + ": " + req.getHeader(n)));

        chain.doFilter(request, response);

        System.out.println("----- Response ---------");

        rep.getHeaderNames()
                .forEach(n -> System.out.println(n + ": " + rep.getHeader(n)));

        System.out.println("Response Status: " + rep.getStatus());
    }

    @Override
    public void destroy () {
    }
}
