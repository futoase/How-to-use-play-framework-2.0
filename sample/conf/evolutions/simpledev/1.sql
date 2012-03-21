# Messages schema

# --- !Ups

CREATE TABLE Messages (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  message TEXT NOT NULL,
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL
);

INSERT INTO Messages (
  name, message, created_at, updated_at
) VALUES 
('keiji matsuzaki', '書き込みテスト', '2012-03-20 11:00:00', '2012-03-20 11:00:00'),
('play taro', '↑どう見ても暇人', '2012-03-20 12:30:45', '2012-03-20 12:30:45')
;

# --- !Downs

DROP TABLE Messages;
