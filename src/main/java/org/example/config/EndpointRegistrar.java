package org.example.config;

import com.sun.net.httpserver.HttpServer;
import org.example.annotation.EndPoint;
import org.example.controller.SimulationController;
import org.example.service.LoggingService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Class for registering endpoints on an HTTP server. This class provides methods to register endpoints on an
 * HTTP server. The endpoints are registered based on the methods in the SimulationController class. The class uses
 * reflection to create endpoint contexts for the methods in the SimulationController class. The endpoint contexts
 * are created based on the method names. If a method has an EndPoint annotation, the value of the annotation is
 * used as the endpoint URL. Otherwise, the endpoint URL is created based on the method name. The class also checks
 * if the method is public before creating the endpoint context.
 * If the method is not public, the endpoint context is not created.
 */
public class EndpointRegistrar {
    private static final String PATH_SIM = "/simulation/";

    /**
     * Registers endpoints on the provided HTTP server.
     *
     * @param httpServer The HTTP server to register endpoints on.
     */
    public static void registerEndpoints(HttpServer httpServer) {
        setSimulationControllerEndpointContexts(httpServer);
    }

    /**
     * Sets endpoint contexts for methods in the SimulationController class.
     *
     * @param httpServer The HTTP server to set endpoint contexts on.
     */
    private static void setSimulationControllerEndpointContexts(HttpServer httpServer) {
        Method[] controllerMethods = SimulationController.class.getDeclaredMethods();
        for (Method method : controllerMethods) {
            if (isMethodPublic(method)) {
                createEndPointContext(method, httpServer);
            }
        }
    }

    /**
     * Checks if the given method is public.
     *
     * @param method The method to check.
     * @return true if the method is public, false otherwise.
     */
    private static boolean isMethodPublic(Method method) {
        return Modifier.isPublic(method.getModifiers());
    }

    /**
     * Creates an endpoint context for the given method on the HTTP server.
     *
     * @param method     The method representing the endpoint.
     * @param httpServer The HTTP server to create the endpoint context on.
     */
    private static void createEndPointContext(Method method, HttpServer httpServer) {
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

    /**
     * Creates the endpoint URL for the given method.
     *
     * @param method The method to create the endpoint URL for.
     * @return The endpoint URL for the given method.
     */
    private static String createEndPointUrl(Method method) {
        String endPointUrl = PATH_SIM + method.getName();
        EndPoint endPointAnnotation = method.getAnnotation(EndPoint.class);
        if (endPointAnnotation != null) {
            endPointUrl = createAnnotationEndPointUrl(endPointAnnotation);
        }
        return endPointUrl;
    }

    /**
     * Creates an endpoint URL based on the provided EndPoint annotation.
     *
     * @param endPointAnnotation The EndPoint annotation.
     * @return The URL of the endpoint.
     */
    private static String createAnnotationEndPointUrl(EndPoint endPointAnnotation) {
        String endPointAnnotationValue = endPointAnnotation.value();
        return PATH_SIM + endPointAnnotationValue;
    }
}
