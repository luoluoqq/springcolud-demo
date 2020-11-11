package com.example.eurekaclient.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    String port;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String index() {
        StringBuffer uriList = new StringBuffer("Hello World " + port + " 端口为您服务！<br>");
        List<ServiceInstance> list = discoveryClient.getInstances("eureka-client");
        uriList.append("<br>discoveryClient.getServices().size() = " + discoveryClient.getServices().size());
        for( String s :  discoveryClient.getServices()){
            List<ServiceInstance> serviceInstances =  discoveryClient.getInstances(s);
            for(ServiceInstance si : serviceInstances){
                uriList.append("<br>services:" + s + ":getHost()=" + si.getHost());
                uriList.append("<br>services:" + s + ":getPort()=" + si.getPort());
                uriList.append("<br>services:" + s + ":getServiceId()=" + si.getServiceId());
                uriList.append("<br>services:" + s + ":getUri()=" + si.getUri());
            }
        }
        return uriList.toString();
    }
}
