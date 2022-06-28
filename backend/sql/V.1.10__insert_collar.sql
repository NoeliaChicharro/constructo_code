INSERT INTO [dbo].[garment]
([garmentType], [name], [description])
VALUES
    ( 'BLOUSE', 'Chemise Kragen', 'Chemise Krgen für Bluesen');

INSERT INTO [dbo].[construction_step]
([stepType], [text], [garment_id], [utilities], [sortOrder])
VALUES
    ('CONSTRUCTION', 'Unter und Oberkragen zusammen steppen', 2, '301',1),
    ('CONSTRUCTION', 'Kragen stürzen', 2, 'Manuell',2),
    ('CONSTRUCTION', 'Fixierstepp', 2, 'Manuell',3),
    ('CONSTRUCTION', 'Sandwitchverfahren', 2, '301',4);