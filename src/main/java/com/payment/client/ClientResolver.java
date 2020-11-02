package com.payment.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Component
public class ClientResolver {

    private HttpServletRequest request;
    private RestTemplate restTemplate;

    public static final String IP_STACK_ACCESS_KEY = "04cafd67a5a1b8af24f70213689542f5&format=1";
    public static final String IP_STACK = "http://api.ipstack.com/";
    public static final String ACCESS_KEY_PARAM = "?access_key=";

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
            client = restTemplate.getForObject(getUriForClientInfo(ip), ClientInfo.class);
        }
        return client;
    }

    public String getUriForClientInfo(String ip) {
        String uriForClient = getIpStackUri(ip, IP_STACK_ACCESS_KEY);
        return uriForClient;
    }

    public String getIpStackUri(String ip, String accessKey) {
        return IP_STACK + ip + ACCESS_KEY_PARAM + accessKey;
    }

    public String getClientCountry() {
        return getClientInfo(getClientIp()).getCountryName();
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
