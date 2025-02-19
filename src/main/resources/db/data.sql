-- 멘토 1: Java Bootcamp 담당 멘토 (mentor_id = 1)
INSERT INTO mentor (created_at, updated_at, email, name, role)
VALUES ('2025-02-14 09:00:00', '2025-02-14 09:00:00', 'mentor1@example.com', 'John Doe', 'ROLE_MENTOR');

-- 멘토 2: Photography Basics 담당 멘토 (mentor_id = 2)
INSERT INTO mentor (created_at, updated_at, email, name, role)
VALUES ('2025-02-13 14:00:00', '2025-02-13 14:00:00', 'mentor2@example.com', 'Jane Smith', 'ROLE_MENTOR');

-- 1. Goal 테이블에 데이터 삽입 (Java Bootcamp)
INSERT INTO goal
(current_participants, discount_price, participants_limit, period, daily_duration, price, created_at, updated_at,
 mentor_id, description, title, topic, goal_status)
VALUES (0, 10000, 20, 30, 30, 50000, '2025-02-14 10:00:00', '2025-02-14 10:00:00', 1,
        'A comprehensive Java bootcamp for beginners.', 'Java Bootcamp', 'Programming', 'OPEN');

-- 삽입된 Goal의 ID를 변수에 저장
SET @goal1_id = LAST_INSERT_ID();

-- 2. Goal_Content_Image 테이블에 데이터 삽입 (랜덤 seed: 101, 102)
INSERT INTO goal_content_image (goal_id, sequence, image_url)
VALUES (@goal1_id, 1, 'https://picsum.photos/seed/101/156/214');
INSERT INTO goal_content_image (goal_id, sequence, image_url)
VALUES (@goal1_id, 2, 'https://picsum.photos/seed/102/156/214');

-- 3. Goal_Thumbnail_Image 테이블에 데이터 삽입 (랜덤 seed: 103)
-- INSERT INTO goal_thumbnail_image (goal_id, sequence, image_url)
-- VALUES (@goal1_id, 1, 'https://picsum.photos/seed/103/156/214');
-- INSERT INTO goal_thumbnail_image (goal_id, sequence, image_url)
-- VALUES (@goal1_id, 2, 'https://picsum.photos/seed/104/156/214');


-- 4. Goal_Daily_Todo 테이블에 데이터 삽입
INSERT INTO goal_daily_todo (estimated_minutes, day_number, goal_id, description, mentor_tip)
VALUES (60, 0, @goal1_id, 'Introduction to Java and setup environment', 'Install JDK and configure your IDE.'),
       (60, 0, @goal1_id, 'More Java and setup environment', 'Install JDK and configure your IDE.'),
       (90, 1, @goal1_id, 'Learn Java syntax and basic programming concepts',
        'Practice coding exercises and review Java documentation.'),
       (75, 2, @goal1_id, 'Explore Java data types and control structures',
        'Write code snippets to understand loops and conditional statements.'),
       (120, 2, @goal1_id, 'Create a simple Java application',
        'Build a console-based program to apply your knowledge.'),
       (90, 3, @goal1_id, 'Understand object-oriented programming principles in Java',
        'Implement classes and objects in your projects.'),
       (120, 3, @goal1_id, 'Develop a Java project with multiple classes',
        'Design a program with inheritance and polymorphism.'),
       (90, 4, @goal1_id, 'Learn Java exception handling and file I/O',
        'Practice error handling and read/write operations in Java.'),
       (120, 4, @goal1_id, 'Complete a Java project with a GUI',
        'Create a graphical user interface for your application.'),
       (90, 5, @goal1_id, 'Review your Java projects and seek feedback',
        'Share your code with peers and mentors for review.'),
       (120, 5, @goal1_id, 'Reflect on your learning journey and set new goals',
        'Identify areas for improvement and set new challenges.'),
       (90, 6, @goal1_id, 'Review your Java projects and seek feedback',
        'Share your code with peers and mentors for review.'),
       (120, 6, @goal1_id, 'Reflect on your learning journey and set new goals',
        'Identify areas for improvement and set new challenges.'),
       (90, 7, @goal1_id, 'Review your Java projects and seek feedback',
        'Share your code with peers and mentors for review.'),
       (120, 7, @goal1_id, 'Reflect on your learning journey and set new goals',
        'Identify areas for improvement and set new challenges.'),
       (90, 8, @goal1_id, 'Review your Java projects and seek feedback',
        'Share your code with peers and mentors for review.'),
       (120, 8, @goal1_id, 'Reflect on your learning journey and set new goals',
        'Identify areas for improvement and set new challenges.'),
       (90, 9, @goal1_id, 'Review your Java projects and seek feedback',
        'Share your code with peers and mentors for review.'),
       (120, 9, @goal1_id, 'Reflect on your learning journey and set new goals',
        'Identify areas for improvement and set new challenges.'),
       (90, 10, @goal1_id, 'Review your Java projects and seek feedback',
        'Share your code with peers and mentors for review.'),
       (120, 10, @goal1_id, 'Reflect on your learning journey and set new goals',
        'Identify areas for improvement and set new challenges.');

