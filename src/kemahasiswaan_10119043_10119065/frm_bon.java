/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kemahasiswaan_10119043_10119065;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author Fachriansyah PC
 */
public class frm_bon extends javax.swing.JFrame {
    koneksi dbsetting;
    String driver, database,user,pass,idBarang;
    Object tabel;
    int row = 0, tagihan = 0, kembalian = 0,bayar =0;
    /**
     * Creates new form frm_bon
     */
    public frm_bon() {
        initComponents();
        
        dbsetting = new koneksi();
        driver = dbsetting.SettingPanel("DBDriver");
        database = dbsetting.SettingPanel("DBDatabase");
        user = dbsetting.SettingPanel("DBUsername");
        pass = dbsetting.SettingPanel("DBPassword");
        
        tbl_barang.setModel(tblModelBarang);
        tbl_keranjang.setModel(tblModelKeranjang);
        settablebarangload();
        settablekeranjangload();
        btn_ubah.setEnabled(false);
        btn_hapus.setEnabled(false);
        txt_tagihan.setEditable(false);
    }
    
    public void membersihkan_input_kekeranjang(){
        jLabel2.setText("Nama Barang");
        txt_qty.setText("");
    }

    private javax.swing.table.DefaultTableModel tblModelBarang = getDefaultTableModelBarang();
    private javax.swing.table.DefaultTableModel getDefaultTableModelBarang(){
        return new javax.swing.table.DefaultTableModel(
        new Object[][]{},
        new String [] {
            "id Barang",
            "Nama Barang",
            "Harga Satuan",
        }
        )
    
        {
            boolean[] canEdit = new boolean[]
            {
                false,false,false
            };
            
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit[columnIndex];
            }
        };
    }
    
    private javax.swing.table.DefaultTableModel tblModelKeranjang = getDefaultTableModelKeranjang();
    private javax.swing.table.DefaultTableModel getDefaultTableModelKeranjang(){
        return new javax.swing.table.DefaultTableModel(
        new Object[][]{},
        new String [] {
            "Id Barang",
            "Nama Barang",
            "Jumlah",
            "Total Harga",
        }
        )
    
        {
            boolean[] canEdit = new boolean[]
            {
                false,false,false,false,false
            };
            
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit[columnIndex];
            }
        };
    }
    
    String dataBarang[]=new String[3];
    String dataKeranjang[]=new String[5];
    
    private void settablebarangload(){
        String stat = "";
        try{
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String SQL = "select * from t_barang";
            ResultSet res = stt.executeQuery(SQL);
            while(res.next()){
                dataBarang[0] = res.getString(1);
                dataBarang[1] = res.getString(2);
                dataBarang[2] = res.getString(3);
                tblModelBarang.addRow(dataBarang);
            }
            res.close();
            stt.close();
            kon.close();
        }catch(Exception ex){
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
    
    private void settablekeranjangload(){
        String stat = "";
        try{
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String SQL = "SELECT *,t_keranjang.qty * t_barang.harga as \"Total Harga\" FROM `t_keranjang` JOIN t_barang ON t_keranjang.idBarang=t_barang.id";
            ResultSet res = stt.executeQuery(SQL);
            tblModelKeranjang.getRowCount();
            tagihan = 0;
            for (int i = tblModelKeranjang.getRowCount() - 1; i >= 0; i--) {
                tblModelKeranjang.removeRow(i);
            }
            while(res.next()){
                dataKeranjang[0] = res.getString(2);
                dataKeranjang[1] = res.getString(5);
                dataKeranjang[2] = res.getString(3);
                dataKeranjang[3] = res.getString(7);
                tblModelKeranjang.addRow(dataKeranjang);
                
                tagihan += Integer.valueOf(res.getString(7));
            }
            
            txt_tagihan.setText(String.valueOf(tagihan));
            res.close();
            stt.close();
            kon.close();
        }catch(Exception ex){
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_barang = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_keranjang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txt_qty = new javax.swing.JTextField();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        txt_tagihan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_bayar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_kembalian = new javax.swing.JTextField();
        btnKonfirPembayaran = new javax.swing.JButton();
        btn_hapusall = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_pembelian = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FORM KASUS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(283, 283, 283)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        tbl_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nama Barang", "Harga Satuan"
            }
        ));
        tbl_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_barangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_barang);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Daftar Barang");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Pembelian");

        tbl_keranjang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No Nota", "Nama Barang", "Harga Satuan", "Jumlah", "Total Harga"
            }
        ));
        tbl_keranjang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_keranjangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_keranjang);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setText("Masukan Jumlah Barang");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Nama Barang");

        jButton1.setText("Tambah");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btn_ubah.setText("Ubah");
        btn_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahActionPerformed(evt);
            }
        });

        btn_hapus.setText("Hapus");
        btn_hapus.setActionCommand("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txt_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ubah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_hapus)))
                .addContainerGap(176, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(btn_ubah)
                    .addComponent(btn_hapus))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Tagihan Pembayar");

        txt_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_bayarKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Bayar");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Kembalian");

        btnKonfirPembayaran.setText("Konfirmasi Pembayaran");
        btnKonfirPembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKonfirPembayaranActionPerformed(evt);
            }
        });

        btn_hapusall.setText("Hapus Semua");
        btn_hapusall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusallActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_bayar)
                            .addComponent(txt_tagihan)
                            .addComponent(txt_kembalian, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_hapusall, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnKonfirPembayaran)
                .addGap(281, 281, 281))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tagihan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(btn_hapusall))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnKonfirPembayaran)
                .addContainerGap(131, Short.MAX_VALUE))
        );

        tbl_pembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tbl_pembelian);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Daftar Pembelian");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 84, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_barangMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            row = tbl_barang.getSelectedRow();
            idBarang = tblModelBarang.getValueAt(row, 0).toString();
            jLabel2.setText(tblModelBarang.getValueAt(row, 1).toString());
        }
    }//GEN-LAST:event_tbl_barangMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(txt_qty.getText().isEmpty() || jLabel2.getText().equals("Nama Barang")){
            JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong","Error",
                    JOptionPane.INFORMATION_MESSAGE);
        }else{
            try{
                    int qty = Integer.valueOf(txt_qty.getText());
                    Class.forName(driver);
                    Connection kon = DriverManager.getConnection(
                        database,
                        user,
                        pass);
                    Statement stt = kon.createStatement();
                    String cekKeranjang = "SELECT * FROM `t_keranjang` where idBarang ='"+idBarang+"'";
                    ResultSet res = stt.executeQuery(cekKeranjang);
                    
                    if(res.next()){
                        int oldQty = Integer.valueOf(res.getString(3));
                        String SQL = "UPDATE `t_keranjang` SET `qty` = "+(qty +oldQty) 
                        + " WHERE idBarang = '"+idBarang+"'";

                        stt.executeUpdate(SQL);
                    }else{
                        String SQL = "INSERT INTO `t_keranjang`(`idBarang`,`qty`) "
                        + "VALUES "
                        + "( '"+idBarang+"', "
                        + " '"+ qty +"')";

                        stt.executeUpdate(SQL);
                    }
//                    stt.executeUpdate(SQL);
//                    dataKeranjang[0] = idBarang;
//                    dataKeranjang[1] = qty;
//
//                    tblModelKeranjang.insertRow(0, dataKeranjang);
                    settablekeranjangload();
                    stt.close();
                    kon.close();
                    membersihkan_input_kekeranjang();
//                    btn_simpan.setEnabled(false);
//                    nonaktif_teks();
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                    ex.getMessage(),"ERROR",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbl_keranjangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_keranjangMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            row = tbl_keranjang.getSelectedRow();
            idBarang = tblModelKeranjang.getValueAt(row, 0).toString();
            jLabel2.setText(tblModelKeranjang.getValueAt(row, 1).toString());
            txt_qty.setText(tblModelKeranjang.getValueAt(row, 2).toString());
            
            btn_ubah.setEnabled(true);
            btn_hapus.setEnabled(true);
        }
    }//GEN-LAST:event_tbl_keranjangMouseClicked

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        // TODO add your handling code here:
        if(txt_qty.getText().isEmpty() || jLabel2.getText().equals("Nama Barang")){
            JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong","Error",
                    JOptionPane.INFORMATION_MESSAGE);
        }else{
            try{
                    int qty = Integer.valueOf(txt_qty.getText());
                    Class.forName(driver);
                    Connection kon = DriverManager.getConnection(
                        database,
                        user,
                        pass);
                    Statement stt = kon.createStatement();
                    String cekKeranjang = "SELECT * FROM `t_keranjang` where idBarang ='"+idBarang+"'";
                    ResultSet res = stt.executeQuery(cekKeranjang);
                    
                    if(res.next()){
                        String SQL = "UPDATE `t_keranjang` SET `qty` = "+(qty) 
                        + " WHERE idBarang = '"+idBarang+"'";
                        JOptionPane.showMessageDialog(null,"Berhasil Diubah","Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                        stt.executeUpdate(SQL);
                        membersihkan_input_kekeranjang();
                        settablekeranjangload();
                        stt.close();
                        kon.close();
                        btn_ubah.setEnabled(false);
                        btn_hapus.setEnabled(false);
                    }
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                    ex.getMessage(),"ERROR",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btnKonfirPembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKonfirPembayaranActionPerformed
        // TODO add your handling code here:
        if(txt_bayar.getText().isEmpty() || txt_kembalian.getText().isEmpty() || txt_tagihan.getText().equals(0)){
            JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong","Error",
                    JOptionPane.INFORMATION_MESSAGE);
        }else{
            try{
                    Class.forName(driver);
                    Connection kon = DriverManager.getConnection(
                        database,
                        user,
                        pass);
                    Statement stt = kon.createStatement();
                    String SQL = "SELECT *,t_keranjang.qty * t_barang.harga as \"Total Harga\" FROM `t_keranjang` JOIN t_barang ON t_keranjang.idBarang=t_barang.id";
                    ResultSet res = stt.executeQuery(SQL);
                    tblModelKeranjang.getRowCount();
                    
                    if(res.next()){
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime waktuSkrng = LocalDateTime.now();
                        String QT_nota = "INSERT INTO `t_nota`(`total`,`bayar`,`kembalian`,`tglTransaksi`) "
                            + "VALUES "
                            + "( '"+tagihan+"', "
                            + " '" + bayar + "', "
                            + " '" + kembalian + "', "
                            + " '"+ waktuSkrng +"')";

                        int newNota = stt.executeUpdate(QT_nota,Statement.RETURN_GENERATED_KEYS);
                        do
                        {
                            String QT_nota_item = "INSERT INTO `t_nota_item`(`noNota`,`idBarang`,`qty`,`totalHarga`) "
                            + "VALUES "
                            + "( '"+newNota+"', "
                            + " '" + Integer.parseInt(res.getString(2)) + "', "
                            + " '" + Integer.parseInt(res.getString(3)) + "', "
                            + " '" + Integer.parseInt(res.getString(7)) + "')";

                            stt.executeUpdate(QT_nota_item);
//                            dataKeranjang[0] = res.getString(2);
//                            dataKeranjang[1] = res.getString(5);
//                            dataKeranjang[2] = res.getString(3);
//                            dataKeranjang[3] = res.getString(7);
//                            tblModelKeranjang.addRow(dataKeranjang);
//
//                            tagihan += Integer.valueOf(res.getString(7));
                        }
                        while (res.next());
                        String Qhpskeranjang = "DELETE FROM t_keranjang ";
                        stt.executeUpdate(Qhpskeranjang);
                    }
                    settablekeranjangload();
                    stt.close();
                    kon.close();
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                    ex.getMessage(),"ERROR",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnKonfirPembayaranActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
         try{
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String  SQL = "DELETE FROM t_keranjang "
            + " where "
            + " idBarang = '"+idBarang+"'";
            stt.executeUpdate(SQL);
            tblModelKeranjang.removeRow(row);
            JOptionPane.showMessageDialog(null,"Berhasil Dihapus Dari Keranjang","Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
            stt.close();
            kon.close();
            settablekeranjangload();
            membersihkan_input_kekeranjang();
            btn_ubah.setEnabled(false);
            btn_hapus.setEnabled(false);
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_hapusallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusallActionPerformed
        // TODO add your handling code here:
         try{
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String  SQL = "DELETE FROM t_keranjang ";
            stt.executeUpdate(SQL);
            tblModelKeranjang.removeRow(row);
            JOptionPane.showMessageDialog(null,"Berhasil Hapus Semua Barang Dari Keranjang","Pesan",
                            JOptionPane.INFORMATION_MESSAGE);
            stt.close();
            kon.close();
            settablekeranjangload();
            membersihkan_input_kekeranjang();
            btn_ubah.setEnabled(false);
            btn_hapus.setEnabled(false);
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }//GEN-LAST:event_btn_hapusallActionPerformed

    private void txt_bayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyPressed
        // TODO add your handling code here:
        bayar = Integer.parseInt(txt_bayar.getText());
        kembalian = bayar - tagihan ;
        txt_kembalian.setText(String.valueOf(kembalian));
    }//GEN-LAST:event_txt_bayarKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frm_bon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_bon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_bon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_bon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_bon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKonfirPembayaran;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_hapusall;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tbl_barang;
    private javax.swing.JTable tbl_keranjang;
    private javax.swing.JTable tbl_pembelian;
    private javax.swing.JTextField txt_bayar;
    private javax.swing.JTextField txt_kembalian;
    private javax.swing.JTextField txt_qty;
    private javax.swing.JTextField txt_tagihan;
    // End of variables declaration//GEN-END:variables
}
