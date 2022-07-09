package org.drod.cloud.job.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "drod.job")
public class JobProperties {
    /**
     * 执行器通讯TOKEN
     */
    private String accessToken;
    @NestedConfigurationProperty
    private final Admin admin = new Admin();
    @NestedConfigurationProperty
    private final Executor executor = new Executor();

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Admin getAdmin() {
        return admin;
    }

    public Executor getExecutor() {
        return executor;
    }

    public static class Admin {
        /**
         * 调度中心部署根地址
         */
        private String addresses;

        public String getAddresses() {
            return addresses;
        }

        public void setAddresses(String addresses) {
            this.addresses = addresses;
        }
    }

    public static class Executor {
        /**
         * 执行器AppName
         */
        private String appName;
        /**
         * 执行器注册
         */
        private String address;
        /**
         * 执行器IP
         */
        private String ip;
        /**
         * 执行器端口号
         */
        private int port;
        /**
         * 执行器运行日志文件存储磁盘路径
         */
        private String logPath;
        /**
         * 执行器日志文件保存天数
         */
        private int logRetentionDays;

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getLogPath() {
            return logPath;
        }

        public void setLogPath(String logPath) {
            this.logPath = logPath;
        }

        public int getLogRetentionDays() {
            return logRetentionDays;
        }

        public void setLogRetentionDays(int logRetentionDays) {
            this.logRetentionDays = logRetentionDays;
        }
    }
}
