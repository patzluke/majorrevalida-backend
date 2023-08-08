drop database if exists majorrevalida;
create database majorrevalida;

\c majorrevalida

drop sequence if exists program_sequence;
create sequence program_sequence as int increment by 1 start with 1001;

drop table if exists program cascade;
create table program (
    program_id serial,
    program_code int default nextval('program_sequence') not null primary key,
    program_title varchar(50),
    active_deactive boolean
); 

drop sequence if exists department_sequence;
create sequence department_sequence as int increment by 1 start with 2001;

drop table if exists department cascade;
create table department (
    dept_id serial,
    dept_code int default nextval('department_sequence') not null primary key,
    dept_name varchar(50),
    active_deactive boolean
); 

drop sequence if exists course_sequence;
create sequence course_sequence as int increment by 1 start with 3001;

drop table if exists course cascade;
create table course (
    course_id serial,
    course_code int default nextval('course_sequence') not null primary key,
    program_code int,
    dept_code int,    
    course_title varchar(50),
    foreign key(program_code) references program(program_code) on delete cascade,
    foreign key(dept_code) references department(dept_code) on delete cascade
);

drop sequence if exists major_sequence;
create sequence major_sequence as int increment by 1 start with 4001;

drop table if exists major cascade;
create table major (
    major_id serial,
    major_code int default nextval('major_sequence') not null primary key,
    course_code int,
    major_title varchar(50),
    active_deactive boolean,
    foreign key(course_code) references course(course_code) on delete cascade
); 

drop sequence if exists curriculum_sequence;
create sequence curriculum_sequence as int increment by 1 start with 5001;

drop table if exists curriculum cascade;
create table curriculum (
    curriculum_id serial,
    curriculum_code int default nextval('curriculum_sequence') not null primary key,
    major_code int,
    curriculum_name varchar(150),
    active_deactive boolean,
    foreign key(major_code) references major(major_code) on delete cascade
); 

drop table if exists academic_year cascade;
create table academic_year (
    academic_year_id serial primary key,
    academic_year int,
    start_date date,
    end_date date,
    semester int,
   	status varchar(20)
); 

drop table if exists student_applicant cascade;
create table student_applicant (
    student_applicant_id serial primary key,
    student_type varchar(20),
    selected_course_code int,
    selected_major_code int,
    year_level int,
    school_year int,
    semester int,
    first_name varchar(70),
    middle_name varchar(70),
    last_name varchar(70),
    suffix_name varchar(70),
    gender varchar(15),
    civil_status varchar(20),
    citizenship varchar(50),
    birth_date date,
    birth_place varchar(50),
    religion varchar(80),
    address text,
    telephone_no varchar(15),
    mobile_no varchar(15),
    email varchar(80) unique,
    
    guardian_first_name varchar(70),
    guardian_middle_name varchar(70),
    guardian_last_name varchar(70),
    guardian_suffix_name varchar(70),
    guardian_mobile_no varchar(15),
    guardian_email varchar(80),
    guardian_occupation varchar(80),
    guardian_relation varchar(80),
    
    date_applied timestamp,
    date_accepted timestamp,
    acceptance_status varchar(30) default 'Pending'
); 

drop table if exists users cascade;
create table users (
	user_id serial primary key,
    username varchar(100) unique,
    password varchar(150),
 	email varchar(50) unique,
    contact_no varchar(50),
    first_name varchar(70),
    middle_name varchar(70),
    last_name varchar(70),
    user_type varchar(30),
    birth_date date,
    address text,
    civil_status varchar(20),
    gender varchar(15),
    nationality varchar(30),
    active_status boolean,
    active_deactive boolean, --soft delete
    image varchar(255)
); 

drop table if exists user_tokens;
create table user_tokens (
	user_id int,
	token varchar(250),
	foreign key(user_id) references users(user_id) on delete cascade
);

drop sequence if exists parent_sequence;
create sequence parent_sequence as int increment by 1 start with 6001;

drop table if exists parent cascade;
create table parent(
    parent_id serial,
    user_id int,
    occupation varchar(30),
    relation varchar(30),
    parent_no int default nextval('parent_sequence') not null primary key,
    foreign key(user_id) references users(user_id) on delete cascade
); 

drop sequence if exists student_sequence;
create sequence student_sequence as int increment by 1 start with 77001;

