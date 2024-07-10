CREATE TABLE tab_user (
  user_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   access TEXT NOT NULL,
   CONSTRAINT pk_tab_user PRIMARY KEY (user_id)
);