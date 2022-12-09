-- mes_work_order
-- mes_work_order_requirement
-- mes_work_order_picture
-- mes_work_order_utk_printable_file
-- mes_printer_work_order_schedule
-- mes_work_order_print_info
-- mes_work_order_feedback
-- mes_dev_utk_base_bp
-- mes_dev_utk_bp
-- mes_material
-- mes_dev_utk_printer_model
-- mes_dev_utk_printer
-- mes_dev_utk_printer_fault
CREATE TABLE mes_work_order
(
    id                    BIGINT AUTO_INCREMENT,
    tenant_id             BIGINT                                                          NOT NULL,
    deleted_at            TIMESTAMP DEFAULT NULL,
    created_at            TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at            TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    code                  VARCHAR(32),
    name                  VARCHAR(36),
    status                VARCHAR(36)                                                     NOT NULL,
    preview_file_id       BIGINT,
    maintainer_id         BIGINT                                                          NOT NULL,
    creator_id            BIGINT                                                          NOT NULL,
    exception_code        VARCHAR(36),
    exception_reason      VARCHAR(512),
    exception_handled     TINYINT   DEFAULT 0,
    is_offline_work_order TINYINT   DEFAULT 0,
    printable_archive     VARCHAR(2048),
    scheduled_at          TIMESTAMP,
    search_text           JSON,
    PRIMARY KEY (id),
    CONSTRAINT uniq_mes_work_order_code UNIQUE (tenant_id, code)
);
CREATE TABLE mes_work_order_requirement
(
    id                   BIGINT AUTO_INCREMENT,
    tenant_id            BIGINT                                                          NOT NULL,
    deleted_at           TIMESTAMP DEFAULT NULL,
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    work_order_id        BIGINT                                                          NOT NULL,
    material_id          BIGINT,
    utk_printer_model_id BIGINT,
    remark               VARCHAR(1024),
    delivery_time        TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uniq_mes_work_order_requirement UNIQUE (tenant_id, work_order_id)
);
CREATE TABLE mes_work_order_picture
(
    id            BIGINT AUTO_INCREMENT,
    tenant_id     BIGINT                                                          NOT NULL,
    deleted_at    TIMESTAMP DEFAULT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    work_order_id BIGINT                                                          NOT NULL,
    file_id       BIGINT,
    PRIMARY KEY (id)
);
CREATE TABLE mes_work_order_utk_printable_file
(
    id            BIGINT AUTO_INCREMENT,
    tenant_id     BIGINT                                                          NOT NULL,
    deleted_at    TIMESTAMP DEFAULT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    work_order_id BIGINT                                                          NOT NULL,
    file_id       BIGINT                                                          NOT NULL,
    is_disabled   TINYINT   DEFAULT 0,
    PRIMARY KEY (id)
);
CREATE TABLE mes_printer_work_order_schedule
(
    id                  BIGINT AUTO_INCREMENT,
    concurrency_version BIGINT    NOT NULL,
    tenant_id           BIGINT    NOT NULL,
    ordinal             INTEGER   NOT NULL,
    utk_printer_id      BIGINT    NOT NULL,
    work_order_id       BIGINT    NOT NULL,
    operator_id         BIGINT    NOT NULL,
    scheduled_at        TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE mes_work_order_print_info
(
    id                    BIGINT AUTO_INCREMENT,
    tenant_id             BIGINT                                                          NOT NULL,
    deleted_at            TIMESTAMP DEFAULT NULL,
    created_at            TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at            TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    work_order_id         BIGINT                                                          NOT NULL,
    utk_printer_id        BIGINT                                                          NOT NULL,
    status                VARCHAR(32)                                                     NOT NULL,
    start_time            TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    end_time              TIMESTAMP,
    error_info            VARCHAR(2048),
    printed_seconds       INT,
    estimated_seconds     INT,
    is_offline_work_order TINYINT   DEFAULT 0,
    start_time_calibrated TINYINT   DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT uniq_mes_utk_work_order_print_info UNIQUE (tenant_id, work_order_id, utk_printer_id)
);
CREATE TABLE mes_work_order_print_info_segment
(
    tenant_id                BIGINT                              NOT NULL,
    work_order_print_info_id BIGINT,
    status                   VARCHAR(32)                         NOT NULL,
    trigger_at               TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
CREATE TABLE mes_work_order_feedback
(
    id            BIGINT AUTO_INCREMENT,
    tenant_id     BIGINT                                                          NOT NULL,
    deleted_at    TIMESTAMP DEFAULT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    work_order_id BIGINT                                                          NOT NULL,
    creator_id    BIGINT                                                          NOT NULL,
    type          VARCHAR(32)                                                     NOT NULL,
    picture_ids   VARCHAR(2048),
    tag_ids       VARCHAR(2048),
    is_submitted  TINYINT   DEFAULT 0,
    is_handled    TINYINT   DEFAULT 0,
    description   VARCHAR(128),
    PRIMARY KEY (id)
);
CREATE TABLE mes_dev_utk_base_bp
(
    id               BIGINT AUTO_INCREMENT,
    tenant_id        BIGINT                                                          NOT NULL,
    deleted_at       TIMESTAMP DEFAULT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    name             VARCHAR(128)                                                    NOT NULL,
    version          VARCHAR(64),
    base_bp_file_id  BIGINT,
    creator_id       BIGINT,
    base_material_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT uniq_mes_utk_base_bp_file UNIQUE (tenant_id, base_bp_file_id)
);

CREATE TABLE mes_dev_utk_bp
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY,
    tenant_id       BIGINT                                                          NOT NULL,
    deleted_at      TIMESTAMP DEFAULT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    name            VARCHAR(128)                                                    NOT NULL,
    creator_id      BIGINT                                                          NOT NULL,
    base_bp_id      BIGINT                                                          NOT NULL,
    bp_file_id      BIGINT                                                          NOT NULL,
    material_id     BIGINT                                                          NOT NULL,
    editable        TINYINT   DEFAULT 1,
    customer_params VARCHAR(10240)                                                  NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE mes_material
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY,
    tenant_id        BIGINT                                                          NOT NULL,
    deleted_at       TIMESTAMP DEFAULT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    name             VARCHAR(128)                                                    NOT NULL,
    creator_id       BIGINT                                                          NOT NULL,
    is_disabled      TINYINT   DEFAULT 0                                             NOT NULL,
    description      VARCHAR(128),
    color            VARCHAR(64),
    tag_ids          VARCHAR(512),
    base_material_id BIGINT,
    editable         TINYINT,
    density          DOUBLE,
    PRIMARY KEY (id)
);
CREATE TABLE mes_dev_utk_printer_model
(
    id         BIGINT AUTO_INCREMENT,
    tenant_id  BIGINT                                                          NOT NULL,
    deleted_at TIMESTAMP DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    name       VARCHAR(128)                                                    NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE mes_dev_utk_printer
(
    id                       BIGINT AUTO_INCREMENT,
    tenant_id                BIGINT                                                          NOT NULL,
    deleted_at               TIMESTAMP DEFAULT NULL,
    created_at               TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at               TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    name                     VARCHAR(128),
    code                     VARCHAR(128),
    status                   VARCHAR(255),
    creator_id               BIGINT                                                          NOT NULL,
    device_id                BIGINT,
    work_order_id            BIGINT,
    model_id                 BIGINT,
    bp_id                    BIGINT,
    material_id              BIGINT,
    make_time                INTEGER,
    left_make_time           INTEGER,
    disable_user_id          BIGINT,
    disabled_at              TIMESTAMP,
    err_code                 VARCHAR(255),
    err_reason               VARCHAR(1024),
    executing_cmd            VARCHAR(255),
    storage_date             TIMESTAMP,
    is_variable_spot         TINYINT,
    warranty_expiration_date TIMESTAMP,
    search_text              JSON,
    PRIMARY KEY (id),
    CONSTRAINT uniq_dev_utk_printer_code UNIQUE (code),
    CONSTRAINT uniq_dev_utk_printer_device_id UNIQUE (device_id),
    CONSTRAINT uniq_dev_utk_printer_work_order_id UNIQUE (tenant_id, work_order_id)
);
CREATE TABLE mes_dev_utk_printer_fault
(
    id            BIGINT AUTO_INCREMENT,
    tenant_id     BIGINT                              NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    printer_id    BIGINT                              NOT NULL,
    fault_info    VARCHAR(10240),
    is_handled    TINYINT,
    maintainer_id BIGINT,
    handled_at    TIMESTAMP,
    PRIMARY KEY (id)
);
