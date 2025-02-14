-- 멘토 1: Java Bootcamp 담당 멘토 (mentor_id = 1)
INSERT INTO mentor (created_at, updated_at, email, name, role)
VALUES ('2025-02-14 09:00:00', '2025-02-14 09:00:00', 'mentor1@example.com', 'John Doe', 'ROLE_MENTOR');

-- 멘토 2: Photography Basics 담당 멘토 (mentor_id = 2)
INSERT INTO mentor (created_at, updated_at, email, name, role)
VALUES ('2025-02-13 14:00:00', '2025-02-13 14:00:00', 'mentor2@example.com', 'Jane Smith', 'ROLE_MENTOR');

-- 1. Goal 테이블에 데이터 삽입 (Java Bootcamp)
INSERT INTO goal
(current_participants, discount_price, participants_limit, period, daily_duration, price, created_at, updated_at, mentor_id, description, title, topic, goal_status)
VALUES
    (0, 10000, 20, 30, 30, 50000, '2025-02-14 10:00:00', '2025-02-14 10:00:00', 1, 'A comprehensive Java bootcamp for beginners.', 'Java Bootcamp', 'Programming', 'IN_PROGRESS');

-- 삽입된 Goal의 ID를 변수에 저장
SET @goal1_id = LAST_INSERT_ID();

-- 2. Goal_Content_Image 테이블에 데이터 삽입 (랜덤 seed: 101, 102)
INSERT INTO goal_content_image (goal_id, image_url)
VALUES (@goal1_id, 'https://picsum.photos/seed/101/156/214');
INSERT INTO goal_content_image (goal_id, image_url)
VALUES (@goal1_id, 'https://picsum.photos/seed/102/156/214');

-- 3. Goal_Thumbnail_Image 테이블에 데이터 삽입 (랜덤 seed: 103)
INSERT INTO goal_thumbnail_image (goal_id, image_url)
VALUES (@goal1_id, 'https://picsum.photos/seed/103/156/214');

-- 4. Goal_Daily_Todo 테이블에 데이터 삽입
INSERT INTO goal_daily_todo (estimated_minutes, todo_date, goal_id, description, mentor_tip)
VALUES (60, '2025-02-15', @goal1_id, 'Introduction to Java and setup environment', 'Install JDK and configure your IDE.');
INSERT INTO goal_daily_todo (estimated_minutes, todo_date, goal_id, description, mentor_tip)
VALUES (90, '2025-02-16', @goal1_id, 'Dive into OOP: Classes, Objects, and Inheritance', 'Review class structures and practice coding exercises.');

-- 5. Goal_Mid_Objective 테이블에 데이터 삽입
INSERT INTO goal_mid_objective (goal_id, description)
VALUES (@goal1_id, 'Complete the Java fundamentals module.');

-- 6. Goal_Weekly_Objective 테이블에 데이터 삽입
INSERT INTO goal_weekly_objective (week_number, goal_id, description)
VALUES (1, @goal1_id, 'Learn Java syntax and core libraries.');
INSERT INTO goal_weekly_objective (week_number, goal_id, description)
VALUES (2, @goal1_id, 'Build and deploy a small Java application.');

-- 1. Goal 테이블에 데이터 삽입 (Photography Basics)
INSERT INTO goal
(current_participants, discount_price, participants_limit, period, daily_duration, price, created_at, updated_at, mentor_id, description, title, topic, goal_status)
VALUES
    (5, 5000, 15, 60, 20,75000, '2025-02-13 15:30:00', '2025-02-13 15:30:00', 2, 'Learn the fundamentals of photography and enhance your skills.', 'Photography Basics', 'Art', 'NOT_STARTED');

-- 삽입된 Goal의 ID를 변수에 저장
SET @goal2_id = LAST_INSERT_ID();

-- 2. Goal_Content_Image 테이블에 데이터 삽입 (랜덤 seed: 201, 202)
INSERT INTO goal_content_image (goal_id, image_url)
VALUES (@goal2_id, 'https://picsum.photos/seed/201/156/214');
INSERT INTO goal_content_image (goal_id, image_url)
VALUES (@goal2_id, 'https://picsum.photos/seed/202/156/214');

-- 3. Goal_Thumbnail_Image 테이블에 데이터 삽입 (랜덤 seed: 203)
INSERT INTO goal_thumbnail_image (goal_id, image_url)
VALUES (@goal2_id, 'https://picsum.photos/seed/203/156/214');

-- 4. Goal_Daily_Todo 테이블에 데이터 삽입
INSERT INTO goal_daily_todo (estimated_minutes, todo_date, goal_id, description, mentor_tip)
VALUES (45, '2025-02-15', @goal2_id, 'Introduction to camera settings: aperture, shutter speed, and ISO', 'Study your camera manual for detailed info.');
INSERT INTO goal_daily_todo (estimated_minutes, todo_date, goal_id, description, mentor_tip)
VALUES (60, '2025-02-16', @goal2_id, 'Practice basic composition and framing techniques', 'Apply the rule of thirds in your shots.');

-- 5. Goal_Mid_Objective 테이블에 데이터 삽입
INSERT INTO goal_mid_objective (goal_id, description)
VALUES (@goal2_id, 'Master manual camera controls.');

-- 6. Goal_Weekly_Objective 테이블에 데이터 삽입
INSERT INTO goal_weekly_objective (week_number, goal_id, description)
VALUES (1, @goal2_id, 'Familiarize yourself with camera functions and settings.');
INSERT INTO goal_weekly_objective (week_number, goal_id, description)
VALUES (2, @goal2_id, 'Develop skills in photo composition and natural lighting.');
