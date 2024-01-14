* 분실물 센터 앱
***


앱 소개
---
물건을 분실했을 때 물건을 찾기 위해 여기저기에 전화를 걸어보고 방문했던 장소로 돌아가 물건을 찾아보느라 시간을 허비한 경험이나
누군가의 분실물을 습득했을 때 물건을 어떻게 돌려줘야 할지 몰라 당황스러운 경험이 누구나 한번쯤은 있을 것입니다.
이러한 불편함을 해소하고자 분실물 센터 앱을 개발하게 되었습니다.

분실물 센터 앱의 사용자는 분실물을 등록하고 찾기를 원하는 분실물을 검색할 수 있습니다. 이 앱은 분실물이 발생한
장소, 시간, 분실물의 종류 등의 정보를 수집하고 이 정보를 기반으로 분실물을 찾는데 도움을 줍니다. 
또한 분실물을 갖고 있는 사람과 채팅을 통해 대화해 분실물을 찾을 수 있습니다. 


팀원 소개
---
강필재: 채팅 관련 부분 구현(https://github.com/piljaekang)

좌소연: 지도, 게시물 관련 부분 구현

구현
---
코틀린

google maps API

firebase


개발기간
---
2023.02 ~ 2023.4


앱 사용 방법
---
1. 처음 화면(로그인 화면)

<img width="375" alt="스크린샷 2023-04-30 오전 5 49 46" src="https://user-images.githubusercontent.com/81522548/235324861-9873596d-d3ee-4c19-a0aa-76fdc90fc968.png">

register 버튼을 클릭해 회원가입을 한 후 아이디와 비밀번호를 누르고 로그인하면 된다.

2. 메인 게시물 화면

<img width="375" alt="스크린샷 2023-04-30 오전 5 50 22" src="https://user-images.githubusercontent.com/81522548/235324900-6178cbe1-c68c-429b-bb09-51a4c48920cc.png">

게시물 화면으로 분실물에 관한 게시물이 올라와 있다.

2-1. 게시물 화면

<img width="375" alt="스크린샷 2023-04-30 오전 5 50 27" src="https://user-images.githubusercontent.com/81522548/235324934-3a217945-7cb0-4c7e-aea2-4fe403b7c7f2.png">

돋보기 버튼을 눌렀을 때 화면으로 분실물에 대한 정보를 더 자세히 검색할 수 있다.

2-2. 게시물 화면

<img width="375" alt="스크린샷 2023-04-30 오전 5 51 29" src="https://user-images.githubusercontent.com/81522548/235325099-c619f43c-0810-4b3d-b6da-d0bf82d920c9.png">

아래 편지 모양 버튼을 눌러 게시물을 올린 사람과 채팅할 수 있다.

3. 게시물 등록 화면

<img width="375" alt="스크린샷 2023-04-30 오전 5 50 33" src="https://user-images.githubusercontent.com/81522548/235324980-2bc14fda-acae-4275-a021-15c6c0dcb1fa.png">
<img width="375" alt="스크린샷 2023-04-30 오전 5 50 39" src="https://user-images.githubusercontent.com/81522548/235324988-a40042ef-b55c-4ee1-af1d-376c435bba0c.png">

4. 지도 화면

<img width="375" alt="스크린샷 2023-04-30 오전 5 50 46" src="https://user-images.githubusercontent.com/81522548/235325002-f4e8e436-22ab-4761-8c93-05276060f278.png">

하단에 지도 버튼을 누르면 볼 수 있는 화면으로 빨간 색 화살표로 현재 위치를 표시하고 있다. 오른쪽 아래 나침반 버튼을 누르면 현재 위치를 표시하고 돋보기 버튼을 누르면 지도에 물건을 잃어버린 위치나 물건을 습득한
위치가 파란색 화살표로 표시되어 있다. 위에 검색창으로 원하는 위치로 이동가능하다.

4-1. 지도 검색창

<img width="375" alt="스크린샷 2023-04-30 오전 5 50 57" src="https://user-images.githubusercontent.com/81522548/235325062-a7e56a54-20da-4a04-ae17-efb01e154f0b.png">

4-2. 분실물 위치가 표시된 지도

<img width="375" alt="스크린샷 2023-04-30 오전 5 51 17" src="https://user-images.githubusercontent.com/81522548/235325077-ab022de0-0490-43a2-b0b6-065bfb46031b.png">

5. 채팅 화면

<img width="375" alt="스크린샷 2023-04-30 오전 5 52 19" src="https://user-images.githubusercontent.com/81522548/235325125-a964cac2-c616-4d8a-b066-b917c4816672.png">

현재 대화하고 있는 사람들의 이름을 표시해둔 채팅 화면으로 이름을 클릭하면 대화화면으로 넘어갈 수 있다.

5-1.

<img width="375" alt="스크린샷 2023-04-30 오전 5 52 41" src="https://user-images.githubusercontent.com/81522548/235325143-04ab6e2d-2f13-4d69-840e-e0ce96a0295f.png">



