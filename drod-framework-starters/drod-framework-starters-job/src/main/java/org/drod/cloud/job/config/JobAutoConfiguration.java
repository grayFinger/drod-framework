package org.drod.cloud.job.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JobProperties.class)
@ConditionalOnProperty(value = "drod.job.enabled", havingValue = "true")
public class JobAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobAutoConfiguration.class);

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor(JobProperties jobProperties) {
        LOGGER.info(">>>>>>>>>>> job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(jobProperties.getAdmin().getAddresses());
        xxlJobSpringExecutor.setAppname(jobProperties.getExecutor().getAppName());
        xxlJobSpringExecutor.setAddress(jobProperties.getExecutor().getAddress());
        xxlJobSpringExecutor.setIp(jobProperties.getExecutor().getIp());
        xxlJobSpringExecutor.setPort(jobProperties.getExecutor().getPort());
        xxlJobSpringExecutor.setAccessToken(jobProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(jobProperties.getExecutor().getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(jobProperties.getExecutor().getLogRetentionDays());

        return xxlJobSpringExecutor;
    }
}
