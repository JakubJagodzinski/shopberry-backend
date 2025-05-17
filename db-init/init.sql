IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'shopberry')
    BEGIN
        CREATE DATABASE shopberry;
    END
GO

USE shopberry;
GO

IF NOT EXISTS (
    SELECT * FROM sys.schemas WHERE name = 'app'
)
    BEGIN
        EXEC('CREATE SCHEMA app');
    END
GO