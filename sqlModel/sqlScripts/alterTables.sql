ALTER table	Interest
ADD column interesterId INT NOT NULL after interestTime;

ALTER table	Comment
ADD column commenterId INT NOT NULL after contentText;