drop table if exists student cascade;
create table student (
    student_id serial,
    student_no int default nextval('student_sequence') not null primary key,
    user_id int,
    parent_no int,
    curriculum_code int,
    academic_year_id int,
    year_level int,
    foreign key(user_id) references users(user_id) on delete cascade,
    foreign key(parent_no) references parent(parent_no) on delete cascade,
    foreign key(curriculum_code) references curriculum(curriculum_code) on delete cascade,
    foreign key(academic_year_id) references academic_year(academic_year_id) on delete cascade
); 

drop sequence if exists admin_sequence;
create sequence admin_sequence as int increment by 1 start with 7001;

drop table if exists admin cascade;
create table admin (
    admin_id serial,
    user_id int,
    admin_no int default nextval('admin_sequence') not null primary key,
    foreign key(user_id) references users(user_id) on delete cascade
); 

drop sequence if exists professor_sequence;
create sequence professor_sequence as int increment by 1 start with 8001;

drop table if exists professor cascade;
create table professor (
    professor_id serial,
    user_id int,
    professor_no int default nextval('professor_sequence') not null primary key,
    work varchar(50),
    foreign key(user_id) references users(user_id) on delete cascade
);

drop table if exists subject cascade;
create table subject (
    subject_id serial,
    subject_code int primary key,
	abbrevation varchar(50),
    subject_title varchar(100),
    units float,
    price float,
    active_deactive boolean,
    active_status boolean
);

insert into subject(subject_code, abbrevation, subject_title, units, active_deactive, active_status, price) 
values(9000, 'N/A', 'None', 0.0, false, false, 0);

drop sequence if exists subject_sequence;
create sequence subject_sequence as int increment by 1 start with 9001;
alter table subject alter column subject_code set default nextval('subject_sequence');

drop table if exists major_subject cascade;
create table major_subject (
    major_subject_id serial,
    subject_code int,
    curriculum_code int,
    pre_requisites int,
    year_level int,
    sem int,
    foreign key(subject_code) references subject(subject_code) on delete cascade,
    foreign key(pre_requisites) references subject(subject_code) on delete cascade,
    foreign key(curriculum_code) references curriculum(curriculum_code) on delete cascade
);

drop table if exists minor_subject cascade;
create table minor_subject (
    minor_subject_id serial,
    subject_code int primary key,
    pre_requisites int,
    year_level int,
    sem int,
    foreign key(subject_code) references subject(subject_code) on delete cascade,
    foreign key(pre_requisites) references subject(subject_code) on delete cascade
);

drop table if exists room cascade;
create table room (
    room_id serial primary key,
    room_no int
); 


drop table if exists section cascade;
create table section (
    section_id serial primary key,
    major_code int,
    section_name varchar(30),
    foreign key(major_code) references major(major_code) on delete cascade
); 

drop table if exists professor_load cascade;
create table professor_load (
    load_id serial primary key,
    professor_no int,
    subject_code int,
    section_id int,
    room_id int,
    dept_code int,
    day varchar(20),
    start_time time,
    end_time time,
    active_deactive boolean,
    foreign key(professor_no) references professor(professor_no) on delete cascade,
    foreign key(section_id) references section(section_id) on delete cascade,
    foreign key(room_id) references room(room_id) on delete cascade,
    foreign key(dept_code) references department(dept_code) on delete cascade    
); 

drop table if exists student_enrollment cascade;
create table student_enrollment (
    enrollment_id serial primary key,
    student_no int,
    academic_year_id int,
    section_id int,
    payment_status varchar(30),
    status varchar(30),
    foreign key(student_no) references student(student_no) on delete cascade,
    foreign key(academic_year_id) references academic_year(academic_year_id) on delete cascade,
    foreign key(section_id) references section(section_id) on delete cascade
); 

drop table if exists t_subject_detail_history cascade;
create table t_subject_detail_history (
    subject_detail_his_id serial primary key,
    professor_no int,
    subject_code int,
    academic_year_id int,
    foreign key(professor_no) references professor(professor_no) on delete cascade,
    foreign key(academic_year_id) references academic_year(academic_year_id) on delete cascade
); 

drop table if exists submitted_subjects_for_enrollment cascade;
create table submitted_subjects_for_enrollment (
    submitted_subjects_id serial primary key,
    subject_code int,
    enrollment_id int,
    status varchar(20) default 'Approved',
    foreign key(subject_code) references subject(subject_code) on delete cascade,
    foreign key(enrollment_id) references student_enrollment(enrollment_id) on delete cascade
); 

