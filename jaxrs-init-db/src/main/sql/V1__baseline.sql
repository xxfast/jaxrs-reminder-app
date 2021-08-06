CREATE table reminders(id INT(11) NOT NULL AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       date DATE NOT NULL,

                       iscomplete BIT NOT NULL,
                       CONSTRAINT reminders_PK PRIMARY KEY (id)

                      )


