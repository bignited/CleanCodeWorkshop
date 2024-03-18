package org.example.config;

import com.sun.net.httpserver.HttpServer;
import org.example.annotation.EndPoint;
import org.example.controller.SimulationController;
import org.example.service.LoggingService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.InetSocketAddress;

public class AppConfig {
    private static final int SERVER_PORT = 8080;
    private static final String PATH_SIM = "/simulation/";
    private static HttpServer httpServer;

    public static void startApplication() {
        setUpServer();
        setSimulationControllerEndpointContexts();
        httpServer.setExecutor(null);
        httpServer.start();
    }

    private static void setUpServer() {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
        } catch (IOException e) {
            LoggingService.logInfo("Server could not be created.:" + e.getMessage(), AppConfig.class);
        }
    }

    private static void setSimulationControllerEndpointContexts() {
        Method[] controllerMethods = SimulationController.class.getDeclaredMethods();
        for (Method method : controllerMethods) {
            if (isMethodPublic(method)) {
                createEndPointContext(method);
            }
        }
    }

    private static boolean isMethodPublic(Method method) {
        return Modifier.isPublic(method.getModifiers());
    }

    private static void createEndPointContext(Method method) {
        String endPointUrl = createEndPointUrl(method);
        httpServer.createContext(endPointUrl, exchange -> {
            try {
                method.invoke(new SimulationController(), exchange);
            } catch (IllegalAccessException e) {
                LoggingService.logInfo("Cannot reach method for endpoint creation. " +
                        "Make sure the method is public.:" + e.getMessage(), AppConfig.class);
            } catch (InvocationTargetException e) {
                LoggingService.logInfo("Cannot invoke specified method: " +
                        method.getName() + " : " + e.getMessage(), AppConfig.class);
            }
        });
    }

    private static String createEndPointUrl(Method method) {
        String endPointUrl = PATH_SIM + method.getName();
        EndPoint endPointAnnotation = method.getAnnotation(EndPoint.class);
        if (endPointAnnotation != null) {
            endPointUrl = createAnnotationEndPointUrl(endPointAnnotation);
        }
        return endPointUrl;
    }

    private static String createAnnotationEndPointUrl(EndPoint endPointAnnotation) {
        String endPointAnnotationValue = endPointAnnotation.value();
        return PATH_SIM + endPointAnnotationValue;
    }
}
