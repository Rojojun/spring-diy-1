package com.diy.framework.servlet;

import com.diy.app.LectureController;
import com.diy.framework.controller.Controller;
import com.diy.framework.value.ModelAndView;
import com.diy.framework.view.View;
import com.diy.framework.view.resolver.JspViewResolver;
import com.diy.framework.view.resolver.ViewResolver;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    private final Map<String, Controller> controllerMapping;

    // 위임된 main에서 전략으로 설정 한다면?
    private final ViewResolver viewResolver =  new JspViewResolver();

    public DispatcherServlet() {
        this.controllerMapping = Map.of("/lectures", new LectureController());
    }

//    public DispatcherServlet(Map<String, Controller> controllerMapping) {
//        this.controllerMapping = controllerMapping;
//    }

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final Map<String, ?> params = parseParams(req);
        final Controller controller = controllerMapping.get(req.getRequestURI());

        try {
            req.setAttribute("params", params);
            final ModelAndView modelAndView = controller.handleRequest(req, resp);
            render(modelAndView, req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, ?> parseParams(final HttpServletRequest request) throws IOException {
        if ("application/json".equals(request.getHeader("Content-Type"))) {
            final byte[] bodyBytes = request.getInputStream().readAllBytes();
            final String body = new String(bodyBytes, StandardCharsets.UTF_8);

            return new ObjectMapper().readValue(body, new TypeReference<>() {});
        } else {
            return request.getParameterMap();
        }
    }

    private void render(final ModelAndView modelAndView, final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
        final String viewName = modelAndView.viewName();
        final View view = Optional.ofNullable(viewResolver.resolveViewName(viewName))
                .orElseThrow(() -> new RuntimeException("View not found: " + viewName));
        view.render(modelAndView.model(), req, resp);
    }
}
