# 使用官方的 Java 21 镜像作为基础镜像
FROM openjdk:21

# 设置工作目录
WORKDIR /app

# 将打包好的 JAR 文件复制到容器中
COPY target/bank-transaction-service-0.0.1.jar bank-transaction-service-0.0.1.jar

# 暴露端口
EXPOSE 8088

# 启动应用
ENTRYPOINT ["java", "-jar", "bank-transaction-service-0.0.1.jar"]
