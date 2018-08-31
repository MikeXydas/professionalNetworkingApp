SELECT * FROM Article a 
WHERE 	a.User_idUser in (SELECT c.connectedUserId FROM Connection c where c.User_idUser = 42) 
		or a.User_idUser = 42
        or a.idArticle in (Select i.Article_idArticle FROM Interest i
							WHERE i.interesterId in 
                            (SELECT c.connectedUserId FROM Connection c where c.User_idUser = 42));
        

SELECT * FROM Article a 
Where a.idArticle in (Select i.Article_idArticle FROM Interest i
												WHERE i.interesterId in (SELECT c.connectedUserId FROM Connection c where c.User_idUser = 42));