drop table if exists student_subject_enrolled cascade;
create table student_subject_enrolled (
    enroll_subject_id serial primary key,
    load_id int,
    enrollment_id int,
    foreign key(load_id) references professor_load(load_id) on delete cascade,
    foreign key(enrollment_id) references student_enrollment(enrollment_id) on delete cascade
); 

drop table if exists grades cascade;
create table grades (
    grade_id serial primary key,
    student_no int,
    subject_detail_his_id int,
    enroll_subject_id int,
    prelim_grade float default 0.00,
    finals_grade float default 0.00,
    total_grade float default 0.00,
    comment text,
    date_prelim_grade_inserted timestamp,
    date_finals_grade_inserted timestamp,
    date_prelim_grade_modified timestamp,
    date_finals_grade_modified timestamp,
    remarks text default 'Conditional',
    is_submitted boolean default 'f',
    foreign key(student_no) references student(student_no) on delete cascade,
    foreign key(subject_detail_his_id) references t_subject_detail_history(subject_detail_his_id) on delete cascade,
    foreign key(enroll_subject_id) references student_subject_enrolled(enroll_subject_id) on delete cascade

); 

drop table if exists student_schedule cascade;
create table student_schedule (
    schedule_id serial primary key,
    student_no int,
    load_id int,
    academic_year_id int,
    foreign key(student_no) references student(student_no) on delete cascade,
    foreign key(load_id) references professor_load(load_id) on delete cascade,
    foreign key(academic_year_id) references academic_year(academic_year_id) on delete cascade
); 

drop table if exists student_attendance cascade;
create table student_attendance (
    student_attendance_id serial primary key,
    student_no int,
    load_id int,
    attendance_date date,
    status varchar(20) default 'Absent',
    foreign key(student_no) references student(student_no) on delete cascade,
    foreign key(load_id) references professor_load(load_id) on delete cascade    
); 

drop table if exists evaluation_question cascade;
create table evaluation_question (
    evaluation_question_id serial primary key,
    question text,
    active_deactive boolean
); 

drop table if exists evaluation_question_answer cascade;
create table evaluation_question_answer (
    evaluation_question_answer_id serial primary key,
    evaluation_question_id int,
    professor_no int,
    subject_code int,
    enrollment_id int,
    rating varchar(40),
    foreign key(evaluation_question_id) references evaluation_question(evaluation_question_id) on delete cascade,
    foreign key(professor_no) references professor(professor_no) on delete cascade,    
    foreign key(subject_code) references subject(subject_code) on delete cascade,    
    foreign key(enrollment_id) references student_enrollment(enrollment_id) on delete cascade
); 

drop table if exists website_activation_toggle cascade;
create table website_activation_toggle (
    is_evaluation_time boolean,
    is_professor_grading_time boolean
); 

INSERT INTO website_activation_toggle (is_evaluation_time, is_professor_grading_time)
values ('f', 'f');

INSERT INTO evaluation_question (question, active_deactive)
values 
('Teacher is prepared for class.', 't'),
('Teacher knows his/her subject.', 't'),
('Teacher is organized and neat.', 't'),
('Teacher is flexible and accomodating for individual student needs.', 't');
--('Teacher is clear in giving directions and on explaining what is expected on assignments and tests.', 't'),
--('Teacher manages time well.', 't'),
--('Teacher returns homework in a timely manner.', 't'),
--('Teacher Grades fairly.', 't'),
--('Teacher is creating in developing activities and lessons', 't'),
--('I have learned a lot from this teacher.', 't');


--insert into academic_year table
INSERT INTO academic_year (academic_year, start_date, end_date, semester, status)
VALUES
  (2023, '2023-08-15', '2023-12-31', 1, 'Process');


--insert into program table
insert into program(program_title, active_deactive) values('Bachelor Of Science', 't');

--insert into Department table
insert into department(dept_name, active_deactive) values('Department of Information And Computing Sciences', 't');

--insert into Course table
insert into course(program_code, dept_code, course_title) values(1001, 2001, 'Information Technology');
insert into course(program_code, dept_code, course_title) values(1001, 2001, 'Information Systems');

--insert into Major table
insert into major(course_code, major_title, active_deactive) values(3001, 'Application Development', 't');

