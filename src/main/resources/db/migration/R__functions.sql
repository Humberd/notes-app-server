create or replace function trigger_set_timestamp()
    returns trigger as
$$
begin
    new.updated_at = now();
    return new;
end;
$$ language plpgsql;

create or replace function trigger_increment_resource_revisions_count()
    returns trigger as
$$
begin
    update resource
    set revisions_count = revisions_count + 1
    where id = new.resource_id;

    return new;
end;
$$ language plpgsql;
