CREATE TABLE [dbo].[garment] (
    [ID]           BIGINT   IDENTITY     NOT NULL,
    [garment_type] VARCHAR (50) NULL,
    [name]         VARCHAR (200)  NOT NULL,
    [description]  VARCHAR (1000) NULL,
    CONSTRAINT [PK_garment] PRIMARY KEY CLUSTERED ([ID] ASC),
    );