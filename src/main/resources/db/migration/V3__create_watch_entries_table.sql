CREATE TABLE watch_entries (
                               id BIGSERIAL PRIMARY KEY,
                               title_type VARCHAR(20) NOT NULL,
                               title_id BIGINT NOT NULL,
                               watched_at DATE NOT NULL,
                               comment TEXT
);

CREATE INDEX idx_watch_entries_title ON watch_entries (title_type, title_id);