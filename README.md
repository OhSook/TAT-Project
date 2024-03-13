## ✈️TAT 여행 패키지 어플리케이션

#### •개발 환경
ⅰ. 사용언어 : JAVA(JDK 17), SQL

ⅱ. Database : Oracle Database 11g

ⅲ. 개발도구 : Android Studio 2022.3.1. Patch 3, Oracle SQL Developer


### 서론


**a. 어플리케이션 제작 동기**

\- 좀 더 편리한 업무를 볼 수 있는 환경을 조성하기 위한 어플리케이션 제작, 스마트 오피스의 상용화를 목적으로 둔 프로젝트

**b. 프로젝트 계획**

| **데이터 모델링** | 데이터 모델링을 하여 어플리케이션 제작 계획을 수립 |
| --- | --- |
| **데이터베이스 설계** | ERD를 제작하고 DB를 구축 |
| **어플리케이션 제작** | DB를 제어할 수 있는 사용자별 어플리케이션을 제 작할 언어와 도구를 선택하고 제작 |
| **테스트** | 동료들과 테스트를 통해 안정성을 확보 |
##
### 본론

#### **a. 데이터 모델링**

#### _1) ERD 모델링_
![img](https://github.com/OhSook/TAT-Project/assets/62128698/0e36dd49-e4a7-4c6d-bd73-5a233fb8f75c)

#### _**2) Table Details**_

#### **1\. COMPANY\_USER (사원 데이터)**

![img (1)](https://github.com/OhSook/TAT-Project/assets/62128698/4fd87fb4-7599-41f4-a851-37be3a2972a7)

#### **2\. CREATE\_PACK (패키지 데이터)**

![img (2)](https://github.com/OhSook/TAT-Project/assets/62128698/7228fc10-c658-4fc5-b6cf-160b5dd9f6ad)

#### **3\. CUS\_USER (회원 데이터)**

![img (3)](https://github.com/OhSook/TAT-Project/assets/62128698/c5237e83-905d-4024-a4dd-5d100a6337ac)

#### **4\. QA (질문 데이터)**

![img (4)](https://github.com/OhSook/TAT-Project/assets/62128698/b75a9179-b8fc-4f22-ad9c-e2d253299255)

#### _**3) Database 구축 명세**_

database : Oracle Database

server : 학습용 노트북

## 
**b. Application 설계**

**a) 사용자 정의**

| **사용자**  | **설명** |
| --- | --- |
| 관리자, 직원  | 패키지 등록, 답변, 시스템 관리를 위한 어플리케이션 |
| 일반 사용자  | 여행패키지 예약 및 QnA를 위한어플리케이션 |

**b) 관리자, 직원 App**

_• Activity 스토리보드_

![img (5)](https://github.com/OhSook/TAT-Project/assets/62128698/ee6480d4-612d-42b2-87d3-f6e287fcf568)

_• Activity 명세_

![img (6)](https://github.com/OhSook/TAT-Project/assets/62128698/519c80e1-7d67-4b22-adef-2f64fbd0a6ea)

**c) 일반 사용자 App**

_• Activity 스토리보드_

![img (7)](https://github.com/OhSook/TAT-Project/assets/62128698/3f9434f2-4b1c-4906-bc8e-7a2da4479dbd)

_• Activity 명세_

![img (8)](https://github.com/OhSook/TAT-Project/assets/62128698/18ded7a3-0f11-4eeb-a06f-e4f8751c6ba0)

##
#### _•App 테스트 및 검증_

_ⅰ. Unit Testing_

 - Activity 간 Intent로 전달하는 stringExtra값과 RecyclerView, Adapter, Item Class file 간 의 데이터의 흐름을 Toast 메세지와 레이아웃에 배치된 TextView를 이용해 디버깅함.

 - 각자가 만든 Activity Class code를 서로 검토하고, Intent값을 넘길 때 사용하는 name이 다 르지 않게 유의하에 수정하였으며 각자 개발한 Activity Class code를 취합하며 테스트를 진행함.

_ii. Integration Testing_

\- 테스트용 여행 패키지 데이터, 사용자 데이터, 관리자 데이터를 생성해 여행 패키지 작성, 예약, 결제 등의 기능들을 구동시킴.

_iii. beta testing_

\- 다른 팀의 팀원들과 beta testing을 통해 피드백을 제공 받고, 그에 따라 추가 디 버깅을 실시하였음.

##
### 3\. 결론

_ⅰ. 프로젝트 요약_

 - Android Studio를 통해 응용 프로그램을 개발하기 위하여 시작한 프로젝트였으며, 여행사 업무 관리 프로그램을 제작하여 편리하게 업무를 볼 수 있는 환경을 조성하고, 사용자는 여행사에서 제공하는 패키지 목록과 결제 그리고 QnA항목까지 이용할 수 있는 어플리케이션 제작이 목적

##

### 시연영상

#### 사용자용 APP

https://github.com/OhSook/TAT-Project/assets/62128698/bff33f6f-502a-4d9c-a297-719b7c5539af

#### 관리자용 APP

https://github.com/OhSook/TAT-Project/assets/62128698/6771520f-9175-4e57-8594-21ef97a91551


 
