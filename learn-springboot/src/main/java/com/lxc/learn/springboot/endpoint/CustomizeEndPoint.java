package com.lxc.learn.springboot.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.AbstractEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.endpoint.AbstractExposableEndpoint;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.EndpointServlet;
import org.springframework.boot.actuate.endpoint.web.servlet.AbstractWebMvcEndpointHandlerMapping;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/11 20:54
 */
@Slf4j
@Configuration
public class CustomizeEndPoint {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(CustomizeEndPoint.class);
        application.run(args);
    }

    public static class MemStatus {
        public MemStatus(Date date, Map<String, Object> status) {
            this.date = date;
            this.status = status;
        }
        private Date date;
        private Map<String, Object> status;
        public Date getDate() {
            return date;
        }
        public Map<String, Object> getStatus() {
            return status;
        }
    }

    public static class MemCollector {
        private int maxSize = 5;
        private List<MemStatus> status;

        public MemCollector(List<MemStatus> status) {
            this.status = status;
        }

        //@Scheduled(cron = "0/5 * *  * * ? ")
        public void collect() {
            System.out.println("-------------");
            Runtime runtime = Runtime.getRuntime();
            Long maxMemory = runtime.maxMemory();
            Long totalMemory = runtime.totalMemory();
            Map<String, Object> memoryMap = new HashMap<String, Object>(2, 1);
            Date date = Calendar.getInstance().getTime();
            memoryMap.put("maxMemory", maxMemory);
            memoryMap.put("totalMemory", totalMemory);
            if (status.size() > maxSize) {
                status.remove(0);
                status.add(new MemStatus(date, memoryMap));
            } else {
                status.add(new MemStatus(date, memoryMap));
            }
        }
    }


    @Endpoint(id = "my")
    public static class MyEndPoint {
        private List<MemStatus> status;
        public MyEndPoint(List<MemStatus> status) {
            this.status = status;
        }
        public String getId() {
            return "my";
        }
        public boolean isEnabled() {
            return true;
        }
        public boolean isSensitive() {
            return false;
        }

        @ReadOperation
        public Object invoke() {
            if (status == null || status.isEmpty()) {
                return "hello world";
            }
            Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();
            for (MemStatus memStatus : status) {
                for (Map.Entry<String, Object> entry : memStatus.status.entrySet()) {
                    List<Map<String, Object>> collectList = result.get(entry.getKey());
                    if (collectList == null) {
                        collectList = new LinkedList<Map<String, Object>>();
                        result.put(entry.getKey(), collectList);
                    }
                    Map<String, Object> soloCollect = new HashMap<String, Object>();
                    soloCollect.put("date", memStatus.getDate());
                    soloCollect.put(entry.getKey(), entry.getValue());
                    collectList.add(soloCollect);
                }
            }
            return result;
        }
    }

    public static class EndPointAutoConfig {
        private List<MemStatus> status = new ArrayList<MemStatus>();
        @Bean
        public MyEndPoint myEndPoint() {
            return new MyEndPoint(status);
        }
        @Bean
        public MemCollector memCollector() {
            return new MemCollector(status);
        }
    }



}
