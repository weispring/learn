package com.lxc.learn.temp;
import com.lxc.learn.common.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.ProxyAsyncConfiguration;

import java.util.Iterator;
import java.util.Map;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//(scanBasePackages = {"com.lxc.learn"})
@EnableScheduling
//@EnableTransactionManagement
//@EnableAsync
@Slf4j
public class Application {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        printContion(context);

        /** @Condition */
        //SpringBootCondition

        /** @Async */
        //ProxyAsyncConfiguration
        //org.springframework.core.task.SimpleAsyncTaskExecutor


    }


    /**
     * 输出注解条件@Condition
     *
     */
    public static void printContion(BeanFactory beanFactory){
        ConditionEvaluationReport conditionEvaluationReport = beanFactory.getBean("autoConfigurationReport", ConditionEvaluationReport.class);
        Map<String, ConditionEvaluationReport.ConditionAndOutcomes> result = conditionEvaluationReport.getConditionAndOutcomesBySource();
        for(String key : result.keySet()) {
            ConditionEvaluationReport.ConditionAndOutcomes conditionAndOutcomes = result.get(key);
            Iterator<ConditionEvaluationReport.ConditionAndOutcome> iterator = conditionAndOutcomes.iterator();
            while(iterator.hasNext()) {
                ConditionEvaluationReport.ConditionAndOutcome conditionAndOutcome = iterator.next();
                System.out.println(key + " -- " + conditionAndOutcome.getCondition().getClass().getSimpleName() + " -- " + conditionAndOutcome.getOutcome());
            }
        }
    }
}
