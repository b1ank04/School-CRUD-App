CREATE TABLE IF NOT EXISTS groups(
                                     id SERIAL PRIMARY KEY not null,
                                     name VARCHAR(5)
);

CREATE TABLE IF NOT EXISTS students(
                                       id SERIAL PRIMARY KEY,
                                       group_id INT,
                                       first_name VARCHAR(20),
                                       last_name VARCHAR(20),
                                       FOREIGN KEY(group_id) references groups(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS courses(
                                      id SERIAL PRIMARY KEY,
                                      name VARCHAR(20),
                                      description VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS student_course(
                                             student_id INT,
                                             course_id INT,
                                             FOREIGN KEY (student_id) references students(id) ON DELETE CASCADE,
                                             FOREIGN KEY (course_id) references courses(id) ON DELETE CASCADE
);
