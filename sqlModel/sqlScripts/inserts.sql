insert into linkedIn.Advertisment (uploadTime, title, descriptionText, User_idUser)
VALUES ('1000-01-01 00:00:00', '1234', 'Mode', 10);


insert into linkedIn.Skill (skillName)
VALUES ('C');

insert into linkedIn.User_has_Skill (User_idUser, Skill_idSkill)
VALUES (12, 1);


insert into linkedIn_db.User (email, password, firstName, lastName, phoneNUmber, isModerator, photoUrl, educationText, jobExperienceText)
VALUES ('moderatorUser@domain.com', '1234', 'Mode', 'Rator', '210548590', 1, '/home/wadasd', 'Right now I am writing a text describing my education. ', 'And now I am describing my job experience');
