drop database if exists majorrevalida;
create database majorrevalida;

\c majorrevalida

drop sequence if exists program_sequence;
create sequence program_sequence as int increment by 1 start with 1001;

drop table if exists program cascade;
create table program (
    program_id serial,
    program_code int default nextval('program_sequence') not null primary key,
    program_title varchar(50)
); 

drop sequence if exists department_sequence;
create sequence department_sequence as int increment by 1 start with 2001;

drop table if exists department cascade;
create table department (
    dept_id serial,
    dept_code int default nextval('department_sequence') not null primary key,
    dept_name varchar(50)
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
    foreign key(major_code) references major(major_code) on delete cascade
); 

drop table if exists academic_year cascade;
create table academic_year (
    academic_year_id serial primary key,
    academic_year varchar(50),
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
    
    father_first_name varchar(70),
    father_middle_name varchar(70),
    father_last_name varchar(70),
    father_suffix_name varchar(70),
    father_mobile_no varchar(15),
    father_email varchar(80),
    father_occupation varchar(80),
    
   	mother_first_name varchar(70),
    mother_middle_name varchar(70),
    mother_last_name varchar(70),
    mother_suffix_name varchar(70),
    mother_mobile_no varchar(15),
    mother_email varchar(80),
    mother_occupation varchar(80),
    
    date_applied timestamp,
    date_accepted timestamp,
    acceptance_status varchar(30) default 'Not yet Accepted'
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
    active_deactive boolean,
    image varchar(50)
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
    active_deactive boolean
);

insert into subject(subject_code, abbrevation, subject_title, units, active_deactive) 
values(9000, 'N/A', 'None', 0.0, false);

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

drop table if exists t_subject_detail_history cascade;
create table t_subject_detail_history (
    subject_detail_his_id serial primary key,
    professor_no int,
    subject_code int,
    academic_year_id int,
    foreign key(professor_no) references professor(professor_no) on delete cascade,
    foreign key(academic_year_id) references academic_year(academic_year_id) on delete cascade
); 

drop table if exists grades cascade;
create table grades (
    grade_id serial primary key,
    student_no int,
    subject_detail_his_id int,
    prelim_grade float,
    finals_grade float,
    total_grade float,
    comment text,
    date_inserted timestamp,
    date_modified timestamp,
    remarks text,
    status varchar(15),
    foreign key(student_no) references student(student_no) on delete cascade,
    foreign key(subject_detail_his_id) references t_subject_detail_history(subject_detail_his_id) on delete cascade
); 

drop table if exists room cascade;
create table room (
    room_id serial primary key,
    room_no int
); 


drop table if exists section cascade;
create table section (
    section_id serial primary key,
    section_name varchar(30)
); 

drop table if exists professor_load cascade;
create table professor_load (
    load_id serial primary key,
    professor_no int,
    subject_code int,
    section_id int,
    room_id int,
    dept_code int,
    day varchar(10),
    start_time time,
    end_time time,
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
    sem int,
    start_date date,
    end_date date,
    foreign key(student_no) references student(student_no) on delete cascade,
    foreign key(academic_year_id) references academic_year(academic_year_id) on delete cascade    
); 

drop table if exists schedule cascade;
create table schedule (
    schedule_id serial primary key,
    student_no int,
    load_id int,
    foreign key(student_no) references student(student_no) on delete cascade,
    foreign key(load_id) references professor_load(load_id) on delete cascade    
); 

drop table if exists student_attendance cascade;
create table student_attendance (
    student_attendance_id serial primary key,
    student_no int,
    load_id int,
    attendance_date date,
    status varchar(20) default 'N/A',
    foreign key(student_no) references student(student_no) on delete cascade,
    foreign key(load_id) references professor_load(load_id) on delete cascade    
); 

--insert into academic_year table
insert into academic_year(academic_year, status) values('2023-2024', 'On-going');

--insert into program table
insert into program(program_title) values('Bachelor Of Science');
insert into program(program_title) values('Bachelor Of Arts');

--insert into Department table
insert into department(dept_name) values('Department of Information And Computing Sciences');
insert into department(dept_name) values('Department of Engineering');

--insert into Course table
insert into course(program_code, dept_code, course_title) values(1001, 2001, 'Information Technology');
insert into course(program_code, dept_code, course_title) values(1001, 2001, 'Information Systems');
insert into course(program_code, dept_code, course_title) values(1001, 2001, 'Information Computer Science');
insert into course(program_code, dept_code, course_title) values(1001, 2002, 'Civil Engineering');
insert into course(program_code, dept_code, course_title) values(1001, 2002, 'Computer Engineering');

--insert into Major table
insert into major(course_code, major_title) values(3001, 'Application Development');
insert into major(course_code, major_title) values(3001, 'Network and Security');
insert into major(course_code, major_title) values(3001, 'Automation');

