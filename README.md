# Auth Service
|Method|API|Description|
|---|---|---|
|POST|/auth-service/v1/auth/signup|회원가입|
|POST|/auth-service/v1/auth/signin|로그인|
|POST|/auth-service/v1/auth/refresh_token|refresh token|
|POST|/auth-service/v1/group/list|그룹 조회|
|GET|/auth-service/v1/group/allList|전체 그룹 조회|
|POST|/auth-service/v1/group/delete|그룹 삭제|
|POST|/auth-service/v1/group/reg|그룹 생성|
|POST|/auth-service/v1/group/participate|그룹 참여|
|POST|/auth-service/v1/group/isParticipated|그룹 참여자 여부 조회|
|GET|/auth-service/v1/group/detail/{groupId}|그룹 상세 정보 조회|
|GET|/auth-service/v1/group/searchId/{regId}|그룹 조회(그룹 생성자)|
|GET|/auth-service/v1/group/searchName/{groupName}|그룹 조회(그룹 이름)|
|GET|/auth-service/v1/user/detail|사용자 상세 정보 조회|
|POST|/auth-service/v1/user/survey_users_analysis|사용자 목록 조회|
|GET|/auth-service/v1/user/allUserList|사용자 검색|
|POST|/auth-service/v1/user/modify|사용자 상세 정보 수정|
|POST|/auth-service/v1/user/search|사용자 검색|
