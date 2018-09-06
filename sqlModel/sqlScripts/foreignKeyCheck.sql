SET FOREIGN_KEY_CHECKS=0;
delete from Skill where idSkill = 7;

delete from Advertisment where User_idUser > 0;

delete from Advertisment_has_Skill where Advertisment_idAdvertisment > 0;