--insert into Curriculum table
insert into curriculum(major_code, curriculum_name) values(4001, 'BACHELOR OF SCIENCE IN INFORMATION TECHNOLOGY SPECIALIZED IN WEB AND MOBILE APPLICATION');
insert into curriculum(major_code, curriculum_name) values(4002, 'BACHELOR OF SCIENCE IN INFORMATION TECHNOLOGY SPECIALIZED IN NETWORK AND SECURITY');
insert into curriculum(major_code, curriculum_name) values(4003, 'BACHELOR OF SCIENCE IN INFORMATION TECHNOLOGY SPECIALIZED IN NETWORK AND SECURITY');

--insert into major and minor subject table
	--for information technology major in web and mobile major subjects (first year 1ST SEM)
	--major subjs
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('ICS 2601', 'Introduction to Computing', 3, 'true');
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9001, 9000, 5001, 1, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('ICS 2602', 'Computer Programming I (Fundamentals of Programming - Imperative)', 3, 'true');
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9002, 9000, 5001, 1, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('ICS 2604', 'Values Education', 2, 'true');
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9003, 9000, 5001, 1, 1);
	
	--minor subjs
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('ART_APP', 'Art Appreciation', 3, 'true');
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9004, 9000, 1, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('MATH_MW', 'Mathematics in the Modern World', 3, 'true');
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9005, 9000, 1, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('PATH-FIT', 'Physical Activities Towards Health and Fitness Sports', 2, 'true');
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9006, 9000, 1, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('STS', 'Science, Technology and Society', 3, 'true');
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9007, 9000, 1, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('THY 1', 'Christian Vision of the Human Person', 3, 'true');
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9008, 9000, 1, 1);
	
	--for information technology major in web and mobile major subjects (first year 2ND SEM)
	--major subjs
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('ICS 2606', 	'Computer Programming II (Intermediate Programming - Object-Oriented)', 4, 'true');
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9009, 9002, 5001, 1, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('IT 2621', 'Information Technology Fundamentals', 3, 'true');
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9010, 9004, 5001, 1, 2);

	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('IT 2622', 'Human-Computer Interaction', 3, 'true');
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9011, 9004, 5001, 1, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('ICS 2603', 'Discrete Structures', 3, 'true');
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9012, 9004, 5001, 1, 2);
	
	--minor subjs
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('PATH-FIT', 'Physical Activities Towards Health and Fitness in Dance', 2, 'true');
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9013, 9006, 1, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('PURPCOM', 'Purposive Communication', 3, 'true');
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9014, 9006, 1, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('THY 2', 'Christian Vision of Marriage and Family', 3, 'true');
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9015, 9006, 1, 2);
	
	--for information technology major in web and mobile major subjects (SECOND year 1ST SEM)
	--major subjs
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('ICS 2605', 'Data Structures and Algorithms', 4, 'true');
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9016, 9014, 5001, 2, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('ICS 2607', 'Information Management', 4, 'true');
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9017, 9000, 5001, 2, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('ICS 2608', 'Applications Development and Emerging Technologies 1 (Web-Front-End)', 3, 'true');
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9018, 9009, 5001, 2, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('ICS 26014', 'Data Communications and Networking I', 3, 'true');
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9019, 9010, 5001, 2, 1);
	
	--minor subjs
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('PATH-FIT', 'Fitness Exercises for Specific Sports', 2, 'true');
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9020, 9000, 2, 1);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('THY 3', 'Christian Vision of the Church in Society', 3, 'true');
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9021, 9016, 2, 1);
	
	--for information technology major in web and mobile major subjects (SECOND year 2ND SEM)
	--major subjs
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('ICS 2609', 'Applications Development and Emerging Technologies 2 (Enterprise Back-end)', 3, 'true');
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9022, 9019, 5001, 2, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('IT 2623', 'Computer Architecture, Organization, and Logic', 3, 'true');
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9023, 9010, 5001, 2, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('IT 2624', 'Data Communications and Networking II', 3, 'true');
	insert into major_subject(subject_code, pre_requisites, curriculum_code, year_level, sem)
	values(9024, 9020, 5001, 2, 2);
	
	--minor subjs
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('PATH-FIT', 'Human Enhancement', 2, 'true');
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9025, 9000, 2, 2);
	
	insert into subject(abbrevation, subject_title, units, active_deactive) 
	values('READ_PH', 'Readings in Philippine History', 3, 'true');
	insert into minor_subject(subject_code, pre_requisites, year_level, sem)
	values(9026, 9000, 2, 2);
	
--insert into Room table
insert into room(room_no) values(101);insert into room(room_no) values(102);insert into room(room_no) values(103);insert into room(room_no) values(104);insert into room(room_no) values(105);
insert into room(room_no) values(201);insert into room(room_no) values(202);insert into room(room_no) values(203);insert into room(room_no) values(204);insert into room(room_no) values(205);
insert into room(room_no) values(301);insert into room(room_no) values(302);insert into room(room_no) values(303);insert into room(room_no) values(304);insert into room(room_no) values(305);

