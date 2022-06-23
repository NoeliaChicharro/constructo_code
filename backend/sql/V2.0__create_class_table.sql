CREATE TABLE [dbo].[class] (
    [ID]        BIGINT           IDENTITY (1, 1) NOT NULL,
    [name] VARCHAR (100)  NULL,
    [mainTeacher] BIGINT,
    [student]   BIGINT,
    CONSTRAINT [PK_class] PRIMARY KEY CLUSTERED ([ID] ASC),
    CONSTRAINT [mainTeacher] FOREIGN KEY ([mainTeacher]) REFERENCES [dbo].[constructo_user] ([ID]),
    CONSTRAINT [student] FOREIGN KEY ([student]) REFERENCES [dbo].[constructo_user] ([ID])
    );