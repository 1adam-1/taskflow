CREATE TABLE tasks (
                       id BIGSERIAL PRIMARY KEY,

                       title VARCHAR(150) NOT NULL,
                       description VARCHAR(2000),

                       status VARCHAR(50) NOT NULL,
                       priority VARCHAR(50) NOT NULL,

                       due_date DATE,

                       project_id BIGINT NOT NULL,
                       assignee_id BIGINT,

                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL,

                       CONSTRAINT fk_task_project
                           FOREIGN KEY (project_id)
                               REFERENCES projects(id)
                               ON DELETE CASCADE,

                       CONSTRAINT fk_task_assignee
                           FOREIGN KEY (assignee_id)
                               REFERENCES users(id)
                               ON DELETE SET NULL
);

CREATE INDEX idx_tasks_project_id
    ON tasks(project_id);

CREATE INDEX idx_tasks_assignee_id
    ON tasks(assignee_id);