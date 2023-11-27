# Getting Started

### How to build the Jar
To build the JAR run the following command in the project root directory

`````./gradlew bootJar`````

Command to connect to Ec2:

```ssh -i .ssh/mtp-aws-accessKey.pem ubuntu@13.250.233.194```

Command to send file to Ec2:

``scp -i .ssh/mtp-aws-accessKey.pem /Users/piyush.mittal/mtp_workspace/mtp/build/libs/mtp-0.0.1-SNAPSHOT.jar ubuntu@13.250.233.194:~/.``

Command to start and stop the services on Ec2 Instance:
```
/usr/local/bin/./mtp.sh start      
/usr/local/bin/./mtp.sh stop      
/usr/local/bin/./mtp.sh start      
/usr/local/bin/./mtp.sh restart
```

To check if NGINX is running:

``
systemctl status nginx
``

NGINX config file at:

``/etc/nginx/sites-available``

Reload NGINX:

```
sudo nginx -t

sudo nginx -s reload
```

Globally set the ENV variable on Ubuntu

``
sudo vi /etc/environment
``

Add the following line:

``MTP_ENVIRONMENT="production"``

Apply changes using source command.

``source /etc/environment``

Test it using echo command.

```
echo $MTP_ENVIRONMENT
# production
```

Twilio Recovery code:
``4vuJ_gZ2p4B7r8ODVYDJZQ2x4uK_941NTYBYswn2``

JWT Token for root:

``eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrNjI4Nzg1ODg4MTI1NiIsImlhdCI6MTY4ODI2ODY0OSwiZXhwIjoxNzE5ODA0NjQ5fQ.GPOSpi1x7Fo5s4MSqbo5J6PuTGF18zKcGaS3nkOqVvc``
