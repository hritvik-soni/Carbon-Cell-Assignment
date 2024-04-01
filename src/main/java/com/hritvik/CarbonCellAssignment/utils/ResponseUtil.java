package com.hritvik.CarbonCellAssignment.utils;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ResponseUtil {

    public Response badRequestResponse(String message) {
        ResponseError error = new ResponseError("400", message);
        return new Response(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, null, error);
    }

    public Response notFoundResponse(String message) {
        ResponseError error = new ResponseError("404", message);
        return new Response(HttpStatus.NOT_FOUND.value(), Boolean.FALSE,null, null, error);
    }

    public Response internalServerErrorResponse(String message) {
        ResponseError error = new ResponseError("500", message);
        return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE,null, null, error);
    }

    public Response conflictResponse(String message) {
        ResponseError error = new ResponseError("409", message);
        return new Response(HttpStatus.CONFLICT.value(), Boolean.FALSE, null,null, error);
    }

    public Response forbiddenResponse(String message) {
        ResponseError error = new ResponseError("403", message);
        return new Response(HttpStatus.FORBIDDEN.value(), Boolean.FALSE, null,null, error);
    }

    public Response successResponse(String message,Object result) {
        return new Response(HttpStatus.OK.value(), Boolean.TRUE, message, result, null);
    }

    public Response unauthorizedResponse(String message) {
        ResponseError error = new ResponseError("401", message);
        return new Response(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE, null,null, error);
    }


}
