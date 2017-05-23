# MANTAINER zli [liz@yyft.com]
# DOCKER-VERSION
#
# Dockerizing jdk: Dockerfile for building jdk8 images

#

FROM java:8

MAINTAINER zli [liz@yyft.com]

# 环境变量
ENV APP_NAME @project.build.finalName@
ENV APP_VERSION @project.version@

#端口
EXPOSE 8080

RUN mkdir -p /home/warehouse/web/boss
WORKDIR /home/warehouse/web/boss


COPY @project.build.finalName@.tar.gz /home/warehouse/web/boss/@project.build.finalName@.tar.gz
RUN tar -zxvf @project.build.finalName@.tar.gz && rm *.tar.gz

#WORKDIR /home/warehouse/web/boss/bin
#-v能挂载指定的主机目录，而volume不行
#volme挂载在宿主机/var/lib/docker/volumes下
#VOLUME mylog:/home/warehouse/web/boss/logs

RUN chmod +x ./bin/catalina.sh
#ENTRYPOINT ["./bin/catalina.sh", "start"]
CMD ["./bin/catalina.sh"]