#!/bin/bash

REPOSITORY=/home/ec2-user/spring-market-web
PROJECT_NAME=spring-market-web

echo "> Build 파일 복사"

cp $REPOSITORY/zip/target/*.jar $REPOSITORY/

echo "> 현재 구동 중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl jpashop | awk '{print $1}')

echo "> 현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

echo "> 관리자 권한 획득"
sudo -s <<EOF
if [ -z "$CURRENT_PID" ]; then
        echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
        echo "> kill -n 15 $CURRENT_PID"
        kill -n 15 $CURRENT_PID
        sleep 5
fi
EOF

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME에 실행권한 추가"

sudo -s <<EOF
chmod +x $REPOSITORY/$JAR_NAME
EOF

echo "> $JAR_NAME 실행"

echo "> 관리자 권학 획득"

sudo -s <<EOF
nohup java -Dspring.profiles.active=real -jar $REPOSITORY/$JAR_NAME>$REPOSITORY/nohup.out 2>&1 &
EOF