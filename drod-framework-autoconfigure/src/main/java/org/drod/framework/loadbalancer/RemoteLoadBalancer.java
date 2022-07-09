package org.drod.framework.loadbalancer;

import org.drod.framework.web.common.HttpRequestConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 可以通过remote_ip调试本地服务
 *
 * @author e9
 */
public class RemoteLoadBalancer implements ReactorServiceInstanceLoadBalancer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteLoadBalancer.class);

    final AtomicInteger position;

    final String serviceId;

    ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    /**
     * @param serviceInstanceListSupplierProvider
     *            a provider of {@link ServiceInstanceListSupplier} that will be used to get available instances
     * @param serviceId
     *            id of the service for which to choose an instance
     */
    public RemoteLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
        String serviceId) {
        this(serviceInstanceListSupplierProvider, serviceId, new Random().nextInt(1000));
    }

    /**
     * @param serviceInstanceListSupplierProvider
     *            a provider of {@link ServiceInstanceListSupplier} that will be used to get available instances
     * @param serviceId
     *            id of the service for which to choose an instance
     * @param seedPosition
     *            Round Robin element position marker
     */
    public RemoteLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
        String serviceId, int seedPosition) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.position = new AtomicInteger(seedPosition);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier =
            serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        // 获取remoteIp
        Optional<String> remoteIp = Optional.ofNullable(((RequestDataContext)request.getContext()).getClientRequest()
            .getHeaders().getFirst(HttpRequestConstants.REMOTE_IP));
        return supplier.get(request).next()
            .map(serviceInstances -> processInstanceResponse(supplier, serviceInstances, remoteIp));
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
        List<ServiceInstance> serviceInstances, Optional<String> remoteIp) {
        Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances, remoteIp);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback)supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, Optional<String> remoteIp) {
        if (instances.isEmpty()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("No servers available for service: " + serviceId);
            }
            return new EmptyResponse();
        }

        // 判断是否有remoteIp
        Optional<ServiceInstance> filterServiceInstance = Optional.empty();
        if (remoteIp.isPresent()) {
            for (ServiceInstance instance : instances) {
                if (StringUtils.equals(instance.getHost(), remoteIp.get())) {
                    filterServiceInstance = Optional.of(instance);
                    break;
                }
            }
        }

        // 如果没有则，取随机值
        if (!filterServiceInstance.isPresent()) {
            int pos = Math.abs(this.position.incrementAndGet());
            filterServiceInstance = Optional.of(instances.get(pos % instances.size()));
        }

        return new DefaultResponse(filterServiceInstance.get());
    }
}
