package com.woowacourse.pelotonbackend.support.log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

public class HttpLoggerFilter extends OncePerRequestFilter {
    private int maxPayloadLength = 1000;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws
        ServletException,
        IOException {

        long startTime = System.currentTimeMillis();
        StringBuffer reqInfo = plusRequestInfo(request, startTime);

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        String requestBody = this.getContentAsString(wrappedRequest.getContentAsByteArray(),
            request.getCharacterEncoding());
        String responseBody = this.getContentAsString(wrappedResponse.getContentAsByteArray(),
            response.getCharacterEncoding());

        final StringBuilder message = plusRequestHeader(request, reqInfo);
        plusRequestBody(requestBody, message);
        wrappedResponse.copyBodyToResponse();
        plusResponseInfo(response, startTime, message);
        plusResponseHeader(response, message);
        plusResponseBody(responseBody, message);

        this.logger.info(message.toString());
    }

    private void plusResponseBody(final String responseBody, final StringBuilder message) {
        message.append("}")
            .append("\n")
            .append("response body")
            .append("\n")
            .append(responseBody)
            .append("\n");
    }

    private void plusResponseHeader(final HttpServletResponse response, final StringBuilder message) {
        final Collection<String> responseHeaders = response.getHeaderNames();
        message.append("response headers : {").append("\n");

        for (String header : responseHeaders) {
            message.append("  ")
                .append(header)
                .append(" : ")
                .append(response.getHeader(header))
                .append(",")
                .append("\n");
        }
    }

    private void plusResponseInfo(final HttpServletResponse response, final long startTime,
        final StringBuilder message) {
        long duration = System.currentTimeMillis() - startTime;
        message.append("=====  response =====")
            .append(" returned status=" + response.getStatus() + " in " + duration + "ms").append("\n");
    }

    private void plusRequestBody(final String requestBody, final StringBuilder message) {
        if (requestBody.length() > 0) {
            message.append("request body : ")
                .append(requestBody)
                .append("\n");
        }
    }

    private StringBuilder plusRequestHeader(final HttpServletRequest request, final StringBuffer reqInfo) {
        return new StringBuilder()
            .append("\n")
            .append("=======  request  =====")
            .append(reqInfo)
            .append("\n")
            .append(getRequestHeaderString(request))
            .append("\n");
    }

    private StringBuffer plusRequestInfo(final HttpServletRequest request, final long startTime) {
        StringBuffer reqInfo = new StringBuffer()
            .append("[")
            .append(startTime % 10000)
            .append("] ")
            .append(request.getMethod())
            .append(" ")
            .append(request.getRequestURL());

        String queryString = request.getQueryString();
        if (queryString != null) {
            reqInfo.append("?").append(queryString);
        }

        if (request.getAuthType() != null) {
            reqInfo.append(", authType=")
                .append(request.getAuthType());
        }
        if (request.getUserPrincipal() != null) {
            reqInfo.append(", principalName=")
                .append(request.getUserPrincipal().getName());
        }
        return reqInfo;
    }

    private String getRequestHeaderString(final HttpServletRequest request) {
        final Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder headers = new StringBuilder();

        headers.append("request headers : {").append("\n");
        while (headerNames.hasMoreElements()) {
            final String name = headerNames.nextElement();
            headers.append("  ")
                .append(name)
                .append(" : ")
                .append(request.getHeader(name)).append(",").append("\n");
        }
        headers.append("}").append("\n");
        return headers.toString();
    }

    private String getContentAsString(byte[] buf, String charsetName) {
        if (buf == null || buf.length == 0)
            return "";
        int length = Math.min(buf.length, this.maxPayloadLength);
        try {
            return new String(buf, 0, length, charsetName);
        } catch (UnsupportedEncodingException ex) {
            return "Unsupported Encoding";
        }
    }
}