--insert into Section table
insert into section(section_name) values('1-ITA');insert into section(section_name) values('1-ITB');insert into section(section_name) values('1-ITC');insert into section(section_name) values('1-ITD');
insert into section(section_name) values('2-ITA');insert into section(section_name) values('2-ITB');insert into section(section_name) values('2-ITC');insert into section(section_name) values('2-ITD');
insert into section(section_name) values('3-ITA');insert into section(section_name) values('3-ITB');insert into section(section_name) values('3-ITC');insert into section(section_name) values('3-ITD');
insert into section(section_name) values('4-ITA');insert into section(section_name) values('4-ITB');insert into section(section_name) values('4-ITC');insert into section(section_name) values('4-ITD');


--insert into users and admin table
insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_deactive, image) 
values('pastrero', '123456', 'patzluke@gmail.com', '9055261296', 'Patrick', 'Artuz', 'Astrero', 'Admin', '1999-07-08', 'Vista Verde, Cainta', 'Single', 'Male', 'Filipino', 'true', 'patrick.jpeg');

insert into admin(user_id) values(1);

insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_deactive, image) 
values('nikaastrero', '123456', 'nikaastrero@gmail.com', '9055261295', 'Nika', 'Artuz', 'Astrero', 'Admin', '2013-07-25', 'Vista Verde, Cainta', 'Single', 'Female', 'Filipino', 'true', 'nika.jpeg');

insert into admin(user_id) values(2);

--insert into users and Parent table
insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_deactive, image) 
values('maribelastrero', '123456', 'maribelastrero@gmail.com', '9188192726', 'Maribel', 'Astros', 'Artuz', 'Parent', '1975-07-08', 'Vista Verde, Cainta', 'Married', 'Female', 'Filipino', 'true', 'maribel.jpeg');

insert into parent(user_id) values(3);

insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_deactive, image) 
values('norbinastrero', '123456', 'norbinastrero@gmail.com', '9055261278', 'Norbin', 'Fernando', 'Astrero', 'Parent', '1975-07-25', 'Vista Verde, Cainta', 'Married', 'Male', 'Filipino', 'true', 'norbin.jpeg');

insert into parent(user_id) values(4);

--insert into users and Professor table
insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_deactive, image) 
values('norman', '123456', 'norman@gmail.com', '9188192721', 'Norman', 'Astros', 'Fernando', 'Professor', '1980-07-08', 'Vista Verde, Cainta', 'Single', 'Male', 'Filipino', 'true', 'norman.jpeg');
insert into professor(user_id, work) values(5, 'Teacher');

insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_deactive, image) 
values('estrella', '123456', 'estrella@gmail.com', '9055261278', 'Estrella', 'Fernando', 'Astros', 'Professor', '1975-07-25', 'Vista Verde, Cainta', 'Single', 'Female', 'Filipino', 'true', 'estrella.jpeg');
insert into professor(user_id, work) values(6, 'Teacher');

insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_deactive, image) 
values('lopez', '123456', 'lopez@gmail.com', '9055261278', 'lopez', 'Abigail', 'chuchu', 'Professor', '1975-07-25', 'Vista Verde, Cainta', 'Single', 'Female', 'Filipino', 'true', 'lopez.jpeg');
insert into professor(user_id, work) values(7, 'Teacher');

insert into users(username, password, email, contact_no, first_name, middle_name, last_name, user_type, birth_date, address, civil_status, gender, nationality, active_deactive, image) 
values('jackson', '123456', 'jackson@gmail.com', '9055261278', 'Jackson', 'Normal', 'Astros', 'Professor', '1975-07-25', 'Vista Verde, Cainta', 'Single', 'Female', 'Filipino', 'true', 'jackson.jpeg');
insert into professor(user_id, work) values(8, 'Teacher');

--insert into Professor Load table
insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time) 
values(8001, 9001, 1, 1, 2001, 'M', '08:00', '11:00');
insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time) 
values(8001, 9002, 1, 1, 2001, 'M', '11:00', '14:00');
insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time) 
values(8002, 9003, 1, 1, 2001, 'M', '16:00', '18:00');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time) 
values(8002, 9004, 1, 1, 2001, 'T', '08:00', '11:00');
insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time) 
values(8003, 9005, 1, 1, 2001, 'T', '11:00', '14:00');
insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time) 
values(8003, 9006, 1, 1, 2001, 'T', '16:00', '18:00');

insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time) 
values(8004, 9007, 1, 1, 2001, 'W', '08:00', '11:00');
insert into professor_load(professor_no, subject_code, section_id, room_id, dept_code, day, start_time, end_time) 
values(8004, 9008, 1, 1, 2001, 'W', '11:00', '14:00');

select cu.curriculum_code, cu.curriculum_name, m.major_code, m.major_title, co.course_code, co.course_title, d.dept_code, d.dept_name, p.program_code, p.program_title from curriculum cu 
inner join major m on cu.major_code = m.major_code
inner join course co on m.course_code = co.course_code
inner join department d on co.dept_code = d.dept_code
inner join program p on co.program_code = p.program_code;
