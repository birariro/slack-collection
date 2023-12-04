[![version](https://img.shields.io/badge/springboot-3.2.1-00bfb3?style=flat&logo=spring-boot)]()
[![version](https://img.shields.io/badge/jdk-17.0.9-e1beee?style=flat&logo=jdk)]()
[![version](https://img.shields.io/badge/springdoc-1.6.9-00bfb3?style=flat&logo=swagger)]()

### slack bot read
1. 봇 생성
   1. https://api.slack.com/apps
2. 봇 권한 설정 및 토큰 얻기
   1. Oauth&Permissions tab 이동
   2. scopes 추가 ```channels:history```, ```channels:read```
   3. token 얻기 ```xoxb-xxxxxxx```
3. slack 방에 봇 초대
   1. ```/invite @bot name```

### application-ket
```
slack:
  token : xoxb-xxxxxxx
```

### docker compose setup
> docker compose -f tools/docker-compose.yml up