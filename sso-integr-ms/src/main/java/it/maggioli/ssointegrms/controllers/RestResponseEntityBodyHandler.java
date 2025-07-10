package it.maggioli.ssointegrms.controllers;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.dto.ResponseDTO;
import it.maggioli.ssointegrms.dto.ResponseListaDTO;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Cristiano Perin
 */
@ControllerAdvice(basePackages = {"it.maggioli.ssointegrms.controllers.internalAuthentication",
        "it.maggioli.ssointegrms.controllers.gestioneUtenti", "it.maggioli.ssointegrms.controllers.amministrazione"})
public class RestResponseEntityBodyHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof ResponseDTO || //
                body instanceof ResponseListaDTO || //
                body instanceof Resource || //
                request.getURI().toString().contains("swagger") || //
                request.getURI().toString().endsWith("/v3/api-docs") || //
                request.getURI().toString().contains("/actuator/") //
        ) {
            return body;
        } else {
            final ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setDone(AppConstants.RESPONSE_DONE_Y);
            if (body != null)
                responseDTO.setResponse(body);
            return responseDTO;
        }
    }

}
