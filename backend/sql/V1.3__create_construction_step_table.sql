CREATE TABLE [dbo].[construction_step] (
    [ID]           BIGINT   IDENTITY     NOT NULL,
    [stepType] VARCHAR (40) NULL,
    [text]         VARCHAR (300)  NOT NULL,
    [garment_id]         BIGINT          NOT NULL,
    [utilities] VARCHAR(100) NULL,
    [sortOrder] INT NULL,
    CONSTRAINT [PK_construction_step] PRIMARY KEY CLUSTERED ([ID] ASC),
    CONSTRAINT [garment_id] FOREIGN KEY ([garment_id]) REFERENCES [dbo].[garment] ([ID])
    );