--insert into Curriculum table
insert into curriculum(major_code, curriculum_name, active_deactive) values(4001, 'BACHELOR OF SCIENCE IN INFORMATION TECHNOLOGY SPECIALIZED IN WEB AND MOBILE APPLICATION', 't');

--insert into major and minor subject table
	--for information technology major in web and mobile major subjects (first year 1ST SEM)
	--major subjs
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 2601', 'Introduction to Computing', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9001, 9000, 5001, 1, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 2602', 'Computer Programming I (Fundamentals of Programming - Imperative)', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9002, 9000, 5001, 1, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 2604', 'Values Education', 2, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9003, 9000, 5001, 1, 1);
	
	--minor subjs
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ART_APP', 'Art Appreciation', 3, 'true', 'true', 1000);
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9004, 9000, 1, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('MATH_MW', 'Mathematics in the Modern World', 3, 'true', 'true', 1000);
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9005, 9000, 1, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('PATH-FIT I', 'Physical Activities Towards Health and Fitness Sports', 2, 'true', 'true', 1000);
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9006, 9000, 1, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('STS', 'Science, Technology and Society', 3, 'true', 'true', 1000);
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9007, 9000, 1, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('THY 1', 'Christian Vision of the Human Person', 3, 'true', 'true', 1000);
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9008, 9000, 1, 1);
	
	--for information technology major in web and mobile major subjects (first year 2ND SEM)
	--major subjs
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 2606', 	'Computer Programming II (Intermediate Programming - Object-Oriented)', 4, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9009, 9002, 5001, 1, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('IT 2621', 'Information Technology Fundamentals', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9010, 9004, 5001, 1, 2);

	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('IT 2622', 'Human-Computer Interaction', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9011, 9004, 5001, 1, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 2603', 'Discrete Structures', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9012, 9000, 5001, 1, 2);
	
	--minor subjs
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('PATH-FIT II', 'Physical Activities Towards Health and Fitness in Dance', 2, 'true', 'true', 1000);
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9013, 9006, 1, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('PURPCOM', 'Purposive Communication', 3, 'true', 'true', 1000);
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9014, 9006, 1, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('THY 2', 'Christian Vision of Marriage and Family', 3, 'true', 'true', 1000);
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9015, 9008, 1, 2);
	
	--for information technology major in web and mobile major subjects (SECOND year 1ST SEM)
	--major subjs
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 2605', 'Data Structures and Algorithms', 4, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9016, 9000, 5001, 2, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 2607', 'Information Management', 4, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9017, 9000, 5001, 2, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 2608', 'Applications Development and Emerging Technologies 1 (Web-Front-End)', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9018, 9009, 5001, 2, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 26014', 'Data Communications and Networking I', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9019, 9010, 5001, 2, 1);
	
	--minor subjs
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('PATH-FIT III', 'Fitness Exercises for Specific Sports', 2, 'true', 'true', 1000);
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9020, 9000, 2, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('THY 3', 'Christian Vision of the Church in Society', 3, 'true', 'true', 1000);
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9021, 9000, 2, 1);
	
	--for information technology major in web and mobile major subjects (SECOND year 2ND SEM)
	--major subjs
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 2609', 'Applications Development and Emerging Technologies 2 (Enterprise Back-end)', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9022, 9019, 5001, 2, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('IT 2623', 'Computer Architecture, Organization, and Logic', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9023, 9000, 5001, 2, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('IT 2624', 'Data Communications and Networking II', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9024, 9020, 5001, 2, 2);
	
	--minor subjs
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('PATH-FIT IV', 'Human Enhancement', 2, 'true', 'true', 1000);
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9025, 9000, 2, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('READ_PH', 'Readings in Philippine History', 3, 'true', 'true', 1000);
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9026, 9000, 2, 2);
	
	--for information technology major in web and mobile major subjects (THIRD year 1st SEM)
	--major subjs
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ELE 1', 'Professional Elective 1 (Specialization)', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9027, 9000, 5001, 3, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 26010', 'Software Engineering 1', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9028, 9018, 5001, 3, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 26011', 'Applications Development and Emerging Technologies 3 (Mobile Programming)', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9029, 9022, 5001, 3, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 26012', 'Operating Systems', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9030, 9023, 5001, 3, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('IT 2625', 'Alternative Operating System', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9031, 9023, 5001, 3, 1);
	
	--minor subjs
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('LIWORIZ', 'Life and Works of Rizal', 2, 'true', 'true', 1000);
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9032, 9000, 3, 1);
	
	--for information technology major in web and mobile major subjects (THIRD year 2nd SEM)
	--major subjs
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ELE 2', 'Professional Elective 2 (Specialization)', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9033, 9000, 5001, 3, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 26013', 'Software Engineering II', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9034, 9029, 5001, 3, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('IT 2627', 'Quantitative Models', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9035, 9012, 5001, 3, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('IT 2629', 'Information Technology Capstone Project I', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9036, 9000, 5001, 3, 2);
	
	--for information technology major in web and mobile major subjects (FOURTH year 1st SEM)
	--major subjs
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ELE 3', 'Professional Elective 3 (Specialization)', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9037, 9033, 5001, 4, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('F-ELEC', 'Free Elective', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9038, 9000, 5001, 4, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 26016', 'Technopreneurship', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9039, 9036, 5001, 4, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('IT 26211', 'Information Technology Capstone Project II', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9040, 9036, 5001, 4, 1);
	
	--for information technology major in web and mobile major subjects (FOURTH year 2nd SEM)
	--major subjs
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ELE 4', 'Professional Elective 4 (Specialization)', 3, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9041, 9033, 5001, 4, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('ICS 26015', 'Emerging Technologies', 1, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9042, 9000, 5001, 4, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive, active_status, price) 
	values('IT 26212', 'Practicum (500 hours)', 6, 'true', 'true', 1000);
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9043, 9040, 5001, 4, 2);
	
	
	
