-- This file will be executed on application startup to populate the database.
-- IMPORTANT: This script assumes you have entities for Movie, Screen, and Show,
-- and that the table/column names match what Hibernate generates.
-- It also assumes the primary key for each table is an auto-incrementing identity column,
-- so we do not specify the ID in the INSERT statements.

INSERT INTO movie (title, description) VALUES
('Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology.'),
('The Dark Knight', 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham.');

INSERT INTO screen (name, seating_capacity, price) VALUES
('Audi 1', 120, 200),
('Audi 2 (IMAX)', 250, 300);

INSERT INTO showtime (movie_id, screen_id, start_time, available_seats) VALUES
(1, 1, '2025-08-14 09:30:00', 120),
(1, 1, '2025-08-14 13:30:00', 120),
(1, 1, '2025-08-14 17:30:00', 120),
(1, 1, '2025-08-14 20:30:00', 120);
INSERT INTO showtime (movie_id, screen_id, start_time, available_seats) VALUES
(2, 2, '2025-08-14 10:00:00', 250),
(2, 2, '2025-08-14 14:00:00', 250),
(2, 2, '2025-08-14 18:00:00', 250),
(2, 2, '2025-08-14 21:00:00', 250);
