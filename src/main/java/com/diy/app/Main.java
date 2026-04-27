package com.diy.app;

import com.diy.framework.controller.Controller;
import com.diy.framework.servlet.DispatcherServlet;
import com.diy.framework.web.server.TomcatWebServer;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, Controller> controllers = Map.of("/lectures", new LectureController());
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        // 위임해서 main에서 조립하는방향은?
        final TomcatWebServer tomcatWebServer = new TomcatWebServer();
        tomcatWebServer.start();
    }
}
