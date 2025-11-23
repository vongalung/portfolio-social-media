BEGIN;

CREATE TABLE social_media.reaction (
	id uuid NOT NULL DEFAULT gen_random_uuid(),
	created_at timestamptz(6) NULL DEFAULT now(),
	inactive_at timestamptz(6) NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT reaction_pkey PRIMARY KEY (id),
	CONSTRAINT reaction_name_unique UNIQUE (name)
);

CREATE TABLE social_media."user" (
	id uuid NOT NULL DEFAULT gen_random_uuid(),
	created_at timestamptz(6) NULL DEFAULT now(),
	unique_id varchar(255) NOT NULL,
	username varchar(255) NOT NULL,
	CONSTRAINT user_uniqueid_unique UNIQUE (unique_id),
	CONSTRAINT user_pkey PRIMARY KEY (id)
);

CREATE TABLE social_media.user_content (
	"type" varchar(31) NOT NULL,
	id uuid NOT NULL DEFAULT gen_random_uuid(),
	"content" text NULL,
	created_at timestamptz(6) NULL DEFAULT now(),
	title varchar(255) NULL,
	user_id uuid NOT NULL,
	parent_id uuid NULL,
	CONSTRAINT user_content_pkey PRIMARY KEY (id),
	CONSTRAINT user_content_type_check CHECK (((type)::text = ANY ((ARRAY['COMMENT'::character varying, 'POST'::character varying])::text[]))),
	CONSTRAINT user_content_parent_fk FOREIGN KEY (parent_id) REFERENCES social_media.user_content(id),
	CONSTRAINT user_content_user_fk FOREIGN KEY (user_id) REFERENCES social_media."user"(id)
);

CREATE TABLE social_media.user_reaction (
	id uuid NOT NULL DEFAULT gen_random_uuid(),
	created_at timestamptz(6) NULL DEFAULT now(),
	content_id uuid NOT NULL,
	reaction_id uuid NOT NULL,
	user_id uuid NOT NULL,
	CONSTRAINT user_reaction_pkey PRIMARY KEY (id),
	CONSTRAINT user_reaction_reaction_fk FOREIGN KEY (reaction_id) REFERENCES social_media.reaction(id),
	CONSTRAINT user_reaction_content_fk FOREIGN KEY (content_id) REFERENCES social_media.user_content(id),
	CONSTRAINT user_reaction_user_fk FOREIGN KEY (user_id) REFERENCES social_media."user"(id)
);

COMMIT;
