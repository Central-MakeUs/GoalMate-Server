/*******************************************************************************************
 *  Goal #1
 *******************************************************************************************/

-- 1) goal 테이블에 단 하나의 Goal (id=1) 삽입
INSERT INTO goal (id, -- id 직접 지정
                  discount_price,
                  end_date,
                  free_participants_limit,
                  participants_limit,
                  period,
                  price,
                  start_date,
                  created_at,
                  updated_at,
                  description,
                  name,
                  topic,
                  goal_status)
VALUES (1, -- 직접 지정한 PK
        8000, -- 할인 가격
        '2026-01-31', -- 목표 종료일
        30, -- 무료 참여 가능 인원
        300, -- 전체 참여 제한 인원
        100, -- 목표 기간(100일)
        15000, -- 원 가격
        '2025-10-15', -- 목표 시작일
        NOW(), -- 생성 시각
        NOW(), -- 업데이트 시각
        '100일 동안 체계적으로 영어 듣기·말하기·독해 실력을 높이는 집중 프로젝트입니다.',
        '2025 영어 완성 챌린지',
        '영어 학습',
        'NOT_STARTED' -- enum: NOT_STARTED / IN_PROGRESS / COMPLETED
       );

-- 2) goal_content_image 테이블에 5개 이상의 이미지 (goal_id=1)
INSERT INTO goal_content_image (goal_id, image_url)
VALUES (1, 'https://cdn.mygoal-site.com/images/english_challenge_01.jpg'),
       (1, 'https://cdn.mygoal-site.com/images/english_challenge_02.jpg'),
       (1, 'https://cdn.mygoal-site.com/images/english_challenge_03.jpg'),
       (1, 'https://cdn.mygoal-site.com/images/english_challenge_04.jpg'),
       (1, 'https://cdn.mygoal-site.com/images/english_challenge_05.jpg');

-- 3) goal_thumbnail_image 테이블에 5개 이상의 썸네일 이미지 (goal_id=1)
INSERT INTO goal_thumbnail_image (goal_id, image_url)
VALUES (1, 'https://cdn.mygoal-site.com/thumbnails/english_thumb_01.jpg'),
       (1, 'https://cdn.mygoal-site.com/thumbnails/english_thumb_02.jpg'),
       (1, 'https://cdn.mygoal-site.com/thumbnails/english_thumb_03.jpg'),
       (1, 'https://cdn.mygoal-site.com/thumbnails/english_thumb_04.jpg'),
       (1, 'https://cdn.mygoal-site.com/thumbnails/english_thumb_05.jpg');

-- 4) goal_mid_objective 테이블에 5개 이상의 중간 목표 (goal_id=1)
INSERT INTO goal_mid_objective (goal_id, description)
VALUES (1, '첫 30일: 영어 기초 문법 및 단어 암기 집중'),
       (1, '중간 30일: 영상(영화·드라마) 청취 훈련 및 70% 이해 목표'),
       (1, '중간 60일: 영어 일기·에세이 작성 주 2회 달성'),
       (1, '중간 75일: 1:1 영어 회화 코칭 5회 이상 참여'),
       (1, '마지막 25일: 실전 영어 회화 모임에서 발표 세션 진행');

-- 5) goal_weekly_objective 테이블에 5개 이상의 주차별 목표 (goal_id=1)
INSERT INTO goal_weekly_objective (week_number, goal_id, description)
VALUES (1, 1, '1주차 - 영어 문장 50개 암기 및 음독 연습'),
       (2, 1, '2주차 - TED 강연 2개 이상 시청 후 요약'),
       (3, 1, '3주차 - 영어 신문 기사 매일 1개씩 읽고 단어 정리'),
       (4, 1, '4주차 - 원어민 발음 비교 녹음 5회 실시'),
       (5, 1, '5주차 - 간단한 영어 인터뷰 스크립트 작성 및 연습');

-- 6) goal_daily_todo 테이블에 5개 이상의 일별 TODO (goal_id=1)
INSERT INTO goal_daily_todo (todo_date, goal_id, description)
VALUES ('2025-10-15', 1, 'Day 1 - 전체 학습 플랜 점검 및 기본 레벨 테스트'),
       ('2025-10-16', 1, 'Day 2 - 발음 교정 앱으로 30분 연습 후 녹음 제출'),
       ('2025-10-17', 1, 'Day 3 - 영어 기사 1개 읽고 핵심 단어 10개 정리'),
       ('2025-10-18', 1, 'Day 4 - 드라마 한 에피소드 자막 없이 시청 후 요약'),
       ('2025-10-19', 1, 'Day 5 - 영어 일기 작성(반 페이지 이상) 및 피드백 확인');

