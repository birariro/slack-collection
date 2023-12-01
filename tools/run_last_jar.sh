#!/bin/bash

JARS_DIR=$(dirname $0)
PORT=9999
PROFILE=prod

LATEST_VERSION=""
LATEST_FILE=""

# 최신 버전 jar 파일 이름 확인
for file in "$JARS_DIR"/*.jar; do
    filename=$(basename "$file")
    version=$(echo "$filename" | grep -oP '\d+\.\d+\.\d+\.\d+(?=\.jar)')

    if [[ "$(printf '%s\n' "$version" "$LATEST_VERSION" | sort -V | tail -n 1)" == "$version" ]]; then
        LATEST_VERSION="$version"
        LATEST_FILE="$filename"
    fi
done

echo "last version jar: $JARS_DIR/$LATEST_FILE"

# 기존 실행중인 jar 종료
PID=`lsof -i :${PORT} | awk '{if (NR!=1) {print $2}}'`

echo -ne "old jar \e[1;31m stop [PID : ${PID}] \e[0m \n"
kill -9 ${PID}

# jar 의 위치로 이동(현 위치에서 실행해야 어플리케이션 root 를 잘 잡을 수 있음)
# 최신버전 jar 실행
cd  ${JARS_DIR} \
&& java -jar -Dspring.profiles.active=${PROFILE} ${JARS_DIR}/${LATEST_FILE}


PID=`lsof -i :${PORT} | awk '{if (NR!=1) {print $2}}'`
echo -ne "new jar \e[1;32m running [PID : ${PID}] \e[0m \n"





