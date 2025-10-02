package models;

import java.util.List;

public class BaseModel {

    private String tableName;
    private List<FieldItem> fields;
    private int id;
    
    public BaseModel(String tableName) {
        this.tableName = tableName;
    }
    
    public BaseModel(BaseModel b){
        this.fields = b.getInfo();
        this.tableName = b.getTableName();
    }
    public int getId() {
        return Integer.parseInt(getValue("ID").toString());
    }

    public void setId(int id) {
         setValue("ID", id);
    }

    public void setFields(List<FieldItem> fields) {
        this.fields = fields;
    }

   

    public String getTableName() {
        return tableName;
    }

    public Object getValue(String columnName) {
        for (FieldItem item : fields) {
            if (item.getField().equalsIgnoreCase(columnName)) {
                return item.getValue();
            }
        }
        return null;
    }
    public void setValue(String columnName, Object value) {
        for (FieldItem item : fields) {
            if (item.getField().equalsIgnoreCase(columnName)) {
                item.setValue(value);
            }
        }
    }

    public List<FieldItem> getInfo() {
        return fields;
    }
}
