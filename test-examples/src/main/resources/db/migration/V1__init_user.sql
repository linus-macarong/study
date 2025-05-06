CREATE TABLE users
(
    id          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    nickname    VARCHAR(30) NOT NULL comment '사용자 닉네임',
    withdrawn   BOOLEAN     NOT NULL comment '탈퇴 여부',
    withdrew_at DATETIME(2) NULL comment '탈퇴 시각',
    created_at  DATETIME(2) NOT NULL comment '데이터 생성 시각',
    updated_at  DATETIME(2) NOT NULL comment '데이터 수정 시각'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='사용자';

CREATE INDEX idx_users_nickname_withdrawn ON users (nickname, withdrawn);
