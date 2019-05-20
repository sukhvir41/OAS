create sequence hibernate_sequence start 1 increment 1
create table admin (id uuid not null, type varchar(255) not null, primary key (id))
create table attendance (lecture_fid varchar(255) not null, student_fid uuid not null, attended boolean not null, leave boolean not null, marked_by_teacher boolean not null, primary key (lecture_fid, student_fid))
create table class_room (id int8 not null, division varchar(2) not null, minimum_subjects int4 not null, name varchar(40) not null, semester int4 not null, course_fid int8, primary key (id))
create table course (id int8 not null, name varchar(40) not null, department_fid int8, primary key (id))
create table department (id int8 not null, name varchar(40) not null, hod_teacher_fid uuid, primary key (id))
create table lecture (id varchar(255) not null, count int4 not null, date timestamp not null, ended boolean not null, tcs_fid int8, primary key (id))
create table student (id uuid not null, first_name varchar(40) not null, last_name varchar(40) not null, mac_id varchar(17), roll_number int4 not null, unaccounted boolean, verified boolean not null, class_fid int8, primary key (id))
create table student_subject_link (student_fid uuid not null, subject_fid int8 not null, primary key (student_fid, subject_fid))
create table subject (id int8 not null, elective boolean not null, name varchar(40) not null, course_fid int8, primary key (id))
create table subject_class_link (class_fid int8 not null, subject_fid int8 not null, primary key (class_fid, subject_fid))
create table tcs (id int8 not null, class_fid int8 not null, subject_fid int8 not null, teacher_fid uuid, primary key (id))
create table teacher (id uuid not null, f_name varchar(40) not null, l_name varchar(40) not null, unaccounted boolean not null, verified boolean not null, class_fid int8, primary key (id))
create table teacher_department_link (teacher_fid int8 not null, department_fid uuid not null, primary key (teacher_fid, department_fid))
create table users (id uuid not null, date timestamp, email varchar(40) not null, number int8 not null, password varchar(100) not null, session_id varchar(50), session_token varchar(255), token varchar(255), used boolean, user_type varchar(255) not null, username varchar(40) not null, primary key (id))
alter table if exists tcs add constraint tcs_unique_constraint unique (class_fid, subject_fid)
alter table if exists users add constraint unique_email unique (email)
alter table if exists users add constraint unique_session_id unique (session_id)
alter table if exists users add constraint unique_username unique (username)
alter table if exists admin add constraint user_admin_foreign_key foreign key (id) references users
alter table if exists attendance add constraint lecture_foreign_key foreign key (lecture_fid) references lecture
alter table if exists attendance add constraint student_foreign_key foreign key (student_fid) references student
alter table if exists class_room add constraint class_course_foreign_key foreign key (course_fid) references course
alter table if exists course add constraint department_foreign_key foreign key (department_fid) references department
alter table if exists department add constraint department_hod_teacher_foreign_key foreign key (hod_teacher_fid) references teacher
alter table if exists lecture add constraint lecture_tcs_foreign_key foreign key (tcs_fid) references tcs
alter table if exists student add constraint class_foreign_key foreign key (class_fid) references class_room
alter table if exists student add constraint user_student_foreign_key foreign key (id) references users
alter table if exists student_subject_link add constraint student_subject_link_subject_foreign_key foreign key (subject_fid) references subject
alter table if exists student_subject_link add constraint student_subject_link_student_foreign_key foreign key (student_fid) references student
alter table if exists subject add constraint subject_course_foreign_key foreign key (course_fid) references course
alter table if exists subject_class_link add constraint subject_class_link_subject_foreign_key foreign key (subject_fid) references subject
alter table if exists subject_class_link add constraint subject_class_link_class_foreign_key foreign key (class_fid) references class_room
alter table if exists tcs add constraint tcs_class_foreign_key foreign key (class_fid) references class_room
alter table if exists tcs add constraint tcs_subject_foreign_key foreign key (subject_fid) references subject
alter table if exists tcs add constraint tcs_teacher_foreign_key foreign key (teacher_fid) references teacher
alter table if exists teacher add constraint class_teacher_class_foreign_key foreign key (class_fid) references class_room
alter table if exists teacher add constraint user_teacher_foreign_key foreign key (id) references users
alter table if exists teacher_department_link add constraint teacher_department_link_department_foreign_key foreign key (department_fid) references teacher
alter table if exists teacher_department_link add constraint teacher_department_link_teacher_foreign_key foreign key (teacher_fid) references department
