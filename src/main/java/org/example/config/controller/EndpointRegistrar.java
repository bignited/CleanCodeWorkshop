package org.example.config.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.annotation.API;
import org.example.annotation.EndPoint;
import org.example.config.AppConfig;
import org.example.controller.IController;
import org.example.model.controller.HttpMethod;
import org.example.model.controller.HttpStatusCode;
import org.example.service.LoggingService;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for registering endpoints on an HTTP server.
 * This class provides methods to register endpoints on an HTTP server.
 * Endpoints are registered based on the methods annotated with {@link EndPoint} in classes annotated with {@link API}.
 * The class uses reflection to create endpoint contexts for the annotated methods.
 * Endpoint URLs are determined based on the method names, with the option to override them using the {@link EndPoint} annotation.
 * Endpoint contexts are created only for public methods.
 */
public class EndpointRegistrar {
    private static IController controllerToRegister;

    /**
     * Registers endpoints on the provided HTTP server.
     *
     * @param httpServer The HTTP server to register endpoints on.
     */
    public static void registerEndpoints(HttpServer httpServer) {
        Set<Class<?>> classes = scanAllClasses();
        createEndPointContextsPerClass(classes, httpServer);
    }

    /**
     * Scans all classes in the package and sub-packages to find those annotated with {@link API}.
     * For each class found, creates endpoint contexts for the methods annotated with {@link EndPoint}.
     *
     * @return Set of classes annotated with {@link API}.
     */
    private static Set<Class<?>> scanAllClasses() {
        String packageName = "org.example";
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return new HashSet<>(reflections.getSubTypesOf(Object.class));
    }

    /**
     * Creates endpoint contexts for the methods in the provided classes.
     *
     * @param classes    Set of classes to scan for endpoint methods.
     * @param httpServer The HTTP server to set endpoint contexts on.
     */
    private static void createEndPointContextsPerClass(Set<Class<?>> classes, HttpServer httpServer) {
        for (Class<?> classObject : classes) {
            API apiAnnotation = classObject.getAnnotation(API.class);
            ifApiAnnotationIsNotNullCreateEndpointsOfControllerClass(apiAnnotation, classObject, httpServer);
        }
    }

    /**
     * If the {@link API} annotation is not null, creates endpoint contexts for the methods in the provided class.
     *
     * @param apiAnnotation The API annotation.
     * @param classObject   The class to scan for
     * @param httpServer    The HTTP server to set endpoint contexts on.
     */
    private static void ifApiAnnotationIsNotNullCreateEndpointsOfControllerClass(API apiAnnotation,
                                                                                 Class<?> classObject, HttpServer httpServer) {
        if (apiAnnotation != null) {
            initializeConstructor(classObject);
            String controllerEndPointUrl = apiAnnotation.endpoint();
            createEndPointContextsOfMethods(httpServer, classObject, controllerEndPointUrl);
        }
    }

