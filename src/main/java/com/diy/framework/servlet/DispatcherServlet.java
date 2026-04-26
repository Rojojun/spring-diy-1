package com.diy.framework.servlet;

import com.diy.framework.controller.Controller;
import com.diy.framework.value.ModelAndView;
import com.diy.framework.view.View;
import com.diy.framework.view.resolver.JspViewResolver;
import com.diy.framework.view.resolver.ViewResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class DispatcherServlet extends HttpServlet {
    private final Map<String, Controller> controllerMapping;

    private final ViewResolver viewResolver = new JspViewResolver();

    public DispatcherServlet(Map<String, Controller> controllerMapping) {
        this.controllerMapping = controllerMapping;
    }

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        Controller controller = controllerMapping.get(req.getRequestURI());

        try {
            final ModelAndView modelAndView = controller.handleRequest(req, resp);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void render(final ModelAndView modelAndView, final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
        final String viewName = modelAndView.viewName();
        final View view = Optional.ofNullable(viewResolver.resolveViewName(viewName))
                .orElseThrow(() -> new RuntimeException("View not found: " + viewName));
        view.render(modelAndView.model(), req, resp);
    }
}
