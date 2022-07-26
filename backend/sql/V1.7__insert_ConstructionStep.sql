INSERT INTO [dbo].[construction_step]
([stepType], [text], [garment_id], [utilities], [sortOrder])
VALUES
('PREPARE', 'Stoff dublieren und abbügeln', 1, 'Bügelanlage',1),
('PREPARE', 'Zuschnitt aller Materialien', 1, 'Manuell',2),
('PREPARE', 'Markierungen anbringen', 1, 'Manuell',3),
('PREPARE', 'Fixieren', 1, 'Fixierpresse',4),
('PREPARE', 'Längs und Achselnähte versäubern', 1, '504',5),
('CONSTRUCTION', 'Abnäher schliessen', 1, '301',6),
('CONSTRUCTION', 'Teilungsnähte schliessen', 1, '301',7),
('CONSTRUCTION', 'Verschluss verarbeiten', 1, '301',8),
('CONSTRUCTION', 'Kragen verarbeiten', 1, '301',9),
('CONSTRUCTION', 'SN schliessen', 1, '301',10),
('CONSTRUCTION', 'Ärmelschlitz einarbeiten', 1, '301',11),
('CONSTRUCTION', 'Ärmelnaht schliessen', 1, '301',12),
('CONSTRUCTION', 'Manchette verarbeiten', 1, '301',13),
('CONSTRUCTION', 'Ärmle einsteppen', 1, '301',14),
('CONSTRUCTION', 'Saum verarbeiten', 1, '301',15),
('FINISH', 'Knopflöcher einarbeiten', 1, '304',16),
('FINISH', 'Knöpfe annähen', 1, 'Manuell',17),
('FINISH', 'Endkontrolle', 1, 'Manuell',18),
('FINISH', 'Endbügeln', 1, 'Bügelanlage',19),
('FINISH', 'Verpacken', 1, 'Manuell',20);