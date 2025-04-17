
use sis_db;
-- 1
select c.course_name, avg(student_count) as avg_students_per_course
from (
    select course_id, count(student_id) as student_count
    from enrollments
    group by course_id
) as course_enrollment_counts
join courses c on course_enrollment_counts.course_id = c.course_id
group by c.course_name;

-- 2
select student_id, s.first_name, amount
from payments
join students s using(student_id)
where amount = (select max(amount) from payments);

-- 3
select course_id, course_name, student_count
from (
    select c.course_id, c.course_name, count(e.student_id) as student_count
    from courses c
    left join enrollments e on c.course_id = e.course_id
    group by c.course_id, c.course_name
) as course_counts
where student_count = (select max(student_count) from (
    select count(student_id) as student_count from enrollments group by course_id
) as max_counts);


-- 6
select teacher_id, first_name
from teacher
where teacher_id not in (select distinct teacher_id from courses);

-- 7
select avg(year(curdate()) - year(date_of_birth)) as avg_age from students;

-- 8
select course_id, course_name
from courses
where course_id not in (select distinct course_id from enrollments);


-- 10
select student_id, count(payment_id) as payment_count
from payments
group by student_id
having count(payment_id) > 1;

-- 12
select c.course_name, count(e.student_id) as student_count
from courses c
left join enrollments e on c.course_id = e.course_id
group by c.course_id, c.course_name;

-- 13
select s.student_id, s.name, avg(p.amount) as avg_payment
from students s
join payments p on s.student_id = p.student_id
group by s.student_id, s.name;