    /**
     * Creates endpoint contexts for the methods in the provided class.
     *
     * @param classObject           The class to scan for endpoint methods.
     * @param httpServer            The HTTP server to set endpoint contexts on.
     * @param controllerEndPointUrl The base endpoint URL for the controller class.
     */
    private static void createEndPointContextsOfMethods(HttpServer httpServer, Class<?> classObject,
                                                        String controllerEndPointUrl) {
        Method[] controllerMethods = classObject.getDeclaredMethods();
        for (Method method : controllerMethods) {
            if (isMethodPublic(method)) {
                createEndPointContext(method, controllerEndPointUrl, httpServer);
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
     * @param method                The method representing the endpoint.
     * @param controllerEndPointUrl The base endpoint URL for the controller class.
     * @param httpServer            The HTTP server to create the endpoint context on.
     */
    private static void createEndPointContext(Method method, String controllerEndPointUrl, HttpServer httpServer) {
        String endPointUrl = createEndPointUrl(method, controllerEndPointUrl);
        try {
            httpServer.createContext(endPointUrl, createHttpHandler((HttpHandler) method.invoke(controllerToRegister),
                    method.getAnnotation(EndPoint.class).method()));
        } catch (IllegalAccessException e) {
            LoggingService.logInfo("Cannot access method: " + method.getName() + " : " + e.getMessage(),
                    AppConfig.class);
        } catch (InvocationTargetException e) {
            LoggingService.logInfo("Cannot invoke method: " + method.getName() + " : " + e.getMessage(),
                    AppConfig.class);
        }
    }

    /**
     * Creates an HTTP handler for the given request handler and HTTP method.
     *
     * @param requestHandler The request handler to create the HTTP handler for.
     * @param httpMethod     The HTTP method for the handler.
     * @return The HTTP handler for the given request handler and HTTP method.
     */
    private static HttpHandler createHttpHandler(HttpHandler requestHandler, HttpMethod httpMethod) {
        return exchange -> {
            if (requestMethodMatches(exchange, httpMethod)) {
                requestHandler.handle(exchange);
            } else {
                HttpStatusCode httpStatusCode = HttpStatusCode.NOT_ALLOWED;
                EndpointResponseBuilder.buildGetResponse(exchange, httpStatusCode.statusCodeMessage, httpStatusCode.statusCode);
                exchange.close();
            }
        };
    }

    /**
     * Checks if the request method matches the expected HTTP method.
     *
     * @param exchange           The HTTP exchange.
     * @param expectedHttpMethod The expected HTTP method.
     * @return true if the request method matches the expected HTTP method, false otherwise.
     */
    private static boolean requestMethodMatches(HttpExchange exchange, HttpMethod expectedHttpMethod) {
        return exchange.getRequestMethod().equalsIgnoreCase(expectedHttpMethod.toString());
    }

    /**
     * Initializes the constructor of the given class.
     *
     * @param classToInitialize The class to initialize the constructor for.
     */
    private static void initializeConstructor(Class<?> classToInitialize) {
        try {
            controllerToRegister = (IController) classToInitialize.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            LoggingService.logInfo("Cannot instantiate class: " + classToInitialize.getName() +
                    " : " + e.getMessage(), AppConfig.class);
        } catch (InvocationTargetException e) {
            LoggingService.logInfo("Cannot invoke constructor of class: " + classToInitialize.getName() +
                    " : " + e.getMessage(), AppConfig.class);
        } catch (IllegalAccessException e) {
            LoggingService.logInfo("Cannot access constructor of class: " + classToInitialize.getName() +
                    " : " + e.getMessage(), AppConfig.class);
        } catch (NoSuchMethodException e) {
            LoggingService.logInfo("No constructor found for class: " + classToInitialize.getName() +
                    " : " + e.getMessage(), AppConfig.class);
        }
    }

    /**
     * Creates the endpoint URL for the given method.
     *
     * @param method                The method to create the endpoint URL for.
     * @param controllerEndPointUrl The base endpoint URL for the controller class.
     * @return The endpoint URL for the given method.
     */
    private static String createEndPointUrl(Method method, String controllerEndPointUrl) {
        String endPointUrl = controllerEndPointUrl + method.getName();
        EndPoint endPointAnnotation = method.getAnnotation(EndPoint.class);
        return ifEndPointAnnotationIsNotNullCreateEndPointUrl(endPointAnnotation, endPointUrl, controllerEndPointUrl);
    }

    /**
     * If the {@link EndPoint} annotation is not null, creates an endpoint URL based on the annotation value.
     *
     * @param endPointAnnotation    The EndPoint annotation.
     * @param endPointUrl           The URL of the endpoint.
     * @param controllerEndPointUrl The base endpoint URL for the controller class.
     * @return The URL of the endpoint.
     */
    private static String ifEndPointAnnotationIsNotNullCreateEndPointUrl(EndPoint endPointAnnotation, String endPointUrl,
                                                                         String controllerEndPointUrl) {
        if (endPointAnnotation != null) {
            endPointUrl = createAnnotationEndPointUrl(controllerEndPointUrl, endPointAnnotation);
        }
        return endPointUrl;
    }

    /**
     * Creates an endpoint URL based on the provided EndPoint annotation.
     *
     * @param controllerEndPointUrl The base endpoint URL for the controller class.
     * @param endPointAnnotation    The EndPoint annotation.
     * @return The URL of the endpoint.
     */
    private static String createAnnotationEndPointUrl(String controllerEndPointUrl, EndPoint endPointAnnotation) {
        String endPointAnnotationValue = endPointAnnotation.url();
        return controllerEndPointUrl + endPointAnnotationValue;
    }
}
