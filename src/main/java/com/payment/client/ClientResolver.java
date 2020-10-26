package com.payment.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Component
public class ClientResolver {

    private HttpServletRequest request;
    private RestTemplate restTemplate;

    public String getClientIp() {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

    public ClientInfo getClientInfo(String ip) {
        ClientInfo client = null;
        if (ip != null && ip != "") {
            final String uri = "http://api.ipstack.com/" + ip + "?access_key=04cafd67a5a1b8af24f70213689542f5&format=1";
            client = restTemplate.getForObject(uri, ClientInfo.class);
        }
        return client;
    }

    public String getClientCountry() {
        return getClientInfo(getClientIp()).getCountry_name();
    }

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
