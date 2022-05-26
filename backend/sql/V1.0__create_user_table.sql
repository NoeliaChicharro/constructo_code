CREATE TABLE [dbo].[constructo_user] (
    [ID]        BIGINT           IDENTITY (1, 1) NOT NULL,
    [firstName] VARCHAR (100)  NULL,
    [lastName] VARCHAR (100)  NULL,
    [username]  VARCHAR (100) NOT NULL,
    [email]     VARCHAR (200) NOT NULL,
    [password]  VARCHAR (200) NOT NULL,
    [role] VARCHAR (50) NOT NULL,
    CONSTRAINT [PK_constructo_user] PRIMARY KEY CLUSTERED ([ID] ASC)
    );