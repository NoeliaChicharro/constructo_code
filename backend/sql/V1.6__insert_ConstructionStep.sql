INSERT INTO [dbo].[construction_step]
([stepType], [text], [garment_id], [utilities])
VALUES
( 'PREPARE', 'Stoff dublieren und abbügeln', 1, 'Bügelanlage'),
('PREPARE', 'Zuschnitt aller Materialien', 1, 'Manuell'),
('PREPARE', 'Markierungen anbringen', 1, 'Manuell'),
('PREPARE', 'Fixieren', 1, 'Fixierpresse'),
('PREPARE', 'Kleinteile vorbereiten', 1, null),
('PREPARE', 'Längs und Achselnähte versäubern', 1, '504'),
('CONSTRUCTION', 'Abnäher schliessen', 1, '301'),
('CONSTRUCTION', 'Teilungsnähte schliessen', 1, '301'),
('CONSTRUCTION', 'Verschluss verarbeiten', 1, '301'),
('CONSTRUCTION', 'Kragen verarbeiten', 1, '301'),
('CONSTRUCTION', 'SN schliessen', 1, '301'),
('CONSTRUCTION', 'Ärmleschlitz einarbeiten', 1, '301'),
('CONSTRUCTION', 'Ärmelnaht schliessen', 1, '301'),
('CONSTRUCTION', 'Manchette verarbeiten', 1, '301'),
('CONSTRUCTION', 'Ärmle einsteppen', 1, '301'),
('CONSTRUCTION', 'Saum verarbeiten', 1, '301'),
('FINISH', 'Knopflöcher einarbeiten', 1, '304'),
('FINISH', 'Knöpfe annähen', 1, 'Manuell'),
('FINISH', 'Endkontrolle', 1, 'Manuell'),
('FINISH', 'Endbügeln', 1, 'Bügelanlage'),
('FINISH', 'Verpacken', 1, 'Manuell');