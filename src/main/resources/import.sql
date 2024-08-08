INSERT INTO concert_user (email, name) VALUES ('111.foo@bar', 'aaa'), ('222.foo@bar', 'bbb'), ('333.foo@bar', 'ccc');

INSERT INTO concert (concert_name, artist, venue, start_date, end_date) VALUES ('콘서트1', '아티스트1', '장소1', '2024-08-01', '2024-09-01'), ('콘서트2', '아티스트2', '장소2', '2024-08-01', '2024-08-10'), ('콘서트3', '아티스트3', '장소3', '2024-08-05', '2024-08-25');

INSERT INTO concert_schedule (concert_id, concert_date, concert_time) VALUES (1, '2024-08-01', '2024-08-01 13:00:00'), (1, '2024-08-04', '2024-08-04 13:00:00'), (1, '2024-08-07', '2024-08-07 13:00:00'), (1, '2024-08-10', '2024-08-10 13:00:00'), (1, '2024-08-13', '2024-08-13 13:00:00'), (2, '2024-08-01', '2024-08-01 13:00:00'), (2, '2024-08-02', '2024-08-02 13:00:00'), (3, '2024-08-05', '2024-08-05 13:00:00'), (3, '2024-08-12', '2024-08-12 13:00:00');
