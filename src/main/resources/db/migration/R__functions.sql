CREATE OR REPLACE FUNCTION trigger_set_timestamp()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION trigger_increment_comments_count()
    RETURNS TRIGGER AS
$$
BEGIN
    update Note
    set comments_count = comments_count + 1
    where id = NEW.note_id;

    raise notice 'INCREMENT: %', NEW;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION trigger_decrement_comments_count()
    RETURNS TRIGGER AS
$$
BEGIN
    update Note
    set comments_count = comments_count - 1
    where id = OLD.note_id;

    raise notice 'DECREMENT: %', OLD;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;
