
ALTER TABLE series
    ADD COLUMN tmdb_id BIGINT,
    ADD COLUMN overview TEXT,
    ADD COLUMN poster_path VARCHAR(255),
    ADD COLUMN vote_average DOUBLE PRECISION;

-- evita duplicar séries em buscas futuras (mesmo padrão usado em movies)
CREATE UNIQUE INDEX idx_series_tmdb_id ON series (tmdb_id) WHERE tmdb_id IS NOT NULL;