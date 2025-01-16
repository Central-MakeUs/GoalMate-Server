-- 1) goalEntity 테이블 (3개 예시)
INSERT INTO goalEntity (discount_price,
                        free_participants_limit,
                        participants_limit,
                        period,
                        price,
                        created_at,
                        end_date,
                        start_date,
                        updated_at,
                        description,
                        name,
                        topic,
                        goal_status)
VALUES (70000, 30, 100, 30, 100000, '2025-01-01 10:00:00', '2025-01-31 23:59:59', '2025-01-01 00:00:00',
        '2025-01-01 11:00:00',
        '하루에 커밋하기', '1일 1커밋 챌린지', '코딩', 'IN_PROGRESS'),
       (50000, 10, 20, 14, 50000, '2025-01-05 09:30:00', '2025-01-19 23:59:59', '2025-01-05 00:00:00',
        '2025-01-05 09:30:00',
        '매일 영어 단어 암기', '2주 영어단어 챌린지', '영어', 'NOT_STARTED'),
       (80000, 50, 200, 60, 120000, '2025-02-01 10:00:00', '2025-04-01 23:59:59', '2025-02-01 00:00:00',
        '2025-02-01 11:00:00',
        '꾸준히 운동하기', '헬스 챌린지', '운동', 'COMPLETED');

-- 2) goal_content_image 테이블 (2개 예시)
INSERT INTO goal_content_image (goal_id,
                                image_url)
VALUES (1, 'https://example.com/content_image1.jpg'),
       (1, 'https://example.com/content_image2.jpg');

-- 3) goal_daily_todo 테이블 (2개 예시)
INSERT INTO goal_daily_todo (goal_id,
                             todo_date,
                             description)
VALUES (1, '2025-01-02 00:00:00', '하루에 최소 한 번 커밋하기'),
       (2, '2025-01-06 00:00:00', '새로운 영어 단어 10개 암기');

-- 4) goal_mid_objective 테이블 (1개 예시)
INSERT INTO goal_mid_objective (goal_id,
                                description)
VALUES (1, '기본적인 Git/GitHub 사용법 숙지');

-- 5) goal_thunbnail_image 테이블 (1개 예시)
INSERT INTO goal_thunbnail_image (goal_id,
                                  image_url)
VALUES (2, 'https://example.com/thumbnail2.jpg');

-- 6) goal_weekly_objective 테이블 (1개 예시)
INSERT INTO goal_weekly_objective (week_number,
                                   goal_id,
                                   description)
VALUES (1, 1, '깃허브 레포 생성 및 초기 세팅');

-- 7) mentee 테이블 (2개 예시)
INSERT INTO mentee (free_join_count,
                    email,
                    name,
                    social_id,
                    provider,
                    role,
                    status,
                    created_at,
                    updated_at)
VALUES (2, 'user1@example.com', '홍길동', 'socialId1', 'KAKAO', 'USER', 'ACTIVE', '2025-01-01 00:00:00', NULL),
       (3, 'user2@example.com', '김철수', 'socialId2', 'APPLE', 'ADMIN', 'PENDING', '2025-01-02 00:00:00',
        '2025-01-03 12:34:56');
