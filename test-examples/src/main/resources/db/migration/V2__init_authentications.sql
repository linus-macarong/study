CREATE TABLE authentications
(
    id                      BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    user_id                 BIGINT UNSIGNED NOT NULL,
    email                   VARCHAR(100) NOT NULL comment '이메일',
    hashed_password         VARCHAR(100) NOT NULL comment '해싱된 비밀번호',
    latest_authenticated_at DATETIME(2) NULL comment '최근 인증 시각',
    deactivated             BOOLEAN      NOT NULL comment '비활성화 여부',
    deactivated_at          DATETIME(2)  NULL comment '비활성화 시각',
    created_at              DATETIME(2)  NOT NULL comment '데이터 생성 시각',
    updated_at              DATETIME(2)  NOT NULL comment '데이터 수정 시각'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='인증';

CREATE INDEX idx_authentications_user_id ON authentications (user_id);
CREATE INDEX idx_authentications_email_deactivated ON authentications (email, deactivated);
