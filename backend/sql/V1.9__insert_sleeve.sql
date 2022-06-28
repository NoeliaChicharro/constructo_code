INSERT INTO [dbo].[garment]
([garmentType], [name], [description])
VALUES
    ( 'BLOUSE', 'Blusen Ärmel', 'Ärmel mit Schlitz und Manchette');

INSERT INTO [dbo].[construction_step]
([stepType], [text], [garment_id], [utilities], [sortOrder])
VALUES
    ('CONSTRUCTION', 'Ärmelschlitz einarbeiten', 3, '301',1),
    ('CONSTRUCTION', 'Ärmelnaht schliessen', 2, '301',2),
    ('CONSTRUCTION', 'Manchette verarbeiten', 2, 'Manuell',3);