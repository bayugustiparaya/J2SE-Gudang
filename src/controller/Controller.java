/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BarangDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Barang;
import resource.Koneksi;
import view.FormBarang;

/**
 *
 * @author bayug
 */
public class Controller {
    FormBarang view;
    Barang barang;
    BarangDao barangDao;
    Koneksi k;
    Connection con;

    public Barang getBarang() {
        return barang;
    }
    
    public Controller(FormBarang view){
        this.view = view;
        barangDao = new BarangDao();
        k = new Koneksi();
        con = k.getConnection();
        view.getjTable1().setModel(barangDao.selectAllDataToTableModel());
    }
    
    public void searchOnBarang(String word) {
        view.getjTable1().setModel(barangDao.searchByAllToTable(word));
    }
    
    public void cekBarang(String kode){
        barang = new Barang();
        if (barangDao.isAvailable(kode, barang)) {
            view.getKodeBesar().setText(barang.getId());
            view.getNamaBesar().setText(barang.getNama());
            view.getTipeBesar().setSelectedItem(barang.getTipe());
            view.getSatuanBesar().setSelectedItem(barang.getSatuan());
            view.getModalBesar().setText(""+barang.getHrg_modal());
            view.getJualBesar().setText(""+barang.getHrg_jual());
//            view.getBanyakBesar().setText(""+barang.getStok());
            view.getIsiBesar().setText(""+barang.getIsi());
            
            view.getNamaBesar().setEditable(false);
            view.getTipeBesar().setEnabled(false);
            view.getSatuanBesar().setEnabled(false);
            view.getModalBesar().setEditable(false);
            view.getJualBesar().setEditable(false);
            view.getIsiBesar().setEditable(false);
            view.getAddSatuanBesar().setVisible(false);
            view.getAddTipeBesar().setVisible(false);
            
            view.getBanyakBesar().requestFocus();
        } else {
            JOptionPane.showMessageDialog(view, "Data belum ada di database.\nSilahkan lengkapi data");
        }
    }
}