--insert into Room table
insert into room(room_no) values(101);insert into room(room_no) values(102);insert into room(room_no) values(103);insert into room(room_no) values(104);insert into room(room_no) values(105);
insert into room(room_no) values(201);insert into room(room_no) values(202);insert into room(room_no) values(203);insert into room(room_no) values(204);insert into room(room_no) values(205);
insert into room(room_no) values(301);insert into room(room_no) values(302);insert into room(room_no) values(303);insert into room(room_no) values(304);insert into room(room_no) values(305);

--insert into Section table
insert into section(major_code, section_name) values(4001, '1-ITA'), (4001, '1-ITB');
insert into section(major_code, section_name) values(4001, '2-ITA'), (4001, '2-ITB');
insert into section(major_code, section_name) values(4001, '3-ITA'), (4001, '3-ITB');
insert into section(major_code, section_name) values(4001, '4-ITA'), (4001, '4-ITB');


--insert into users and admin table
insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_status, active_deactive) 
values('pastrero', '123456', 'patzluke@gmail.com', '9055261296', 'Patrick', 'Artuz', 'Astrero', 'Admin', '1999-07-08', 'Vista Verde, Cainta', 'Single', 'Male', 'Filipino', 'true', 'true');

insert into admin(user_id) values(1);

insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_status, active_deactive) 
values('nikaastrero', '123456', 'nikaastrero@gmail.com', '9055261295', 'Nika', 'Artuz', 'Astrero', 'Admin', '2013-07-25', 'Vista Verde, Cainta', 'Single', 'Female', 'Filipino', 'true', 'true');

insert into admin(user_id) values(2);

--insert into users and Professor table
insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_status, active_deactive) 
values('norman', '123456', 'norman@gmail.com', '9188192721', 'Norman', 'Astros', 'Fernando', 'Professor', '1980-07-08', 'Vista Verde, Cainta', 'Single', 'Male', 'Filipino', 'true', 'true');
insert into professor(user_id, work) values(3, 'Teacher');

insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_status, active_deactive) 
values('estrella', '123456', 'estrella@gmail.com', '9055261278', 'Estrella', 'Fernando', 'Astros', 'Professor', '1975-07-25', 'Vista Verde, Cainta', 'Single', 'Female', 'Filipino', 'true', 'true');
insert into professor(user_id, work) values(4, 'Teacher');

insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_status, active_deactive) 
values('lopez', '123456', 'lopez@gmail.com', '9055261278', 'lopez', 'Abigail', 'chuchu', 'Professor', '1975-07-25', 'Vista Verde, Cainta', 'Single', 'Female', 'Filipino', 'true', 'true');
insert into professor(user_id, work) values(5, 'Teacher');

insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_status, active_deactive) 
values('jackson', '123456', 'jackson@gmail.com', '9055261278', 'Jackson', 'Normal', 'Astros', 'Professor', '1975-07-25', 'Vista Verde, Cainta', 'Single', 'Female', 'Filipino', 'true', 'true');
insert into professor(user_id, work) values(6, 'Teacher');

insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_status, active_deactive) 
values('patricia', '123456', 'patricia@gmail.com', '9055261278', 'Patricia', 'Megan', 'Young', 'Professor', '1975-07-25', 'Vista Verde, Cainta', 'Single', 'Female', 'Filipino', 'true', 'true');
insert into professor(user_id, work) values(7, 'Teacher');

insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_status, active_deactive) 
values('joy', '123456', 'joy@gmail.com', '9055261278', 'Joy', 'Old', 'Young', 'Professor', '1975-07-25', 'Vista Verde, Cainta', 'Single', 'Female', 'Filipino', 'true', 'true');
insert into professor(user_id, work) values(8, 'Teacher');

--insert into Professor Load table
--1st year
--1st sem
insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8001, 9001, 1, 1, 2001, 'Monday', '08:00', '11:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8002, 9002, 1, 1, 2001, 'Monday', '12:00', '15:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8003, 9003, 1, 1, 2001, 'Monday', '15:00', '18:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8004, 9004, 1, 1, 2001, 'Tuesday', '08:00', '11:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8005, 9005, 1, 1, 2001, 'Tuesday', '12:00', '15:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8006, 9006, 1, 1, 2001, 'Tuesday', '15:00', '17:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8001, 9007, 1, 1, 2001, 'Wednesday', '08:00', '11:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8002, 9008, 1, 1, 2001, 'Wednesday', '12:00', '15:00', 't');

--1st year
--2nd sem
insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8003, 9009, 1, 2, 2001, 'Wednesday', '15:00', '18:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8004, 9010, 1, 2, 2001, 'Thursday', '08:00', '11:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8005, 9011, 1, 2, 2001, 'Thursday', '12:00', '15:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8006, 9012, 1, 2, 2001, 'Thursday', '15:00', '18:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8001, 9013, 1, 2, 2001, 'Friday', '08:00', '10:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8002, 9014, 1, 2, 2001, 'Friday', '12:00', '15:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8003, 9015, 1, 2, 2001, 'Friday', '15:00', '18:00', 't');

--2nd year
--1st sem
insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8004, 9016, 1, 3, 2001, 'Saturday', '08:00', '11:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8005, 9017, 1, 3, 2001, 'Saturday', '12:00', '15:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8006, 9018, 1, 3, 2001, 'Saturday', '15:00', '18:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8001, 9019, 1, 3, 2001, 'Monday', '08:00', '11:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8002, 9020, 1, 3, 2001, 'Monday', '12:00', '15:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8003, 9021, 1, 3, 2001, 'Monday', '15:00', '18:00', 't');

--2nd year
--2nd sem
insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8004, 9022, 1, 4, 2001, 'Tuesday', '08:00', '11:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8005, 9023, 1, 4, 2001, 'Tuesday', '12:00', '15:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8006, 9024, 1, 4, 2001, 'Tuesday', '15:00', '18:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8001, 9025, 1, 4, 2001, 'Wednesday', '08:00', '11:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8002, 9026, 1, 4, 2001, 'Wednesday', '12:00', '15:00', 't');

--3rd year
--1st sem
insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8003, 9027, 1, 5, 2001, 'Wednesday', '15:00', '18:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8004, 9028, 1, 5, 2001, 'Thursday', '08:00', '11:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8005, 9029, 1, 5, 2001, 'Thursday', '12:00', '15:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8006, 9030, 1, 5, 2001, 'Thursday', '15:00', '18:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8001, 9031, 1, 5, 2001, 'Friday', '08:00', '11:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8002, 9032, 1, 5, 2001, 'Friday', '12:00', '15:00', 't');

--3rd year
--2nd sem
insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8003, 9033, 1, 6, 2001, 'Friday', '15:00', '18:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8004, 9034, 1, 6, 2001, 'Saturday', '08:00', '11:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8005, 9035, 1, 6, 2001, 'Saturday', '12:00', '15:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8006, 9036, 1, 6, 2001, 'Saturday', '15:00', '18:00', 't');