-- 5. Goal_Mid_Objective 테이블에 데이터 삽입
INSERT INTO goal_mid_objective (goal_id, sequence, description)
VALUES (@goal1_id, 1, 'Complete the Java fundamentals module.'),
       (@goal1_id, 2, 'Develop a simple Java application.');

-- 6. Goal_Weekly_Objective 테이블에 데이터 삽입
INSERT INTO goal_weekly_objective (week_number, goal_id, description)
VALUES (1, @goal1_id, 'Learn Java syntax and core libraries.'),
       (2, @goal1_id, 'Build and deploy a small Java application.'),
       (3, @goal1_id, 'Create a Java project with multiple classes.'),
       (4, @goal1_id, 'Implement inheritance and polymorphism in Java.'),
       (5, @goal1_id, 'Understand Java exception handling and file I/O.'),
       (6, @goal1_id, 'Complete a Java project with a GUI.');

-- 1. Goal 테이블에 데이터 삽입 (Photography Basics)
INSERT INTO goal
(current_participants, discount_price, participants_limit, period, daily_duration, price, created_at, updated_at,
 mentor_id, description, title, topic, goal_status)
VALUES (5, 5000, 15, 60, 20, 75000, '2025-02-13 15:30:00', '2025-02-13 15:30:00', 2,
        'Learn the fundamentals of photography and enhance your skills.', 'Photography Basics', 'Art', 'UPCOMING');

-- 삽입된 Goal의 ID를 변수에 저장
SET @goal2_id = LAST_INSERT_ID();

-- 2. Goal_Content_Image 테이블에 데이터 삽입 (랜덤 seed: 201, 202)
INSERT INTO goal_content_image (goal_id, sequence, image_url)
VALUES (@goal2_id, 1, 'https://picsum.photos/seed/99/156/214');
INSERT INTO goal_content_image (goal_id, sequence, image_url)
VALUES (@goal2_id, 2, 'https://picsum.photos/seed/902/156/214');

-- 3. Goal_Thumbnail_Image 테이블에 데이터 삽입 (랜덤 seed: 103)
INSERT INTO goal_thumbnail_image (goal_id, sequence, image_url)
VALUES (@goal2_id, 1, 'https://picsum.photos/seed/11/156/214');
INSERT INTO goal_thumbnail_image (goal_id, sequence, image_url)
VALUES (@goal2_id, 2, 'https://picsum.photos/seed/14/156/214');

-- 4. Goal_Daily_Todo 테이블에 데이터 삽입
INSERT INTO goal_daily_todo (estimated_minutes, day_number, goal_id, description, mentor_tip)
VALUES (30, 0, @goal2_id, 'Introduction to photography basics and camera types',
        'Research different camera models and their features.'),
       (45, 0, @goal2_id, 'Understanding exposure: aperture, shutter speed, and ISO',
        'Experiment with different settings in manual mode.'),
       (60, 1, @goal2_id, 'Introduction to camera settings: aperture, shutter speed, and ISO',
        'Study your camera manual for detailed info.'),
       (60, 1, @goal2_id, 'Practice basic composition and framing techniques',
        'Apply the rule of thirds in your shots.'),
       (90, 2, @goal2_id, 'Learn about natural lighting and its effects on photography',
        'Experiment with different lighting conditions.'),
       (75, 2, @goal2_id, 'Explore photo composition and visual storytelling',
        'Capture images that tell a story or evoke emotions.'),
       (120, 3, @goal2_id, 'Understand the principles of exposure and metering',
        'Practice exposure compensation and metering modes.'),
       (90, 3, @goal2_id, 'Develop skills in photo editing and post-processing',
        'Experiment with editing software and techniques.'),
       (120, 4, @goal2_id, 'Learn to compose visually appealing photographs',
        'Experiment with different angles and perspectives.'),
       (90, 4, @goal2_id, 'Explore advanced techniques in portrait and landscape photography',
        'Experiment with different lighting setups and compositions.'),
       (120, 5, @goal2_id, 'Create a photography portfolio and share your work',
        'Select your best images and create an online portfolio.'),
       (90, 5, @goal2_id, 'Review your progress and set new photography goals',
        'Reflect on your learning journey and set new challenges.');

-- 5. Goal_Mid_Objective 테이블에 데이터 삽입
INSERT INTO goal_mid_objective (goal_id, sequence, description)
VALUES (@goal2_id, 1, 'Master manual camera controls.'),
       (@goal2_id, 2, 'Understand the principles of exposure.'),
       (@goal2_id, 3, 'Learn to compose visually appealing photographs.');

-- 6. Goal_Weekly_Objective 테이블에 데이터 삽입
INSERT INTO goal_weekly_objective (week_number, goal_id, description)
VALUES (1, @goal2_id, 'Familiarize yourself with camera functions and settings.'),
       (2, @goal2_id, 'Develop skills in photo composition and natural lighting.'),
       (3, @goal2_id, 'Understand the basics of exposure and metering.'),
       (4, @goal2_id, 'Learn to capture motion and still life photography.'),
       (5, @goal2_id, 'Explore advanced techniques in portrait and landscape photography.'),
       (6, @goal2_id, 'Create a photography portfolio and share your work.'),
       (7, @goal2_id, 'Review your progress and set new photography goals.');
