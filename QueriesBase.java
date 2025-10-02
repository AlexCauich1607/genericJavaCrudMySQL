/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexc
 */
public class QueriesBase extends ConnectionDB {

    private String tableName;
    private BaseModel baseModel;

    public QueriesBase(BaseModel b) {
        this.tableName = b.getTableName();
        this.baseModel = b;

    }

    public String getTableName() {
        return tableName;
    }

    
    
    public boolean create(BaseModel b) {
        PreparedStatement ps = null;
        Connection con = getConnection();
        String fieldsInto = "";
        String fieldsValues = "";
        List<FieldItem> list = b.getInfo();
        for (int i = 1; i < list.size(); i++) {
            if (i == 1) {
                fieldsInto += list.get(i).getField();
                fieldsValues += "?";
            } else {
                fieldsInto += ", " + list.get(i).getField();
                fieldsValues += ", ?";
            }
        }
        String sql = "INSERT INTO " + tableName + " (" + fieldsInto + ") VALUES(" + fieldsValues + ")";
        try {
            ps = con.prepareStatement(sql);
            for (int i = 1; i < list.size(); i++) {
                String type = b.getInfo().get(i).getType();
                Object value = list.get(i).getValue();

                switch (type) {
                    case "String":
                        ps.setString(i, value.toString());
                        break;

                    case "int":
                        ps.setInt(i, Integer.parseInt(value.toString()));
                        break;

                    case "double":
                        ps.setDouble(i, Double.parseDouble(value.toString()));
                        break;

                    default:
                        throw new IllegalArgumentException("Tipo no soportado: " + type);
                }
            }
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Hola:" + e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.err.println("1 " + ex);
            }
        }
    }
    
    
    
    
    
    
    

    public BaseModel read(int id) {
        BaseModel model = new BaseModel(baseModel);
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        String sql = "SELECT * FROM " + tableName + " WHERE ID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {

                List<FieldItem> list = model.getInfo();

                for (int i = 0; i < list.size(); i++) {

                    String type = baseModel.getInfo().get(i).getType();
                    String field = list.get(i).getField();

                    switch (type) {
                        case "String":
                            list.set(i, new FieldItem(field, type, rs.getString(field)));
                            break;

                        case "int":
                            list.set(i, new FieldItem(field, type, Integer.parseInt(rs.getString(field))));
                            break;

                        case "double":
                            list.set(i, new FieldItem(field, type, Double.parseDouble(rs.getString(field))));
                            break;
                        default:
                            throw new IllegalArgumentException("Tipo no soportado: " + type);
                    }
                }
                return model;
            }
            return null;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }
    }
    
    
    
    
    

    public boolean update(BaseModel a) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String fieldsValues = "";
        List<FieldItem> list = a.getInfo();

        for (int i = 1; i < list.size(); i++) {
            if (i == 1) {
                fieldsValues += list.get(i).getField() + " = ?";
            } else {
                fieldsValues += ", " + list.get(i).getField() + " = ?";
            }
        }

        String sql = "UPDATE "+tableName+" SET " + fieldsValues + " WHERE ID = ?";

        try {
            ps = con.prepareStatement(sql);

            for (int i = 1; i < list.size(); i++) {
                String type = a.getInfo().get(i).getType();
                Object value = list.get(i).getValue();

                switch (type) {
                    case "String":
                      
                        ps.setString(i, value.toString());
                        break;

                    case "int":
                        ps.setInt(i, Integer.parseInt(value.toString()));
                        break;

                    case "double":
                        ps.setDouble(i, Double.parseDouble(value.toString()));
                        break;

                    default:
                        throw new IllegalArgumentException("Tipo no soportado: " + type);
                }
            }
            ps.setInt(list.size(), a.getId());
           
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }
    }

    public boolean delete(int id) {
        PreparedStatement ps = null;
        Connection con = getConnection();
        String sql = "DELETE FROM " + baseModel.getTableName() + " WHERE ID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }

    }

    public List<BaseModel> readAll() {
        PreparedStatement ps = null;
        Connection con = getConnection();
        ResultSet rs = null;
        String sql = "SELECT * FROM " + tableName;
        List<BaseModel> listModels = new ArrayList<>();

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            List<FieldItem> templateFields = baseModel.getInfo();

            while (rs.next()) {
                List<FieldItem> modelFields = new ArrayList<>();
                for (int i = 0; i < templateFields.size(); i++) {
                    String type = templateFields.get(i).getType();
                    String field = templateFields.get(i).getField();

                    Object value;
                    switch (type) {
                        case "String":
                            value = rs.getString(field);
                            break;
                        case "int":
                            value = rs.getInt(field);
                            break;
                        case "double":
                            value = rs.getDouble(field);
                            break;
                        default:
                            throw new IllegalArgumentException("Tipo no soportado: " + type);
                    }
                    modelFields.add(new FieldItem(field, type, value));
                }
                BaseModel al = new BaseModel(baseModel.getTableName());
                al.setFields(modelFields);
                listModels.add(al);
            }
            return listModels;
        } catch (SQLException e) {
            System.err.println(e);
            return listModels;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }
    }

}
