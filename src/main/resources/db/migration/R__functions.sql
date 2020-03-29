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

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION trigger_update_comments_count()
    RETURNS TRIGGER AS
$$
BEGIN
    if OLD is null then
        if NEW.is_upvote = true then
            perform update_votes_score(NEW.note_id, 1);
        else
            perform update_votes_score(NEW.note_id, -1);
        end if;
    elsif NEW is null then
        if OLD.is_upvote = true then
            perform update_votes_score(OLD.note_id, -1);
        else
            perform update_votes_score(OLD.note_id, 1);
        end if;
    elsif OLD.is_upvote is distinct from NEW.is_upvote then
        if NEW.is_upvote = true then
            perform update_votes_score(NEW.note_id, 2);
        else
            perform update_votes_score(NEW.note_id, -2);
        end if;
    end if;


    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_votes_score(note_id varchar(32), update_by integer)
    RETURNS void AS
$$
BEGIN
    update Note
    set votes_score = votes_score + update_by
    where id = note_id;
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
    new.search_vector = setweight(to_tsvector('english', new.title), 'D') ||
                        setweight(to_tsvector('english', coalesce(new.url, '')), 'C') ||
                        setweight(to_tsvector('english', coalesce(new.content, '')), 'B');

    return new;
END;
$$ LANGUAGE plpgsql;