/*******************************************************************************************
 *  Goal #2: "데이터 사이언스 마스터 챌린지"
 *******************************************************************************************/

-- 1) goal 테이블에 Goal 2 (id=2) 삽입
INSERT INTO goal (id,
                  discount_price,
                  end_date,
                  free_participants_limit,
                  participants_limit,
                  period,
                  price,
                  start_date,
                  created_at,
                  updated_at,
                  description,
                  name,
                  topic,
                  goal_status)
VALUES (2,
        2000, -- 할인 가격
        '2026-06-30', -- 목표 종료일
        20, -- 무료 참여 가능 인원
        200, -- 전체 참여 제한 인원
        90, -- 목표 기간(90일)
        8000, -- 원 가격
        '2026-04-01', -- 목표 시작일
        NOW(), -- 생성 시각
        NOW(), -- 업데이트 시각
        '데이터 사이언스 기초부터 프로젝트 실습까지 90일 동안 정복하는 챌린지입니다.',
        '데이터 사이언스 마스터 챌린지',
        '데이터 사이언스',
        'NOT_STARTED');

-- 2) goal_content_image 테이블 (goal_id=2)
INSERT INTO goal_content_image (goal_id, image_url)
VALUES (2, 'https://cdn.datascience.com/images/ds_challenge_01.jpg'),
       (2, 'https://cdn.datascience.com/images/ds_challenge_02.jpg'),
       (2, 'https://cdn.datascience.com/images/ds_challenge_03.jpg'),
       (2, 'https://cdn.datascience.com/images/ds_challenge_04.jpg'),
       (2, 'https://cdn.datascience.com/images/ds_challenge_05.jpg');

-- 3) goal_thumbnail_image 테이블 (goal_id=2)
INSERT INTO goal_thumbnail_image (goal_id, image_url)
VALUES (2, 'https://cdn.datascience.com/thumbnails/ds_thumb_01.jpg'),
       (2, 'https://cdn.datascience.com/thumbnails/ds_thumb_02.jpg'),
       (2, 'https://cdn.datascience.com/thumbnails/ds_thumb_03.jpg'),
       (2, 'https://cdn.datascience.com/thumbnails/ds_thumb_04.jpg'),
       (2, 'https://cdn.datascience.com/thumbnails/ds_thumb_05.jpg');

-- 4) goal_mid_objective 테이블 (goal_id=2)
INSERT INTO goal_mid_objective (goal_id, description)
VALUES (2, '첫 30일: 파이썬 기초 & 데이터 분석 라이브러리 학습'),
       (2, '중간 30일: 머신러닝 알고리즘 실습 및 간단한 모델 구현'),
       (2, '중간 60일: 통계 & 수학 개념 정리, 캐글 미니 프로젝트 참여'),
       (2, '중간 75일: 딥러닝 기본 모델 설계 및 GPU 환경 실습'),
       (2, '마지막 15일: 대규모 데이터셋 프로젝트 완성 & 발표 준비');

-- 5) goal_weekly_objective 테이블 (goal_id=2)
INSERT INTO goal_weekly_objective (week_number, goal_id, description)
VALUES (1, 2, '1주차: 파이썬 문법 복습 및 Numpy/Pandas 기본 실습'),
       (2, 2, '2주차: 시각화 도구(Matplotlib, Seaborn) 연습'),
       (3, 2, '3주차: 머신러닝 기초 개념(회귀, 분류) 공부'),
       (4, 2, '4주차: Scikit-learn으로 모델 만들어보기'),
       (5, 2, '5주차: 캐글 입문 대회 참여하여 모델 성능 개선 시도');

-- 6) goal_daily_todo 테이블 (goal_id=2)
INSERT INTO goal_daily_todo (todo_date, goal_id, description)
VALUES ('2026-04-01', 2, 'Day 1 - 파이썬 환경 설정 및 기본 구문 복습'),
       ('2026-04-02', 2, 'Day 2 - Numpy 배열 연산 실습 (백터/행렬)'),
       ('2026-04-03', 2, 'Day 3 - Pandas로 간단한 EDA(탐색적 데이터 분석) 진행'),
       ('2026-04-04', 2, 'Day 4 - 시각화 기초(Matplotlib) 학습'),
       ('2026-04-05', 2, 'Day 5 - 머신러닝 개념(선형회귀, 비용함수) 정리');

