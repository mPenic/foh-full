CREATE OR REPLACE FUNCTION log_reservation_actions()
RETURNS trigger AS $$
DECLARE
    v_action_type VARCHAR(50);
    v_description VARCHAR(255);
    v_action_by INT;
    v_company_id INT;
BEGIN
    v_company_id := NEW.CompanyId;  -- CompanyId from the NEW row (on UPDATE/INSERT)
    
    IF TG_OP = 'INSERT' THEN
        -- On INSERT
        v_action_type := 'INSERT';
        v_action_by := NEW.CreatedBy; 
        v_description := 'Created new reservation ' || NEW.ReservationName;

        INSERT INTO ActionLogs (ReservationId, ActionBy, ActionType, Description, CompanyId)
        VALUES (NEW.ReservationId, v_action_by, v_action_type, v_description, v_company_id);

        RETURN NEW;

    ELSIF TG_OP = 'UPDATE' THEN
        -- On UPDATE
        -- Determine who performed the update. Try ReservationMetadata first:
        SELECT COALESCE(UpdatedBy, CompletedBy, OLD.CreatedBy)
          INTO v_action_by
        FROM ReservationMetadata
        WHERE ReservationId = NEW.ReservationId;

        IF v_action_by IS NULL THEN
            v_action_by := OLD.CreatedBy; 
        END IF;

        v_action_type := 'UPDATE';
        v_description := 'Updated reservation ' || NEW.ReservationName;

        INSERT INTO ActionLogs (ReservationId, ActionBy, ActionType, Description, CompanyId)
        VALUES (NEW.ReservationId, v_action_by, v_action_type, v_description, v_company_id);

        RETURN NEW;

    --ELSIF TG_OP = 'DELETE' THEN
    --    -- On DELETE
    --    -- For DELETE, we only have OLD values.
    --    -- Determine who performed the delete. This might depend on your logic.
    --    -- If you have no direct way to know, you might rely on OLD.CreatedBy or a session variable.
    --    -- For now, we’ll assume OLD.CreatedBy is the actor.
    --    v_action_by := OLD.CreatedBy;
    --    v_action_type := 'DELETE';
    --    v_description := 'Deleted reservation ' || OLD.ReservationName;
    --    
    --    -- CompanyId can come from OLD in case of delete
    --    v_company_id := OLD.CompanyId;
--
    --    INSERT INTO ActionLogs (ReservationId, ActionBy, ActionType, Description, CompanyId)
    --    VALUES (OLD.ReservationId, v_action_by, v_action_type, v_description, v_company_id);
--
    --    RETURN OLD;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- Trigger: includes DELETE to log deletions as well
DROP TRIGGER IF EXISTS reservations_action_log ON Reservations;

CREATE TRIGGER reservations_action_log
AFTER INSERT OR UPDATE OR DELETE ON Reservations
FOR EACH ROW
EXECUTE PROCEDURE log_reservation_actions();