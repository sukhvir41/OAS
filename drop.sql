alter table if exists admin drop constraint if exists user_admin_foreign_key
alter table if exists attendance drop constraint if exists lecture_foreign_key
alter table if exists attendance drop constraint if exists student_foreign_key
alter table if exists class_room drop constraint if exists class_course_foreign_key
alter table if exists course drop constraint if exists department_foreign_key
alter table if exists department drop constraint if exists department_hod_teacher_foreign_key
alter table if exists lecture drop constraint if exists lecture_tcs_foreign_key
alter table if exists student drop constraint if exists class_foreign_key
alter table if exists student drop constraint if exists user_student_foreign_key
alter table if exists student_subject_link drop constraint if exists student_subject_link_subject_foreign_key
alter table if exists student_subject_link drop constraint if exists student_subject_link_student_foreign_key
alter table if exists subject drop constraint if exists subject_course_foreign_key
alter table if exists subject_class_link drop constraint if exists subject_class_link_subject_foreign_key
alter table if exists subject_class_link drop constraint if exists subject_class_link_class_foreign_key
alter table if exists tcs drop constraint if exists tcs_class_foreign_key
alter table if exists tcs drop constraint if exists tcs_subject_foreign_key
alter table if exists tcs drop constraint if exists tcs_teacher_foreign_key
alter table if exists teacher drop constraint if exists class_teacher_class_foreign_key
alter table if exists teacher drop constraint if exists user_teacher_foreign_key
alter table if exists teacher_department_link drop constraint if exists teacher_department_link_department_foreign_key
alter table if exists teacher_department_link drop constraint if exists teacher_department_link_teacher_foreign_key
drop table if exists admin cascade
drop table if exists attendance cascade
drop table if exists class_room cascade
drop table if exists course cascade
drop table if exists department cascade
drop table if exists lecture cascade
drop table if exists student cascade
drop table if exists student_subject_link cascade
drop table if exists subject cascade
drop table if exists subject_class_link cascade
drop table if exists tcs cascade
drop table if exists teacher cascade
drop table if exists teacher_department_link cascade
drop table if exists users cascade
drop sequence if exists hibernate_sequence
