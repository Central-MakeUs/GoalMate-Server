INSERT INTO mentor (created_at, id, updated_at, email, name, role)
VALUES ('2025-01-01 09:00:00', 1, '2025-01-05 10:00:00', 'alice@domain.com', 'Alice', 'ROLE_MENTOR'),
       ('2025-01-01 09:30:00', 2, '2025-01-05 10:30:00', 'bob@domain.com', 'Bob', 'ROLE_MENTOR'),
       ('2025-01-01 10:00:00', 3, '2025-01-06 11:00:00', 'charlie@domain.com', 'Charlie', 'ROLE_MENTOR'),
       ('2025-01-01 11:00:00', 4, '2025-01-07 09:15:00', 'david@domain.com', 'David', 'ROLE_ADMIN'),
       ('2025-01-01 12:00:00', 5, '2025-01-07 09:20:00', 'eve@domain.com', 'Eve', 'ROLE_MENTOR');

-- (2) goal 테이블: 5건 (mentor_id = 1~5)
INSERT INTO goal (discount_price, end_date, free_participants_limit, participants_limit,
                  period, price, start_date, created_at, id, mentor_id, updated_at,
                  description, name, topic, goal_status)
VALUES (5000, '2025-02-01', 5, 50, 30, 10000, '2025-01-15', '2025-01-01 09:00:00', 1, 1, '2025-01-05 10:10:00',
        '개발 입문자를 위한 30일 완주 프로젝트', 'Goal A', 'Backend', 'NOT_STARTED'),
       (3000, '2025-03-01', 3, 30, 14, 8000, '2025-02-15', '2025-01-01 09:30:00', 2, 2, '2025-01-05 10:20:00',
        'AI 기본 이론 습득 및 실습', 'Goal B', 'AI', 'IN_PROGRESS'),
       (0, '2025-04-01', 10, 100, 60, 20000, '2025-02-20', '2025-01-01 10:00:00', 3, 3, '2025-01-06 10:00:00',
        '클라우드 자격증 준비반', 'Goal C', 'Cloud', 'IN_PROGRESS'),
       (1000, '2025-02-10', 2, 20, 7, 6000, '2025-01-10', '2025-01-02 09:00:00', 4, 4, '2025-01-07 09:30:00',
        '단기 알고리즘 집중반', 'Goal D', 'Algorithm', 'COMPLETED'),
       (2000, '2025-05-01', 8, 80, 20, 15000, '2025-03-01', '2025-01-03 09:00:00', 5, 5, '2025-01-07 09:45:00',
        'Web 기초부터 배포까지', 'Goal E', 'Web', 'NOT_STARTED');

-- (3) goal_content_image 테이블: 5건 (goal_id = 1~5)
INSERT INTO goal_content_image (goal_id, id, image_url)
VALUES (1, 1, 'https://example.com/content_image1.jpg'),
       (2, 2, 'https://example.com/content_image2.jpg'),
       (3, 3, 'https://example.com/content_image3.jpg'),
       (4, 4, 'https://example.com/content_image4.jpg'),
       (5, 5, 'https://example.com/content_image5.jpg');

-- (4) goal_daily_todo 테이블: 5건 (goal_id = 1~5)
INSERT INTO goal_daily_todo (status, todo_date, goal_id, id, description)
VALUES (1, '2025-01-16', 1, 1, 'Backend 기본 문법 복습'),
       (0, '2025-01-17', 2, 2, '논문 리뷰 및 주제 조사'),
       (1, '2025-01-18', 3, 3, '클라우드 서비스 개요 학습'),
       (1, '2025-01-19', 4, 4, '알고리즘 문제 풀이 3개 도전'),
       (0, '2025-01-20', 5, 5, '웹 서버 설정 공부');

