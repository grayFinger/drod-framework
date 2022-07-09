package org.drod.framework.consul;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistrationCustomizer;

import java.util.ArrayList;
import java.util.List;


/**
 * 新增tags，系统给默认的tags。boot admin根据tag标志来做服务监控过滤
 * @author e9
 */
public class TagsModifyRegistrationCustomizer implements ConsulRegistrationCustomizer {


    @Value("${drod.framework.version}")
    private String frameworkVersion  ;

    @Override
    public void customize(ConsulRegistration registration) {
        List<String> tags =  registration.getService().getTags();
        if (tags == null) {
            tags = new ArrayList();
        }
        tags.add("e9-framework-version=" + frameworkVersion);
        registration.getService().setTags((List)tags);
    }
}
