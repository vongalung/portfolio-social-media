BEGIN;

insert into social_media.reaction
    ("id", created_at , "name")
values
    (gen_random_uuid(), now(), 'LIKE'),
    (gen_random_uuid(), now(), 'DISLIKE'),
    (gen_random_uuid(), now(), 'HEART'),
    (gen_random_uuid(), now(), 'SMILE'),
    (gen_random_uuid(), now(), 'CRY'),
    (gen_random_uuid(), now(), 'SALUTE'),
    (gen_random_uuid(), now(), 'LAUGH'),
    (gen_random_uuid(), now(), 'ANGRY'),
    (gen_random_uuid(), now(), 'QUESTION'),
    (gen_random_uuid(), now(), 'VOMIT'),
    (gen_random_uuid(), now(), 'SUN_GLASS')
;

COMMIT;
