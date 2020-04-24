CREATE OR REPLACE FUNCTION trigger_set_timestamp()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION trigger_increment_notes_count()
    RETURNS TRIGGER AS
$$
BEGIN
    update Tag
    set notes_count = notes_count + 1
    where id = NEW.tag_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION trigger_decrement_notes_count()
    RETURNS TRIGGER AS
$$
BEGIN
    update Tag
    set notes_count = notes_count - 1
    where id = OLD.tag_id;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION trigger_set_search_vector()
    RETURNS TRIGGER AS
$$
BEGIN
    new.search_vector = setweight(to_tsvector('simple', new.title), 'D') ||
                        setweight(to_tsvector('simple', coalesce(new.url, '')), 'C') ||
                        setweight(to_tsvector('simple', coalesce(new.content, '')), 'B');

    return new;
END;
$$ LANGUAGE plpgsql;
