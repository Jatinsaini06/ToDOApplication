CREATE SCHEMA IF NOT EXISTS "nexdew_to_do";




CREATE TABLE role (
    role_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_name VARCHAR(255),
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    modified_at TIMESTAMP,
    modified_by VARCHAR(255),
    project_id UUID UNIQUE,
    user_id UUID UNIQUE
);


CREATE TABLE users (
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_email VARCHAR(255),
    user_name VARCHAR(255),
    user_phone_no VARCHAR(255),
    user_password VARCHAR(255) NOT NULL,
    business_information VARCHAR(255),
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    modified_at TIMESTAMP,
    modified_by VARCHAR(255),
    role_id UUID,
    CONSTRAINT fk_users_role FOREIGN KEY (role_id)
        REFERENCES role(role_id) ON DELETE SET NULL
);

CREATE TABLE project (
    project_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    project_name VARCHAR(255),
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    modified_at TIMESTAMP,
    modified_by VARCHAR(255),
    user_id UUID,
    role_id UUID,
    CONSTRAINT fk_project_users FOREIGN KEY (user_id)
        REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_project_role FOREIGN KEY (role_id)
        REFERENCES role(role_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS  project_invitation (
    invitation_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL,
    role_id UUID,
    accepted BOOLEAN DEFAULT FALSE,
    project_id UUID NOT NULL,
    token VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_invitation_project
        FOREIGN KEY (project_id)
        REFERENCES project(project_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_invitation_role
        FOREIGN KEY (role_id)
        REFERENCES role(role_id)
        ON DELETE SET NULL
);

ALTER TABLE role
ADD CONSTRAINT fk_role_project FOREIGN KEY (project_id)
    REFERENCES project(project_id) ON DELETE CASCADE;

ALTER TABLE role
ADD CONSTRAINT fk_role_user FOREIGN KEY (user_id)
    REFERENCES users(user_id) ON DELETE CASCADE;

CREATE TABLE permission (
    permission_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    permission_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    modified_at TIMESTAMP,
    modified_by VARCHAR(255),
    role_id UUID,
    CONSTRAINT fk_permission_role FOREIGN KEY (role_id)
        REFERENCES role(role_id)
);

CREATE TABLE project_member (
    member_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    modified_at TIMESTAMP,
    modified_by VARCHAR(255),
    project_id UUID,
    role_id UUID,
    user_id UUID,
    CONSTRAINT fk_member_project FOREIGN KEY (project_id)
        REFERENCES project(project_id),
    CONSTRAINT fk_member_role FOREIGN KEY (role_id)
        REFERENCES role(role_id),
    CONSTRAINT fk_member_user FOREIGN KEY (user_id)
        REFERENCES users(user_id)
);

CREATE TABLE status (
    status_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    modified_at TIMESTAMP,
    modified_by VARCHAR(255)
);

CREATE TABLE task (
    task_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    task_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    modified_at TIMESTAMP,
    modified_by VARCHAR(255),
    user_id UUID,
    project_id UUID,
    status_id UUID,
    CONSTRAINT fk_task_user FOREIGN KEY (user_id)
        REFERENCES users(user_id),
    CONSTRAINT fk_task_project FOREIGN KEY (project_id)
        REFERENCES project(project_id),
    CONSTRAINT fk_task_status FOREIGN KEY (status_id)
        REFERENCES status(status_id)
);

CREATE TABLE notification (
    notification_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    modified_at TIMESTAMP,
    modified_by VARCHAR(255),
    project_id UUID,
    task_id UUID,
    user_id UUID,
    CONSTRAINT fk_notification_project FOREIGN KEY (project_id)
        REFERENCES project(project_id),
    CONSTRAINT fk_notification_task FOREIGN KEY (task_id)
        REFERENCES task(task_id),
    CONSTRAINT fk_notification_user FOREIGN KEY (user_id)
        REFERENCES users(user_id)
);
