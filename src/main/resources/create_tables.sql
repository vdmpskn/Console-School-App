CREATE TABLE IF NOT EXISTS groups (
                                      group_id SERIAL PRIMARY KEY,
                                      group_name VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS students (
                                        student_id SERIAL PRIMARY KEY,
                                        group_id INT,
                                        first_name VARCHAR(100),
    last_name VARCHAR(100),
    FOREIGN KEY (group_id) REFERENCES groups (group_id)
    );

CREATE TABLE IF NOT EXISTS courses (
                                       course_id SERIAL PRIMARY KEY,
                                       course_name VARCHAR(100),
    course_description VARCHAR(255)
    );