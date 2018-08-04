ALTER table	Interest
ADD column interesterId INT NOT NULL after interestTime;

ALTER table	Comment
ADD column commenterId INT NOT NULL after contentText;

ALTER table User
ADD column isPublicEducation INT after educationText;

ALTER table User
ADD column isPublicJob INT after jobExperienceText;
