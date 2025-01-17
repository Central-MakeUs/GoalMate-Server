-- 트랜잭션 시작 (선택 사항)
START TRANSACTION;

-- 1. Mentor 생성
INSERT INTO mentor (created_at,
                    updated_at,
                    email,
                    name,
                    role)
VALUES ('2025-01-17 09:00:00',
        '2025-01-17 09:00:00',
        'mentor1@example.com',
        '홍길동',
        'ROLE_MENTOR');

-- 2. Goal 생성 (mentor_id는 위에서 생성된 멘토의 id라고 가정해 1번 사용)
INSERT INTO goal (discount_price,
                  end_date,
                  free_participants_limit,
                  participants_limit,
                  period,
                  price,
                  start_date,
                  created_at,
                  updated_at,
                  mentor_id,
                  description,
                  title,
                  topic,
                  goal_status)
VALUES (1000,
        '2025-02-01',
        10,
        50,
        30,
        2000,
        '2025-01-17',
        '2025-01-17 09:10:00',
        '2025-01-17 09:10:00',
        1, -- mentor_id
        '이 목표는 자바를 마스터하기 위한 목표입니다.',
        '자바 마스터 스터디',
        'Java Programming',
        'IN_PROGRESS'),
       (500,
        '2025-03-15',
        5,
        20,
        60,
        1000,
        '2025-01-20',
        '2025-01-20 09:00:00',
        '2025-01-20 09:00:00',
        1, -- 같은 mentor_id
        '자료구조 기초를 배우고 실습하는 목표입니다.',
        '자료구조 기초',
        'Data Structures',
        'NOT_STARTED');

-- 3. Goal 관련 이미지 (goal_content_image)
--    goal_id=1,2 에 예시로 2~3개 이미지 넣음
INSERT INTO goal_content_image (goal_id,
                                image_url)
VALUES (1, 'https://example.com/images/goal1_img1.png'),
       (1, 'https://example.com/images/goal1_img2.png'),
       (2, 'https://example.com/images/goal2_img1.png');

-- 4. Goal Thumbnail 이미지 (goal_thumbnail_image)
INSERT INTO goal_thumbnail_image (goal_id,
                                  image_url)
VALUES (1, 'https://example.com/images/goal1_thumb.png'),
       (2, 'https://example.com/images/goal2_thumb.png');

-- 5. Goal Mid Objective (goal_mid_objective)
INSERT INTO goal_mid_objective (goal_id,
                                description)
VALUES (1, '자바 기초 문법 숙달'),
       (1, '객체 지향 프로그래밍 이해'),
       (2, '배열과 리스트 이해');

-- 6. Goal Weekly Objective (goal_weekly_objective)
INSERT INTO goal_weekly_objective (week_number,
                                   goal_id,
                                   description)
VALUES (1, 1, '입문서 읽기'),
       (2, 1, '실습 프로젝트 시작'),
       (1, 2, '배열 다루기 실습');

-- 7. Goal Daily Todo (goal_daily_todo)
INSERT INTO goal_daily_todo (todo_date,
                             goal_id,
                             description)
VALUES ('2025-01-18', 1, '챕터 1 읽기'),
       ('2025-01-19', 1, '챕터 1 복습 & 문제 풀기'),
       ('2025-01-21', 2, '기초 개념 정리');

-- 8. Mentee Goal (mentee_goal)
--    mentee_id는 이미 1번 존재한다고 가정
--    status는 'CANCELLED','COMPLETED','IN_PROGRESS' 중 택1
INSERT INTO mentee_goal (created_at,
                         goal_id,
                         joined_at,
                         mentee_id,
                         updated_at,
                         final_comment,
                         status)
VALUES ('2025-01-17 09:15:00',
        1,
        '2025-01-17 09:15:00',
        1, -- 기존 mentee
        '2025-01-17 09:15:00',
        '열심히 해보겠습니다.',
        'IN_PROGRESS'),
       ('2025-01-20 09:15:00',
        2,
        '2025-01-20 09:15:00',
        1, -- 기존 mentee
        '2025-01-20 09:15:00',
        '사정이 생겨서 취소합니다.',
        'CANCELLED');

-- 9. Mentee Goal Daily Comment (mentee_goal_daily_comment)
--    mentee_goal_id는 위에서 생성된 auto_increment 값을 사용
--    여기서는 첫 번째 INSERT가 id=1, 두 번째가 id=2가 된다고 가정
INSERT INTO mentee_goal_daily_comment (comment_date,
                                       created_at,
                                       mentee_goal_id,
                                       updated_at,
                                       comment)
VALUES ('2025-01-18',
        '2025-01-18 20:00:00',
        1, -- mentee_goal의 첫 번째 레코드
        '2025-01-18 20:10:00',
        '오늘은 챕터 1을 읽었습니다.'),
       ('2025-01-19',
        '2025-01-19 21:00:00',
        1, -- 동일 mentee_goal_id=1
        '2025-01-19 21:10:00',
        '챕터 1 복습 완료!');

-- 10. Mentee Goal Daily Todo (mentee_goal_daily_todo)
INSERT INTO mentee_goal_daily_todo (todo_date,
                                    completed_at,
                                    created_at,
                                    mentee_goal_id,
                                    updated_at,
                                    description,
                                    status)
VALUES ('2025-01-18',
        '2025-01-18 20:00:00',
        '2025-01-18 09:00:00',
        1, -- mentee_goal의 id=1
        '2025-01-18 20:00:00',
        '챕터 1 읽기',
        'DONE'),
       ('2025-01-19',
        NULL,
        '2025-01-19 09:00:00',
        1, -- mentee_goal의 id=1
        '2025-01-19 09:10:00',
        '챕터 1 복습 & 문제 풀기',
        'TODO');

