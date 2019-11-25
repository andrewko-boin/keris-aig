FROM node:latest

# /app 디렉토리 생성
RUN mkdir -p /usr/src/app
# /app 디렉토리를 WORKDIR 로 설정
WORKDIR /usr/src/app
# 현재 Dockerfile 있는 경로의 모든 파일을 /app 에 복사
COPY . /usr/src/app
# npm install 을 실행
RUN npm install

#가상 머신에 오픈할 포트
EXPOSE 8080

#컨테이너에서 실행될 명령을 지정
CMD ["npm", "run", "serve"]  