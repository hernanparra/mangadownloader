package md.model;

/**
 *
 * @author Hernan
 */
public interface EventsHandler {
    void event(EventTypes event, String description);

    public enum EventTypes {
        HighLevelEvent, 
        LowLevelEvent,
    }
}
