package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Barang;
import resource.Koneksi;

/**
 *
 * @author bayug
 */
public class BarangDao {
    Connection con;
    public BarangDao() {
        con = (new Koneksi()).getConnection();
    }
    
    public void insert(Barang barang) throws SQLException {
        String sql = "INSERT INTO barang VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, barang.getId());
        ps.setString(2, barang.getNama());
        ps.setString(3, barang.getTipe());
        ps.setString(4, barang.getSatuan());
        ps.setDouble(5, barang.getHrg_modal());
        ps.setDouble(6, barang.getHrg_jual());
        ps.setInt(7, barang.getStok());
        ps.setInt(8, barang.getIsi());
        ps.setString(9, barang.getId_pecah());
        ps.executeUpdate();
    }
    
    public void tambahbarang(Barang barang) throws SQLException{
        String sql="call tambahstok(?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, barang.getId());
        ps.setInt(2, barang.getStok());
        ps.executeUpdate();
    }
    
    public void update(Barang barang) throws SQLException{
        String sql="update barang set nama_barang=?, tipe_barang=?, satuan=?, "
                + "harga_modal=?, harga_jual=?, stok=?, isi=?, id_pecah=? where id_barang=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, barang.getNama());
        ps.setString(2, barang.getTipe());
        ps.setString(3, barang.getSatuan());
        ps.setDouble(4, barang.getHrg_modal());
        ps.setDouble(5, barang.getHrg_jual());
        ps.setInt(6, barang.getStok());
        ps.setInt(7, barang.getIsi());
        ps.setString(8, barang.getId_pecah());
        ps.setString(9, barang.getId());
        ps.executeUpdate(); 
    }
    
    public void delete(Barang barang) throws SQLException{
        String sql ="delete from barang where id_barang = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, barang.getId());
        ps.executeUpdate();
    }
    
    public DefaultTableModel selectAllDataToTableModel() {
        DefaultTableModel tableModel = new DefaultTableModel(); tableModel.setRowCount(0); tableModel.setColumnCount(0);
        tableModel.addColumn("ID"); tableModel.addColumn("Nama"); tableModel.addColumn("Tipe"); tableModel.addColumn("Satuan");
        tableModel.addColumn("Harga Modal"); tableModel.addColumn("Harga Jual"); tableModel.addColumn("Stok");
        tableModel.addColumn("Isi"); tableModel.addColumn("ID Satuan");
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM barang");
            while (rs.next()) {
                Object[] data = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
                    rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)};
                tableModel.addRow(data);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return tableModel;
    }
    
    public DefaultTableModel searchByAllToTable(String word) {
        DefaultTableModel tableModel = new DefaultTableModel(); tableModel.setRowCount(0); tableModel.setColumnCount(0);
        tableModel.addColumn("ID"); tableModel.addColumn("Nama"); tableModel.addColumn("Tipe"); tableModel.addColumn("Satuan");
        tableModel.addColumn("Harga Modal"); tableModel.addColumn("Harga Jual"); tableModel.addColumn("Stok");
        tableModel.addColumn("Isi"); tableModel.addColumn("ID Satuan");
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM barang WHERE id_barang LIKE '%"+word+"%' OR "
                    + "nama_barang LIKE '%"+word+"%' OR tipe_barang LIKE '%"+word+"%'");
            while (rs.next()) {
                Object[] data = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
                    rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)};
                tableModel.addRow(data);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return tableModel;
    }
    
    public boolean isAvailable(String kode, Barang barang) {
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM barang WHERE id_barang='"+kode+"'");
            if (rs.next()) {
                barang.setId(rs.getString(1));
                barang.setNama(rs.getString(2));
                barang.setTipe(rs.getString(3));
                barang.setSatuan(rs.getString(4));
                barang.setHrg_modal(rs.getDouble(5));
                barang.setHrg_jual(rs.getDouble(6));
//                barang.setStok(rs.getInt(7));
                barang.setIsi(rs.getInt(8));
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            return false;
        }
    }
}
