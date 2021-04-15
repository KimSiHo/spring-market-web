#!/bin/bash

if [ -d //home/ec2-user/spring-market-web/zip ]; then
    sudo rm -rf /home/ec2-user/spring-market-web/zip
fi
sudo mkdir -vp /home/ec2-user/spring-market-web/zip