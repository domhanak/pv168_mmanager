-- my-schema.sql
CREATE TABLE albums (
  id     INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  name   VARCHAR(70),
  genre  VARCHAR(70),
  artist VARCHAR(70),
  published INT);

CREATE TABLE songs (
  id       INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  name     VARCHAR(50),
  track    INT,
  rank     INT,
  length   INT);

CREATE TABLE music (
  id          INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  albumId      INT REFERENCES albums (id) ON DELETE CASCADE,
  songId  INT REFERENCES songs (id) ON DELETE CASCADE);