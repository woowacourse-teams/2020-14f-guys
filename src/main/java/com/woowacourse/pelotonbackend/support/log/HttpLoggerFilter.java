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

        StringBuilder reqInfo = plusRequestInfo(request, startTime);
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        String requestBody = this.getContentAsString(wrappedRequest.getContentAsByteArray(),
            request.getCharacterEncoding());
        final StringBuilder message = plusRequestHeader(request, reqInfo);
        plusRequestBody(requestBody, message);

        String responseBody = this.getContentAsString(wrappedResponse.getContentAsByteArray(),
            response.getCharacterEncoding());
        wrappedResponse.copyBodyToResponse();
        plusResponseInfo(response, startTime, message);
        plusResponseHeader(response, message);
        plusResponseBody(responseBody, message);

        this.logger.info(message.toString());
    }

    private void plusResponseBody(final String responseBody, final StringBuilder message) {
        message.append("}")
            .append(System.lineSeparator())
            .append("response body")
            .append(System.lineSeparator())
            .append(responseBody)
            .append(System.lineSeparator());
    }

    private void plusResponseHeader(final HttpServletResponse response, final StringBuilder message) {
        final Collection<String> responseHeaders = response.getHeaderNames();
        message.append("response headers : {").append(System.lineSeparator());

        for (String header : responseHeaders) {
            message.append("  ")
                .append(header)
                .append(" : ")
                .append(response.getHeader(header))
                .append(",")
                .append(System.lineSeparator());
        }
    }

    private void plusResponseInfo(final HttpServletResponse response, final long startTime,
        final StringBuilder message) {
        long duration = System.currentTimeMillis() - startTime;
        message.append("=====  response =====")
            .append(" returned status=" + response.getStatus() + " in " + duration + "ms")
            .append(System.lineSeparator());
    }

    private void plusRequestBody(final String requestBody, final StringBuilder message) {
        if (requestBody.length() > 0) {
            message.append("request body : ")
                .append(requestBody)
                .append(System.lineSeparator());
        }
    }

    private StringBuilder plusRequestHeader(final HttpServletRequest request, final StringBuilder reqInfo) {
        return new StringBuilder()
            .append(System.lineSeparator())
            .append("=======  request  =====")
            .append(reqInfo)
            .append(System.lineSeparator())
            .append(getRequestHeaderString(request))
            .append(System.lineSeparator());
    }

    private StringBuilder plusRequestInfo(final HttpServletRequest request, final long startTime) {
        StringBuilder reqInfo = new StringBuilder()
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

        headers.append("request headers : {").append(System.lineSeparator());
        while (headerNames.hasMoreElements()) {
            final String name = headerNames.nextElement();
            headers.append("  ")
                .append(name)
                .append(" : ")
                .append(request.getHeader(name)).append(",").append(System.lineSeparator());
        }
        headers.append("}").append(System.lineSeparator());
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