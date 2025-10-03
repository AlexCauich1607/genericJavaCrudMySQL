
/**
 *
 * @author alexc
 */
public class FieldItem {

    private String field;
    private String type;
    private Object value;

    public FieldItem(String field, String type, Object value) {
        this.field = field;
        this.type = type;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

