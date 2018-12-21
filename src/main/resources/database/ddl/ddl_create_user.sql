CREATE TABLE USER (
	id VARCHAR(100) NOT NULL,
  	name VARCHAR(100),
  	email VARCHAR(50),
  	password VARCHAR(20),
  	created DATE,
  	modified DATE,
  	last_login DATE,
  	token VARCHAR(100),
  	PRIMARY KEY (id)
);


CREATE TABLE PHONE (
	id VARCHAR(100) NOT NULL,
	id_user VARCHAR(100) NOT NULL,
	number VARCHAR(15),
    ddd VARCHAR(5),
    PRIMARY KEY (id),
    FOREIGN KEY (id_user) REFERENCES USER (id)
);