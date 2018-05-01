package com.stefan.xhc.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SecurityUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private SecurityUtils() {
    }

    public static void sendError(HttpServletResponse response, Exception exception, int status, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        PrintWriter writer = response.getWriter();

        ResponseBody responseBody = new ResponseBody(message, exception.getMessage());
        writer.write(MAPPER.writeValueAsString(responseBody));

        writer.flush();
        writer.close();
    }


    public static void sendResponse(HttpServletResponse response, int status, Object object) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);

        PrintWriter writer = response.getWriter();
        writer.write(MAPPER.writeValueAsString(object));

        writer.flush();
        writer.close();
    }
}
