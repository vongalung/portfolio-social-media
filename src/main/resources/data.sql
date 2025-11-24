BEGIN;

INSERT INTO social_media.reaction ("name")
VALUES
    ('LIKE'),
    ('DISLIKE'),
    ('HEART'),
    ('SMILE'),
    ('CRY'),
    ('SALUTE'),
    ('LAUGH'),
    ('ANGRY'),
    ('QUESTION'),
    ('VOMIT'),
    ('SUN_GLASS'),
    ('POINTS_UP'),
    ('POINTS_LEFT'),
    ('POINTS_DOWN'),
    ('POINTS_RIGHT')
;

COMMIT;
