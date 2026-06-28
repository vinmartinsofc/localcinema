ALTER TABLE movies
    ADD COLUMN tmdb_id BIGINT,
    ADD COLUMN overview TEXT,
    ADD COLUMN poster_path VARCHAR(255),
    ADD COLUMN vote_average DOUBLE PRECISION;

ALTER TABLE movies
    ADD CONSTRAINT uk_movies_tmdb_id UNIQUE (tmdb_id);