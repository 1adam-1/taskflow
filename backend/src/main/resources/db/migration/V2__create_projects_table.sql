CREATE TABLE projects (
                          id BIGSERIAL PRIMARY KEY,

                          name VARCHAR(100) NOT NULL,

                          description VARCHAR(1000),

                          start_date DATE NOT NULL,

                          end_date DATE,

                          status VARCHAR(30) NOT NULL,

                          owner_id BIGINT NOT NULL,

                          created_at TIMESTAMP NOT NULL,

                          updated_at TIMESTAMP NOT NULL,

                          CONSTRAINT fk_project_owner
                              FOREIGN KEY (owner_id)
                                  REFERENCES users(id)
                                  ON DELETE CASCADE
);

CREATE INDEX idx_project_owner
    ON projects(owner_id);