###
# To build:
#    docker build -t downgoon/jresty-ping .
# 
# To run: 	
# 	docker run -t -i -p 8080:8080 downgoon/jresty-ping
###



FROM  hub.c.163.com/public/tomcat:7.0.28
MAINTAINER netease

# install maven and jdk7    
RUN apt-get update && apt-get install -y maven openjdk-7-jdk    

# Set the location of the tomcat
ENV TOMCAT_ROOT /var/lib/tomcat7/webapps/ROOT/

# expose http port
EXPOSE 8080

COPY . ~/java
WORKDIR ~/java
COPY ./settings.xml ~/.m2/
    
RUN mvn package && cp -rf ~/java/jresty-rest-example/target/jresty-rest-example-*.war $TOMCAT_ROOT
ENTRYPOINT /etc/init.d/tomcat7 start