package org.example;

import com.sun.net.httpserver.HttpServer;
import org.example.annotation.EndPoint;
import org.example.controller.SimulationController;
import org.example.service.LoggingService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.InetSocketAddress;

public class Main {
    private static final int SERVER_PORT = 8080;
    private static final String PATH_SIM = "/simulation/";
    private static final SimulationController simulationController = new SimulationController();

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
            setSimulationControllerEndpointContexts(server);
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            LoggingService.logInfo("Server could not be started. Exiting...:" + e.getMessage(), Main.class);
        }
    }

    private static void setSimulationControllerEndpointContexts(HttpServer server) {
        Method[] controllerMethods = simulationController.getClass().getDeclaredMethods();
        for (Method method : controllerMethods) {
            if (isMethodPublic(method)) {
                createEndPointContext(server, method);
            }
        }
    }

    private static boolean isMethodPublic(Method method) {
        return Modifier.isPublic(method.getModifiers());
    }

    private static void createEndPointContext(HttpServer server, Method method) {
        String endPointUrl = createEndPointUrl(method);
        server.createContext(endPointUrl, exchange -> {
            try {
                method.invoke(simulationController, exchange);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static String createEndPointUrl(Method method) {
        String endPointUrl = PATH_SIM + method.getName();
        EndPoint endPointAnnotation = method.getAnnotation(EndPoint.class);
        if (endPointAnnotation != null) {
            String endPointAnnotationValue = endPointAnnotation.value();
            endPointUrl = PATH_SIM + endPointAnnotationValue;
        }
        return endPointUrl;
    }
}