/*******************************************************************************************
 *  Goal #3: "헬스 & 피트니스 집중 챌린지"
 *******************************************************************************************/

-- 1) goal 테이블에 Goal 3 (id=3) 삽입
INSERT INTO goal (id,
                  discount_price,
                  end_date,
                  free_participants_limit,
                  participants_limit,
                  period,
                  price,
                  start_date,
                  created_at,
                  updated_at,
                  description,
                  name,
                  topic,
                  goal_status)
VALUES (3,
        1500, -- 할인 가격
        '2026-03-31', -- 목표 종료일
        15, -- 무료 참여 가능 인원
        150, -- 전체 참여 제한 인원
        60, -- 기간(60일)
        5000, -- 원 가격
        '2026-02-01', -- 목표 시작일
        NOW(), -- 생성 시각
        NOW(), -- 업데이트 시각
        '2개월 동안 식단·운동·생활습관을 균형 있게 개선하는 피트니스 도전 프로그램입니다.',
        '헬스 & 피트니스 집중 챌린지',
        '건강/운동',
        'NOT_STARTED');

-- 2) goal_content_image 테이블 (goal_id=3)
INSERT INTO goal_content_image (goal_id, image_url)
VALUES (3, 'https://cdn.healthfit.com/images/fitness_01.jpg'),
       (3, 'https://cdn.healthfit.com/images/fitness_02.jpg'),
       (3, 'https://cdn.healthfit.com/images/fitness_03.jpg'),
       (3, 'https://cdn.healthfit.com/images/fitness_04.jpg'),
       (3, 'https://cdn.healthfit.com/images/fitness_05.jpg');

-- 3) goal_thumbnail_image 테이블 (goal_id=3)
INSERT INTO goal_thumbnail_image (goal_id, image_url)
VALUES (3, 'https://cdn.healthfit.com/thumbnails/fitness_thumb_01.jpg'),
       (3, 'https://cdn.healthfit.com/thumbnails/fitness_thumb_02.jpg'),
       (3, 'https://cdn.healthfit.com/thumbnails/fitness_thumb_03.jpg'),
       (3, 'https://cdn.healthfit.com/thumbnails/fitness_thumb_04.jpg'),
       (3, 'https://cdn.healthfit.com/thumbnails/fitness_thumb_05.jpg');

-- 4) goal_mid_objective 테이블 (goal_id=3)
INSERT INTO goal_mid_objective (goal_id, description)
VALUES (3, '첫 15일: 기초 체력 측정 및 개인별 맞춤 식단 설정'),
       (3, '중간 30일: 주 3회 이상 근력 운동, 유산소 병행'),
       (3, '중간 45일: 체중 3kg 감량 + 체지방률 2%P 감소 목표'),
       (3, '중간 50일: 생활습관(수면, 스트레스) 점검 및 개선 계획'),
       (3, '마지막 10일: 개인별 피트니스 루틴 완성 & 유지 전략 수립');

-- 5) goal_weekly_objective 테이블 (goal_id=3)
INSERT INTO goal_weekly_objective (week_number, goal_id, description)
VALUES (1, 3, '1주차: 체중/체성분 측정 및 기초 체력 평가'),
       (2, 3, '2주차: 근력 운동 루틴(Basic) 시작, 식단관리 앱 기록'),
       (3, 3, '3주차: 유산소 운동(러닝, 사이클 등) 주 3회 이상 수행'),
       (4, 3, '4주차: 중간 점검 후 근육량/체지방률 비교 분석'),
       (5, 3, '5주차: 생활습관(수면 패턴, 스트레스 관리) 개선 목표 설정');

-- 6) goal_daily_todo 테이블 (goal_id=3)
INSERT INTO goal_daily_todo (todo_date, goal_id, description)
VALUES ('2026-02-01', 3, 'Day 1 - 헬스장 기초 체력 테스트 & 인바디 측정'),
       ('2026-02-02', 3, 'Day 2 - 트레이너 상담 후 맞춤 운동 계획 작성'),
       ('2026-02-03', 3, 'Day 3 - 식단관리 앱 설치 및 당일 식단 기록'),
       ('2026-02-04', 3, 'Day 4 - 상체 근력 운동(푸시업, 덤벨) 30분'),
       ('2026-02-05', 3, 'Day 5 - 유산소 러닝 5km, 스트레칭 15분');
