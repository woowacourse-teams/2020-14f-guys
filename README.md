# PELOTON 프로젝트
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

![https://user-images.githubusercontent.com/49060374/91448157-8026b880-e8b4-11ea-9273-1c77fe0a09b2.png](https://user-images.githubusercontent.com/49060374/91448157-8026b880-e8b4-11ea-9273-1c77fe0a09b2.png)

![peloton](./image/peloton.gif)

## 소개

- 목표 달성을 위한 동기부여 서비스
    - 목표를 함께 달성할 Peloton을 모집할 수 있습니다.
    - 성취도에 따라서 모인 금액을 차등 지급합니다.
    - 그룹별, 주제별 랭킹 시스템을 도입하여 동기와 소속감을 부여합니다.

- API 문서 : [https://peloton.ga](https://peloton.ga/)
- Android : 구글 플레이 스토어에 `펠로톤` 이라고 검색하시면 찾을 수 있습니다.

## 사용자 스토리

- 데일리
    - 제이슨네 데일리는 매일 지각자가 나왔다. 그래서 펠로톤을 사용해 아침 10시까지 데일리에 참석하지 않을 경우 지각으로 간주하기로 했다. 보스독을 비롯한 10명의 크루들은 만원을 입금하고, 펠로톤 기간을 2주로 정했다. 2주의 펠로톤이 끝난 뒤, 성취도(지각 비율)에 따라 크루들은 돈을 분배받았다. 지각을 제일 많이한 보스독은 가장 적은 돈을 가져갔고, 지각을 한번도 하지 않은 터틀은 가장 많은 돈을 가져갔다.
- 스터디
    - 카일은 대학교 커뮤니티에서 익명의 사람들과 기상 스터디를 하기로 했다. 아침에 일어난 걸 증명하기 위해 펠로톤 서비스에 그룹을 등록한다. 카일은 3만원을 입금하고, 매일 아침 펠로톤 서비스에 6시에 기상하여 인증 사진을 등록한다. 정상 출근율이 30%인 호돌이는 입금 금액에 30%만 돌려받고, 나머지 회원들도 정상 출근율에 비례하여 금액을 돌려받는다. 남는 금액에 대해서 성취도가 100%인 스터디원들에게 상금으로 분배된다.
- 개인
    - 디디는 좋은 개발자가 되기위해 1일 1커밋을 다짐했다. 이와 동시에 펠로톤에서 제공하는 1일1커밋 그룹에 만원을 내고 참여하여 한달동안 1일 1커밋을 완성했다. 약 500명의 사람들이 참여했는데, 이 중 성공하지 못한 사람의 돈 일부가 성공한 사람들에게 입금되었다. 디디는 한달간 1일 1커밋도 지켰을 뿐 아니라 상금도 받게되어 기분이 좋다.  ***⇒ 선입금 및 미션 완료 시점에 돌려받는 형태***

## 사용 기술

### Back-end

- Java8
- Spring boot
- Spring Data Jdbc
- Liquibase
- Maria DB / H2

### Front-end

- Javascript es6
- React Native with Expo
- Recoil

### Infra

- AWS EC2, S3
- Docker
- Log4j2
- Jenkins
- Nginx

### Tools

- Intellij
- Mac os
- Git
- Linux Ubuntu

