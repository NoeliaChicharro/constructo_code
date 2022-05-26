CREATE TABLE [dbo].[user_result] (
    [ID]           BIGINT   IDENTITY     NOT NULL,
    [rightAmount]    INT NULL,
    [answerTime] DATETIME2,
    [passed] BIT,
    [attemptedGarment_id]      BIGINT          NOT NULL,
    [user_id]        BIGINT          NOT NULL,
    CONSTRAINT [PK_user_result] PRIMARY KEY CLUSTERED ([ID] ASC),
    CONSTRAINT [attemptedGarment_id] FOREIGN KEY ([attemptedGarment_id]) REFERENCES [dbo].[garment] ([ID]),
    CONSTRAINT [user_id] FOREIGN KEY ([user_id]) REFERENCES [dbo].[constructo_user] ([ID])
    );