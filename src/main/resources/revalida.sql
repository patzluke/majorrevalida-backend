drop database if exists majorrevalida;
create database majorrevalida;

\c majorrevalida


drop table if exists program cascade;
create table program (
    program_id serial primary key,
    program_code int unique,
    program_title varchar(50),
    major varchar(50)
); 

drop sequence if exists student_sequence;
create sequence student_sequence as int increment by 1 start with 77001;

drop table if exists student cascade;
create table student (
    student_id serial primary key,
    student_no int default nextval('student_sequence') not null unique,
    program_id int,
    password varchar(70),
    first_name varchar(70),
    middle_name varchar(70),
    last_name varchar(70),
    birth_date date,
    sem int,
    year_level int,
    academic_year varchar(50),
    status varchar(15),
    active_deactive boolean,
    course varchar(50),
    image varchar(50),
    foreign key(program_id) references program(program_id) on delete cascade
); 

drop sequence if exists subject_sequence;
create sequence subject_sequence as int increment by 1 start with 1001;

drop table if exists subject cascade;
create table subject (
    subject_id serial primary key,
    subject_code int default nextval('subject_sequence') not null unique,
    subject_title varchar(50),
    units varchar(70),
    pre_requisites varchar(70),
    active_deactive boolean
); 

drop table if exists grades cascade;
create table grades (
    grade_id serial primary key,
    session_id int,
    student_id int,
    subject_id int,
    grade varchar(50),
    comment varchar(70),
    date_modified date,
    status varchar(15),
    foreign key(student_id) references student(student_id) on delete cascade,
    foreign key(subject_id) references subject(subject_id) on delete cascade,
    foreign key(session_id) references session(session_id) on delete cascade
); 

drop sequence if exists professor_sequence;
create sequence professor_sequence as int increment by 1 start with 2001;

drop table if exists professor cascade;
create table professor (
    professor_id serial primary key,
    professor_no int default nextval('professor_sequence') not null unique,
    professor_name varchar(50),
    password varchar(100),
    work varchar(50),
    gender varchar(7),
    status varchar(15),
    birth_date date,
    active_deactive boolean
); 

drop table if exists professor_load cascade;
create table professor_load (
    load_id serial primary key,
    professor_id int,
    course_title varchar(50),
    section varchar(50),
    year_level int,
    foreign key(professor_id) references professor(professor_id) on delete cascade
); 

drop table if exists t_subject_detail_history cascade;
create table t_subject_detail_history (
    session_id serial primary key,
    professor_id int,
    subject_id int,
    academic_year varchar(50),
    sem int,
    schedule varchar(30),
    section varchar(50),
    year_level int,
    status varchar(15),
    active_deactive boolean,
    foreign key(professor_id) references professor(professor_id) on delete cascade,
    foreign key(subject_id) references subject(subject_id) on delete cascade
); 

drop table if exists admin_user cascade;
create table admin_user (
    admin_id serial primary key,
    username varchar(50),
    password varchar(100),
    first_name varchar(30),
    last_name varchar(50),
    admin_type varchar(15)
); 

insert into admin_user(username, password, first_name, last_name, admin_type) values('pastrero', '123456', 'patrick', 'astrero', 'Admin');
