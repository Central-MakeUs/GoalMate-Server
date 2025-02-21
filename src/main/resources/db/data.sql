-- 어드민
INSERT INTO admin (created_at, updated_at, login_id, password, role)
VALUES ('2025-02-14 09:00:00', '2025-02-14 09:00:00', 'admin',
        '$2a$10$1i.h33et9oKl/R9jjz9GV.JdROMZHOH.cWge4QtXdXSaCxhkmQpYW', 'ROLE_ADMIN');

-- 멘토 1: Java Bootcamp 담당 멘토 (mentor_id = 1)
INSERT INTO mentor (created_at, updated_at, email, name, password, role)
VALUES ('2025-02-14 09:00:00', '2025-02-14 09:00:00', 'mentor1@example.com', 'John Doe',
        '$2a$10$rXFCPrq71rGm2YhXAAPOF.3Pw/S9cHMFkh8HpMrbCutOKut5lBfry', 'ROLE_MENTOR'),
       ('2025-02-13 14:00:00', '2025-02-13 14:00:00', 'mentor2@example.com', 'Jane Smith',
        '$2a$10$doQL4j/bMbSx3e6d07Akte3KLauOUtAh8G1tXvrRBCWWw2OmQR3nO', 'ROLE_MENTOR');

-- 1. Goal 테이블에 데이터 삽입 (Java Bootcamp)
INSERT INTO goal
(current_participants, discount_price, participants_limit, period, daily_duration, price, created_at, updated_at,
 mentor_id, description, title, topic, goal_status)
VALUES (7, 10000, 10, 30, 30, 50000, '2025-02-14 10:00:00', '2025-02-14 10:00:00', 1,
        'A comprehensive Java bootcamp for beginners.', 'Java Bootcamp', 'Programming', 'OPEN');

-- 삽입된 Goal의 ID를 변수에 저장
SET @goal1_id = LAST_INSERT_ID();

-- 2. Goal_Content_Image 테이블에 데이터 삽입 (랜덤 seed: 101, 102)
INSERT INTO goal_content_image (goal_id, sequence, image_url)
VALUES (@goal1_id, 1, 'https://picsum.photos/seed/101/156/214'),
       (@goal1_id, 2, 'https://picsum.photos/seed/102/156/214');

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
        'Learn the fundamentals of photography and enhance your skills.', 'Photography Basics', 'Art', 'OPEN');

-- 삽입된 Goal의 ID를 변수에 저장
SET @goal2_id = LAST_INSERT_ID();

-- 2. Goal_Content_Image 테이블에 데이터 삽입 (랜덤 seed: 201, 202)
INSERT INTO goal_content_image (goal_id, sequence, image_url)
VALUES (@goal2_id, 1, 'https://picsum.photos/seed/99/156/214'),
       (@goal2_id, 2, 'https://picsum.photos/seed/902/156/214');

-- 3. Goal_Thumbnail_Image 테이블에 데이터 삽입 (랜덤 seed: 103)
INSERT INTO goal_thumbnail_image (goal_id, sequence, image_url)
VALUES (@goal2_id, 1, 'https://picsum.photos/seed/11/156/214'),
       (@goal2_id, 2, 'https://picsum.photos/seed/14/156/214');

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

-- 1. Goal 테이블에 데이터 삽입 (Web Development Bootcamp)
INSERT INTO goal (current_participants, discount_price, participants_limit, period, daily_duration, price, created_at,
                  updated_at, mentor_id, description, title, topic, goal_status)
VALUES (10, 15000, 25, 45, 40, 100000, '2025-02-13 16:00:00', '2025-02-13 16:00:00', 1,
        'Master the fundamentals of web development and build dynamic websites.', 'Web Development Bootcamp',
        'Programming', 'CLOSED');

-- 삽입된 Goal의 ID를 변수에 저장
SET @goal3_id = LAST_INSERT_ID();

-- 2. Goal_Content_Image 테이블에 데이터 삽입 (랜덤 seed: 301, 302)
INSERT INTO goal_content_image (goal_id, sequence, image_url)
VALUES (@goal3_id, 1, 'https://picsum.photos/seed/301/156/214'),
       (@goal3_id, 2, 'https://picsum.photos/seed/302/156/214');

-- 3. Goal_Thumbnail_Image 테이블에 데이터 삽입 (랜덤 seed: 303)
INSERT INTO goal_thumbnail_image (goal_id, sequence, image_url)
VALUES (@goal3_id, 1, 'https://picsum.photos/seed/303/156/214'),
       (@goal3_id, 2, 'https://picsum.photos/seed/304/156/214');

-- 4. Goal_Daily_Todo 테이블에 데이터 삽입
INSERT INTO goal_daily_todo (estimated_minutes, day_number, goal_id, description, mentor_tip)
VALUES (60, 0, @goal3_id, 'Introduction to web development and setup environment',
        'Install code editor and browser developer tools.'),
       (60, 0, @goal3_id, 'More web development and setup environment',
        'Install code editor and browser developer tools.'),
       (90, 1, @goal3_id, 'Learn HTML and create a basic webpage',
        'Practice writing HTML tags and elements.'),
       (75, 2, @goal3_id, 'Explore CSS and style your webpage',
        'Experiment with CSS properties and selectors.'),
       (120, 2, @goal3_id, 'Build a responsive webpage with CSS',
        'Design a mobile-friendly layout using media queries.'),
       (90, 3, @goal3_id, 'Understand JavaScript fundamentals and DOM manipulation',
        'Practice writing JavaScript code and interacting with the DOM.'),
       (120, 3, @goal3_id, 'Develop interactive features with JavaScript',
        'Create dynamic content and user interactions with JavaScript.'),
       (90, 4, @goal3_id, 'Learn about web development frameworks and libraries',
        'Explore popular frameworks like React and Angular.'),
       (120, 4, @goal3_id, 'Build a single-page application with React',
        'Create a modern web app using React components and state management.'),
       (90, 5, @goal3_id, 'Review your web projects and seek feedback',
        'Share your code with peers and mentors for review.'),
       (120, 5, @goal3_id, 'Reflect on your learning journey and set new goals',
        'Identify areas for improvement and set new challenges.');

-- 5. Goal_Mid_Objective 테이블에 데이터 삽입
INSERT INTO goal_mid_objective (goal_id, sequence, description)
VALUES (@goal3_id, 1, 'Master HTML and CSS for web design.'),
       (@goal3_id, 2, 'Understand JavaScript fundamentals and DOM manipulation.');

-- 6. Goal_Weekly_Objective 테이블에 데이터 삽입
INSERT INTO goal_weekly_objective (week_number, goal_id, description)
VALUES (1, @goal3_id, 'Learn HTML and create a basic webpage.'),
       (2, @goal3_id, 'Explore CSS and style your webpage.'),
       (3, @goal3_id, 'Understand JavaScript fundamentals and DOM manipulation.'),
       (4, @goal3_id, 'Learn about web development frameworks and libraries.'),
       (5, @goal3_id, 'Review your web projects and seek feedback.'),
       (6, @goal3_id, 'Reflect on your learning journey and set new goals.');
