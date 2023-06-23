drop database if exists majorrevalida;
create database majorrevalida;

\c majorrevalida

drop sequence if exists program_sequence;
create sequence program_sequence as int increment by 1 start with 1001;

drop table if exists program cascade;
create table program (
    program_id serial primary key,
    program_code int default nextval('program_sequence') not null unique,
    program_title varchar(50)
); 

drop sequence if exists course_sequence;
create sequence course_sequence as int increment by 1 start with 2001;

drop table if exists course cascade;
create table course (
    course_id serial primary key,
    program_id int,
    course_code int default nextval('course_sequence') not null unique,
    course_title varchar(50),
    foreign key(program_id) references program(program_id) on delete cascade
);

drop sequence if exists major_sequence;
create sequence major_sequence as int increment by 1 start with 3001;

drop table if exists major cascade;
create table major (
    major_id serial primary key,
    course_id int,
    major_code int default nextval('major_sequence') not null unique,
    major_title varchar(50),
    foreign key(course_id) references course(course_id) on delete cascade
); 

drop sequence if exists curriculum_sequence;
create sequence curriculum_sequence as int increment by 1 start with 4001;

drop table if exists curriculum cascade;
create table curriculum (
    curriculum_id serial primary key,
    major_id int,
    curriculum_code int default nextval('curriculum_sequence') not null unique,
    curriculum_name varchar(50),
    foreign key(major_id) references major(major_id) on delete cascade
); 

drop table if exists academic_year cascade;
create table academic_year (
    academic_year_id serial primary key,
    academic_year varchar(50),
   	status varchar(20)
); 

drop table if exists users cascade;
create table users (
	user_id serial primary key,
    username varchar(50),
    password varchar(50),
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

drop sequence if exists parent_sequence;
create sequence parent_sequence as int increment by 1 start with 5001;

drop table if exists parent cascade;
create table parent(
    parent_id serial primary key,
    user_id int,
    foreign key(user_id) references users(user_id) on delete cascade
); 

drop sequence if exists student_sequence;
create sequence student_sequence as int increment by 1 start with 77001;

drop table if exists student cascade;
create table student (
    student_id serial primary key,
    user_id int,
    student_no int default nextval('student_sequence') not null unique,
    curriculum_id int,
    parent_id int,
    sem int,
    year_level int,
    academic_year_id int,
    foreign key(user_id) references users(user_id) on delete cascade,
    foreign key(parent_id) references parent(parent_id) on delete cascade,
    foreign key(curriculum_id) references curriculum(curriculum_id) on delete cascade,
    foreign key(academic_year_id) references academic_year(academic_year_id) on delete cascade
); 

drop sequence if exists admin_sequence;
create sequence admin_sequence as int increment by 1 start with 6001;

drop table if exists admin cascade;
create table admin (
    admin_id serial primary key,
    user_id int,
    admin_no int default nextval('admin_sequence') not null unique,
    foreign key(user_id) references users(user_id) on delete cascade
); 

drop sequence if exists professor_sequence;
create sequence professor_sequence as int increment by 1 start with 7001;

drop table if exists professor cascade;
create table professor (
    professor_id serial primary key,
    user_id int,
    professor_no int default nextval('professor_sequence') not null unique,
    work varchar(50),
    foreign key(user_id) references users(user_id) on delete cascade
); 

drop sequence if exists subject_sequence;
create sequence subject_sequence as int increment by 1 start with 1001;

drop table if exists subject cascade;
create table subject (
    subject_id serial primary key,
    subject_code int default nextval('subject_sequence') not null unique,
    subject_title varchar(50),
    units int,
    pre_requisites varchar(70),
    active_deactive boolean
);

drop table if exists t_subject_detail_history cascade;
create table t_subject_detail_history (
    subject_detail_his_id serial primary key,
    professor_id int,
    subject_id int,
    academic_year_id int,
    foreign key(professor_id) references professor(professor_id) on delete cascade,
    foreign key(subject_id) references subject(subject_id) on delete cascade,
    foreign key(academic_year_id) references academic_year(academic_year_id) on delete cascade
); 

drop table if exists grades cascade;
create table grades (
    grade_id serial primary key,
    student_id int,
    subject_detail_his_id int,
    prelim float,
    finals float,
    grade float,
    comment text,
    date_inserted timestamp,
    date_modified timestamp,
    remarks text,
    status varchar(15),
    foreign key(student_id) references student(student_id) on delete cascade,
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
    room_id int,
    foreign key(room_id) references room(room_id) on delete cascade
); 

drop table if exists professor_load cascade;
create table professor_load (
    load_id serial primary key,
    professor_id int,
    subject_id int,
    section_id int,
    foreign key(professor_id) references professor(professor_id) on delete cascade,
    foreign key(subject_id) references subject(subject_id) on delete cascade,
    foreign key(section_id) references section(section_id) on delete cascade
); 

drop table if exists student_enrollment cascade;
create table student_enrollment (
    enrollment_id serial primary key,
    student_id int,
    subject_id int,
    section_id int,
    foreign key(student_id) references student(student_id) on delete cascade,
    foreign key(subject_id) references subject(subject_id) on delete cascade,
    foreign key(section_id) references section(section_id) on delete cascade
); 

----insert into program table
--insert into program(program_title, major) values('BS IT', 'Application Development');
--
----insert into admin_user table
--insert into admin_user(username, password, first_name, last_name, admin_type) values('pastrero', '123456', 'patrick', 'astrero', 'Admin');
--
----insert into subject table
--insert into subject(subject_title, units, pre_requisites, active_deactive) values('Data Structures', 3, 'Math in the Modern World', 't');
--
----insert into professor table
--insert into professor(professor_name, password, work, gender, status, birth_date, active_deactive) values('pastrero', '123456', 'data structure prof', 'male', 'employed', '2001-07-25', 't');
--