--4th year
--1st sem
insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8001, 9037, 1, 7, 2001, 'Monday', '08:00', '11:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8002, 9038, 1, 7, 2001, 'Monday', '12:00', '15:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8003, 9039, 1, 7, 2001, 'Monday', '15:00', '18:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8004, 9040, 1, 7, 2001, 'Tuesday', '08:00', '11:00', 't');

--4th year
--2nd sem
insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8005, 9041, 1, 7, 2001, 'Tuesday', '12:00', '15:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8006, 9042, 1, 7, 2001, 'Tuesday', '15:00', '18:00', 't');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time, active_deactive) 
values(8001, 9043, 1, 7, 2001, 'Wednesday', '08:00', '14:00', 't');

--Insert value to student_applicant
INSERT INTO student_applicant (
    student_type,
    selected_course_code,
    selected_major_code,
    year_level,
    school_year,
    semester,
    first_name,
    middle_name,
    last_name,
    suffix_name,
    gender,
    civil_status,
    citizenship,
    birth_date,
    birth_place,
    religion,
    address,
    telephone_no,
    mobile_no,
    email,
    guardian_first_name,
    guardian_middle_name,
    guardian_last_name,
    guardian_suffix_name,
    guardian_mobile_no,
    guardian_email,
    guardian_occupation,
    guardian_relation,
    date_applied,
    date_accepted,
    acceptance_status
) VALUES
    -- First row
    ('New', 3001, 4001, 1, 2023, 1, 'John', 'Doe', 'Smith', 'Jr.', 'Male', 'Single', 'US', '1990-01-01', 'New York', 'Christian', '123 Main St', '123456789', '987654321', 'john.doe@example.com', 'Father', 'Father Middle', 'Father Last', 'Sr.', '123456789', 'father1@example.com', 'Engineer', 'Father', NOW(), NULL, 'Pending'),
    -- Second row
    ('New', 3001, 4001, 1, 2023, 1, 'Jane', 'Doe', 'Johnson', NULL, 'Female', 'Married', 'UK', '1992-05-15', 'London', 'Catholic', '456 Elm St', '987654321', '123456789', 'jane.doe@example.com', 'Father', 'Father Middle', 'Father Last', NULL, '987654321', 'father2@example.com', 'Teacher', 'Mother', NOW(), NULL, 'Pending'),
    -- Third row
    ('New', 3001, 4001, 1, 2023, 1, 'Michael', 'Brown', 'Johnson', NULL, 'Male', 'Single', 'Canada', '1991-07-20', 'Toronto', 'Protestant', '789 Oak St', '555555555', '999999999', 'michael.brown@example.com', 'Father', 'Father Middle', 'Father Last', NULL, '555555555', 'father3@example.com', 'Architect', 'Mother',  NOW(), NULL, 'Pending'),
    -- Fourth row
    ('New', 3001, 4001, 1, 2023, 1, 'Emily', 'Smith', 'Wilson', 'Jr.', 'Female', 'Single', 'Australia', '1993-09-10', 'Sydney', 'Buddhist', '234 Pine St', '222222222', '888888888', 'emily.smith@example.com', 'Father', 'Father Middle', 'Father Last', NULL, '222222222', 'father4@example.com', 'Lawyer', 'Mother', NOW(), NULL, 'Pending'),
    -- Fifth row
    ('New', 3001, 4001, 1, 2023, 1, 'David', 'Johnson', 'Taylor', NULL, 'Male', 'Married', 'France', '1992-04-05', 'Paris', 'Jewish', '345 Walnut St', '333333333', '777777777', 'david.johnson@example.com', 'Father', 'Father Middle', 'Father Last', NULL, '333333333', 'father5@example.com', 'Entrepreneur', 'Mother', NOW(), NULL, 'Pending');



select se.student_no, sub.abbrevation from submitted_subjects_for_enrollment ssfe
inner join student_enrollment se on ssfe.enrollment_id = se.enrollment_id
inner join professor_load pl on ssfe.subject_code = pl.subject_code
inner join subject sub on ssfe.subject_code = sub.subject_code

where se.student_no = 77015 and ssfe.enrollment_id = (select max(enrollment_id) from submitted_subjects_for_enrollment);

select distinct sa.attendance_date, sa.load_id from student_attendance sa
right join student_enrollment se on sa.student_no = se.student_no
where sa.load_id = 1
order by sa.attendance_date;
