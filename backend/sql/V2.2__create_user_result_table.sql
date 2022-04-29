CREATE TABLE [dbo].[user_result] (
    [ID]           BIGINT   IDENTITY     NOT NULL,
    [right_amount]    INT NULL,
    [answer_time] DATETIME2,
    [passed] BIT,
    [garment_id]      BIGINT          NOT NULL,
    [user_id]        BIGINT          NOT NULL,
    CONSTRAINT [PK_user_result] PRIMARY KEY CLUSTERED ([ID] ASC),
    CONSTRAINT [garment_id] FOREIGN KEY ([garment_id]) REFERENCES [dbo].[garment] ([ID]),
    CONSTRAINT [user_id] FOREIGN KEY ([user_id]) REFERENCES [dbo].[constructo_user] ([ID])
    );