-- (5) goal_mid_objective 테이블: 5건 (goal_id = 1~5)
INSERT INTO goal_mid_objective (goal_id, id, description)
VALUES (1, 1, '중간 점검: 간단한 REST API 개발'),
       (2, 2, '중간 점검: 기본 AI 모델 테스트'),
       (3, 3, '중간 점검: 클라우드 배포 스크립트 작성'),
       (4, 4, '중간 점검: 알고리즘 최적화 전략 수립'),
       (5, 5, '중간 점검: 프론트엔드-백엔드 연결 테스트');

-- (6) goal_thumbnail_image 테이블: 5건 (goal_id = 1~5)
INSERT INTO goal_thumbnail_image (goal_id, id, image_url)
VALUES (1, 1, 'https://example.com/thumbnail1.png'),
       (2, 2, 'https://example.com/thumbnail2.png'),
       (3, 3, 'https://example.com/thumbnail3.png'),
       (4, 4, 'https://example.com/thumbnail4.png'),
       (5, 5, 'https://example.com/thumbnail5.png');

-- (7) goal_weekly_objective 테이블: 5건 (goal_id = 1~5)
INSERT INTO goal_weekly_objective (week_number, goal_id, id, description)
VALUES (1, 1, 1, '1주차: 개발 환경 설정'),
       (2, 2, 2, '2주차: 기초 AI 모델 실습'),
       (3, 3, 3, '3주차: 클라우드 아키텍처 설계'),
       (4, 4, 4, '4주차: 알고리즘 최적화 기법 학습'),
       (5, 5, 5, '5주차: 웹 프레임워크 적용');

-- (8) mentee 테이블: 5건
INSERT INTO mentee (created_at, free_join_count, id, updated_at, email,
                    name, social_id, provider, role, status)
VALUES ('2025-01-10 09:10:00', 2, 1, '2025-01-11 10:00:00', 'mentee1@example.com',
        'Mentee One', 'apple_111', 'APPLE', 'ROLE_MENTEE', 'ACTIVE'),
       ('2025-01-10 09:15:00', 1, 2, '2025-01-11 10:05:00', 'mentee2@example.com',
        'Mentee Two', 'kakao_222', 'KAKAO', 'ROLE_MENTEE', 'ACTIVE'),
       ('2025-01-10 09:20:00', 3, 3, '2025-01-11 10:10:00', 'mentee3@example.com',
        'Mentee Three', 'apple_333', 'APPLE', 'ROLE_MENTEE', 'DELETED'),
       ('2025-01-10 09:25:00', 5, 4, '2025-01-12 11:00:00', 'admin@example.com',
        'Admin User', 'kakao_444', 'KAKAO', 'ROLE_ADMIN', 'ACTIVE'),
       ('2025-01-10 09:30:00', 0, 5, '2025-01-12 11:05:00', 'mentee5@example.com',
        'Mentee Five', 'apple_555', 'APPLE', 'ROLE_MENTEE', 'PENDING');

-- (9) mentee_goal 테이블: 5건
INSERT INTO mentee_goal (created_at, goal_id, id, joined_at, mentee_id, updated_at,
                         final_comment, status)
VALUES ('2025-01-15 08:00:00', 1, 1, '2025-01-15 09:00:00', 1, '2025-01-16 09:00:00',
        'Backend 입문 화이팅!', 'IN_PROGRESS'),
       ('2025-01-16 08:00:00', 2, 2, '2025-01-16 09:00:00', 2, '2025-01-17 09:00:00',
        'AI 기초 다지기 중', 'IN_PROGRESS'),
       ('2025-01-17 08:00:00', 3, 3, '2025-01-17 09:00:00', 1, '2025-01-18 10:00:00',
        '클라우드 너무 재밌어요', 'IN_PROGRESS'),
       ('2025-01-18 08:00:00', 4, 4, '2025-01-18 09:00:00', 3, '2025-01-19 11:00:00',
        '알고리즘 마무리!', 'COMPLETED'),
       ('2025-01-19 08:00:00', 5, 5, '2025-01-19 09:00:00', 2, '2025-01-20 12:00:00',
        '웹 개발 도전 중', 'IN_PROGRESS');
