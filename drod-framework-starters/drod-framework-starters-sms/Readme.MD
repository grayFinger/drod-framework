#短信二方包：
#引入方式：

````
<dependency>
    <groupId>com.e9.cloud</groupId>
    <artifactId>e9-framework-starters-sms</artifactId>
    <version>${当前版本}</version>
</dependency>
````

#配置方式：

````
drod:
  submail:
        sms:
          enabled: true
          app-id: ${SUBMEIL平台的 appid}
          app-key: ${SUBMEIL平台的 appkey}
          template-signature: ${SUBMEIL平台的 sms_signature 公司名或应用、APP、网站名作为您的短信签名，请将签名字数控制在 2-10 个字符以内，并使用全角大括号“【”和“】”包括，如:“【SUBMAIL】”}
````
