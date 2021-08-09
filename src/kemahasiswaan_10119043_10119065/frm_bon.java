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
    int row = 0, tagihan = 0, kembalian = 0,bayar =0, noNota, itemBarang,hargaSatuan;
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
        tbl_pembelian.setModel(tblModelNota);
        settablebarangload();
        settablekeranjangload();
        settablenotaload();
        btn_ubah.setEnabled(false);
        btn_hapus.setEnabled(false);
        txt_tagihan.setEditable(false);
        setModelComboBarang();
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
    
    private javax.swing.table.DefaultTableModel tblModelNota = getDefaultTableModelNota();
    private javax.swing.table.DefaultTableModel getDefaultTableModelNota(){
        return new javax.swing.table.DefaultTableModel(
        new Object[][]{},
        new String [] {
            "No Nota",
            "Id Barang",
            "Nama Barang",
            "Qty",
            "Total Harga",
            "Tanggal Transaksi",
        }
        )
    
        {
            boolean[] canEdit = new boolean[]
            {
                false,false,false,false,false,false
            };
            
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit[columnIndex];
            }
        };
    }
    
    String dataBarang[]=new String[3];
    String dataKeranjang[]=new String[5];
    String dataNota[] = new String[9];
    
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
    
    private void settablenotaload(){
        String stat = "";
        try{
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String SQL = "select * from `t_nota_item` "
                    +"JOIN `t_nota` ON `t_nota_item`.`noNota` = `t_nota`.`noNota` "
                    +"JOIN `t_barang` ON `t_nota_item`.`idBarang` = `t_barang`.`id`";
            ResultSet res = stt.executeQuery(SQL);
            for (int i = tblModelNota.getRowCount() - 1; i >= 0; i--) {
                tblModelNota.removeRow(i);
            }
            while(res.next()){
                dataNota[0] = res.getString(2);
                dataNota[1] = res.getString(3);
                dataNota[2] = res.getString(12);
                dataNota[3] = res.getString(4);
                dataNota[4] = res.getString(5);
                dataNota[5] = res.getString(10);
                tblModelNota.addRow(dataNota);
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
    
//    int rows = 0;
    private void tampilField() {
//        System.out.println(tblModelNota.getValueAt(row, 0).toString());
        row = tbl_pembelian.getSelectedRow();
        noNota = Integer.valueOf(tblModelNota.getValueAt(row, 0).toString()); 
        itemBarang = Integer.valueOf(tblModelNota.getValueAt(row, 1).toString()); 
        comboBarang.setSelectedItem(tblModelNota.getValueAt(row, 2).toString());
        txt_jumlah.setText(tblModelNota.getValueAt(row, 3).toString());
        txt_totHarga.setText(tblModelNota.getValueAt(row, 4).toString());
        jLabel14.setText(String.valueOf(getTagihan(tblModelNota.getValueAt(row, 2).toString(), Integer.valueOf(tblModelNota.getValueAt(row, 0).toString()))));
    }
    
    private void setModelComboBarang(){
       try{
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String SQL = "select nama_barang from t_barang";
            ResultSet res = stt.executeQuery(SQL);
            comboBarang.removeAllItems();
            comboBarang.addItem("-- Pilih --");
            while(res.next()){
                comboBarang.addItem(res.getString(1));
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
    
    private int getTagihan(String nama, int noNota) {
       int totalTagihan = 0;
        try{
            Class.forName(driver);
            
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String SQL = "select t_nota.total \n" +
                            "from t_nota\n" +
                            "join  t_nota_item on t_nota.noNota=t_nota_item.noNota\n" +
                            "join t_barang on t_nota_item.idBarang=t_barang.id\n" +
                            "where t_barang.`nama_barang`='"+nama+"' AND t_nota_item.`noNota`="+noNota+";";
            ResultSet res = stt.executeQuery(SQL);
            while(res.next()){
                totalTagihan = Integer.valueOf(res.getString(1));
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
        return totalTagihan;
    }
    
    public void membersihkan_teks(){
        comboBarang.setSelectedIndex(0);
        txt_jumlah.setText("1");
        txt_totHarga.setText("");
        jLabel14.setText("0");
        jTextField2.setText("");
        jTextField3.setText("");
    }
    
    public int getIdBarangFromDB(String nama_barang) {
        int id = 0;
         try{
            Class.forName(driver);
            
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String SQL = "select id from t_barang where `nama_barang`='"+nama_barang+"';";
            ResultSet res = stt.executeQuery(SQL);
            while(res.next()){
                id = Integer.valueOf(res.getString(1).toString());
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
        return id;
    }
    
    public Boolean getNamaBarangFromDB() {
        String nama = "";
        System.out.println(comboBarang.getSelectedItem());
        System.out.println(itemBarang);
        System.out.println(getIdBarangFromDB(String.valueOf(comboBarang.getSelectedItem())));
        if (comboBarang.getSelectedIndex() > 0 && getIdBarangFromDB(String.valueOf(comboBarang.getSelectedItem())) == itemBarang) {
            return false;
        }
         try{
            Class.forName(driver);
            
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String SQL = "select t_barang.nama_barang from t_nota_item join t_barang on t_nota_item.idBarang=t_barang.id where t_nota_item.`idBarang`='"+getIdBarangFromDB(String.valueOf(comboBarang.getSelectedItem()))+"' AND t_nota_item.`noNota`='"+ noNota +"';";
            ResultSet res = stt.executeQuery(SQL);
            if(res.next()){
                return true;
            } else {
                return false;
            }
//            res.close();
//            stt.close();
//            kon.close();
        }catch(Exception ex){
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
            return false;
        }
    }
//    private void HargaByNamaBarang(String nama,int hargaSatuan) {
//        
//        int harga = 0;
//        int totHarga = 0;
//        try{
//            Class.forName(driver);
//            Connection kon = DriverManager.getConnection(database,user,pass);
//            Statement stt = kon.createStatement();
//            String SQL = "select (t_barang.harga * t_nota_item.qty) as totalHargaPerItem, harga\n" +
//                            "from t_barang\n" +
//                            "JOIN t_nota_item ON t_barang.id = t_nota_item.idBarang\n" +
//                            "WHERE t_barang.nama_barang = '"+nama+"';";
//            ResultSet res = stt.executeQuery(SQL);
//            while(res.next()){
//                harga = Integer.valueOf(res.getString(1));
////                totHarga = totHarga + harga;
//            }
////            int totalHargaKeseluruhan = harga + hargaSatuan;
//            System.out.println(hargaSatuan);
////            jLabel14.setText(String.valueOf(totalHargaKeseluruhan));
//            res.close();
//            stt.close();
//            kon.close();
//        }catch(Exception ex){
//            System.err.println(ex.getMessage());
//            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
//                    JOptionPane.INFORMATION_MESSAGE);
//            System.exit(0);
//        }
//        
//    }
    
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
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_pembelian = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_jumlah = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_totHarga = new javax.swing.JTextField();
        comboBarang = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txt_cari_nota = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

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
        jLabel4.setText("Keranjang");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bayarKeyReleased(evt);
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnKonfirPembayaran)
                .addGap(191, 191, 191))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        tbl_pembelian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_pembelianMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_pembelian);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Nota Pembelian");

        jLabel16.setText("Kembalian");

        txt_jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlahActionPerformed(evt);
            }
        });
        txt_jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_jumlahKeyReleased(evt);
            }
        });

        jLabel12.setText("Jumlah");

        jLabel6.setText("Total Harga");

        txt_totHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totHargaActionPerformed(evt);
            }
        });

        comboBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Barang --" }));
        comboBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBarangActionPerformed(evt);
            }
        });

        jButton2.setText("Ubah");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel13.setText("Tagihan Pembayaran");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jLabel14.setText("0");

        jLabel15.setText("Bayar");

        jLabel11.setText("Nama Barang");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Forn Pencarian Data", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION)));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setText("No Nota");

        txt_cari_nota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cari_notaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(txt_cari_nota, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(txt_cari_nota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel6)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_totHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField2)
                                .addComponent(jLabel14)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jButton2)))
                .addContainerGap(291, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(comboBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_totHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(42, 42, 42)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
                .addGap(519, 519, 519)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_hapusallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusallActionPerformed
        // TODO add your handling code here:
        try{
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String  SQL = "DELETE FROM t_keranjang ";
            stt.executeUpdate(SQL);
            tblModelKeranjang.removeRow(row);
            stt.close();
            kon.close();
            settablekeranjangload();
            membersihkan_input_kekeranjang();
            JOptionPane.showMessageDialog(null,"Berhasil Hapus Semua Barang Dari Keranjang","Pesan",
                JOptionPane.INFORMATION_MESSAGE);
            btn_ubah.setEnabled(false);
            btn_hapus.setEnabled(false);
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }//GEN-LAST:event_btn_hapusallActionPerformed

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
                String SQL = "SELECT *,t_keranjang.qty * t_barang.harga as `Total Harga` FROM `t_keranjang` JOIN t_barang ON t_keranjang.idBarang=t_barang.id";
                ResultSet res = stt.executeQuery(SQL);
                int rowCart = tblModelKeranjang.getRowCount();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime waktuSkrng = LocalDateTime.now();
                String QT_nota = "INSERT INTO `t_nota`(`total`,`bayar`,`kembalian`,`tglTransaksi`) "
                + "VALUES "
                + "( '"+tagihan+"', "
                + " '" + bayar + "', "
                + " '" + kembalian + "', "
                + " '"+ waktuSkrng +"')";
                stt.executeUpdate(QT_nota,Statement.RETURN_GENERATED_KEYS);
                ResultSet newNotaKey = stt.getGeneratedKeys();

                newNotaKey.next();
                int noNota=Integer.valueOf(newNotaKey.getString(1));
                String QT_nota_item = "INSERT INTO `t_nota_item`(`noNota`,`idBarang`,`qty`,`totalHarga`) VALUES ";

                int i = 0;
                ResultSet res2 = stt.executeQuery(SQL);
                while( res2.next()){
                    QT_nota_item += "( '"+noNota+"', '"
                    + Integer.parseInt(res2.getString(2)) + "', "
                    + " '" + Integer.parseInt(res2.getString(3)) + "', '"+Integer.parseInt(res2.getString(7))+"' )";
                    if((i+1) == rowCart){
                        QT_nota_item += "; ";
                    }else{
                        QT_nota_item += ", ";
                    }
                    i++;
                }
                stt.executeUpdate(QT_nota_item);
                String Qhpskeranjang = "DELETE FROM t_keranjang ";
                stt.executeUpdate(Qhpskeranjang);
                settablekeranjangload();
                settablenotaload();
                stt.close();
                kon.close();

                txt_bayar.setText("");
                txt_kembalian.setText("");
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                    ex.getMessage(),"ERROR",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnKonfirPembayaranActionPerformed

    private void txt_bayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyReleased
        // TODO add your handling code here:
        bayar = Integer.parseInt(txt_bayar.getText());
        kembalian = bayar - tagihan ;
        txt_kembalian.setText(String.valueOf(kembalian));
    }//GEN-LAST:event_txt_bayarKeyReleased

    private void txt_bayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyPressed
        // TODO add your handling code here:
        //        if(txt_bayar.getText().equals(" ")){
            //            System.out.println("aaaa");
            //        }
        //        bayar = Integer.parseInt(txt_bayar.getText());
        //        kembalian = bayar - tagihan ;
        //        txt_kembalian.setText(String.valueOf(kembalian));
    }//GEN-LAST:event_txt_bayarKeyPressed

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
                settablekeranjangload();
                stt.close();
                kon.close();
                membersihkan_input_kekeranjang();
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

    private void tbl_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_barangMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            row = tbl_barang.getSelectedRow();
            idBarang = tblModelBarang.getValueAt(row, 0).toString();
            jLabel2.setText(tblModelBarang.getValueAt(row, 1).toString());
        }
    }//GEN-LAST:event_tbl_barangMouseClicked

    private void tbl_pembelianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_pembelianMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            tampilField();
        }
    }//GEN-LAST:event_tbl_pembelianMouseClicked

    private void txt_jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlahActionPerformed

    private void txt_totHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totHargaActionPerformed

    private void comboBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBarangActionPerformed
        // TODO add your handling code here:
        Object barang = comboBarang.getSelectedItem();
        txt_jumlah.setText("1");
        
        System.out.println(comboBarang.getSelectedIndex());
        try {
                Class.forName(driver);
                Connection kon = DriverManager.getConnection(database,user,pass);
                Statement stt = kon.createStatement();
                String QUERY = "SELECT totalHarga FROM t_nota_item WHERE noNota='"+ noNota +"' AND t_nota_item.idBarang != '"+ itemBarang +"'";
                String SQL = "select harga from t_barang where `nama_barang` = '"+barang+"';" ;
                ResultSet asd = stt.executeQuery(QUERY);
                int totalHarga = 0;
                while (asd.next()) {
                    totalHarga +=  Integer.valueOf(asd.getString(1));
                }
                
                ResultSet res = stt.executeQuery(SQL);
//                res.next();
                
                while (res.next()) {
//                    System.out.println(res.getString(1));
                    hargaSatuan = Integer.valueOf(res.getString(1));
                    
                }
//                HargaByNamaBarang((String) barang,hargaSatuan);
                txt_totHarga.setText(String.valueOf(hargaSatuan));
                jLabel14.setText(String.valueOf(totalHarga+hargaSatuan));
//                res.close();
                asd.close();
                stt.close();
                kon.close();
            
            
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }//GEN-LAST:event_comboBarangActionPerformed

    private void txt_jumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlahKeyReleased
        // TODO add your handling code here:
        int jumlah = Integer.valueOf(txt_jumlah.getText());
        int totalharga = jumlah * hargaSatuan;
        txt_totHarga.setText(String.valueOf(totalharga));
        try {
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String QUERY = "SELECT totalHarga FROM t_nota_item WHERE noNota='"+ noNota +"' AND t_nota_item.idBarang != '"+ itemBarang +"'";
            ResultSet asd = stt.executeQuery(QUERY);
            int totalHarga = 0;
            while (asd.next()) {
                totalHarga +=  Integer.valueOf(asd.getString(1));
            }
            jLabel14.setText(String.valueOf(totalharga+totalHarga));
            asd.close();
            stt.close();
            kon.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }//GEN-LAST:event_txt_jumlahKeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
        int bayar = Integer.parseInt(jTextField2.getText());
        int tagihan = Integer.parseInt(jLabel14.getText());
        int kembalian = bayar - tagihan;
        jTextField3.setText(String.valueOf(kembalian));
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (comboBarang.getSelectedIndex() > 0 && getNamaBarangFromDB()) {
            JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong","Error",
               JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
            Class.forName(driver);
                    Connection kon = DriverManager.getConnection(database,user,pass);
                    Statement stt = kon.createStatement();
//                    row = tbl_keranjang.getSelectedRow();
//                    idBarang = Integer.valueOf(tblModelKeranjang.getValueAt(row, 1).toString());
                    String SQL = "UPDATE `t_nota_item` "
                    + "SET"
                    + "`idBarang` = '"+Integer.valueOf(getIdBarangFromDB((String) comboBarang.getSelectedItem()))+"', "
                    + "`qty` = '"+ Integer.valueOf(txt_jumlah.getText())+"', "
                    + "`totalHarga` = '"+Integer.valueOf(txt_totHarga.getText())+"' "
                    + "WHERE "
                    + "`noNota` ='"+noNota+"' AND `idBarang` = '"+itemBarang+"'; ";
                    stt.executeUpdate(SQL);
                    System.out.println(itemBarang);
                    
                    String QUERY = "UPDATE `t_nota` "
                    + "SET "
                    + "`total` = '"+ Integer.valueOf(jLabel14.getText()) +"', "
                    + "`bayar` = '"+ Integer.valueOf(jTextField2.getText()) +"', "
                    + "`kembalian` = '"+Integer.valueOf(jTextField3.getText())+"' "
                    + "WHERE "
                    + "`noNota` ='"+noNota+"'; ";
                    stt.executeUpdate(QUERY);
                    
//                    dataKeranjang[0] = String.valueOf(noNota);
//                    dataKeranjang[1] = getIdBarangFromDB((String) comboBarang.getSelectedItem());
//                    dataKeranjang[2] = (String) comboBarang.getSelectedItem();
//                    dataKeranjang[3] = String.valueOf(txt_jumlah.getText());
//                    dataKeranjang[4] = String.valueOf(txt_totHarga.getText());
//                    tblModelNota.insertRow(row, dataKeranjang);
                    settablenotaload();
                    membersihkan_teks();
                    stt.close();
                    kon.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_cari_notaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cari_notaKeyReleased
        // TODO add your handling code here:
        tblModelNota.setRowCount(0);

        try{
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
//            String SQL = "SELECT * FROM t_mata_kuliah where `kd_mk` LIKE '%"+
//            txt_cari_mk.getText()+"%' OR `nama_mk` LIKE '%"+txt_cari_mk.getText()+"%'";
            String SQL = "select * from `t_nota_item` "
                    +"JOIN `t_nota` ON `t_nota_item`.`noNota` = `t_nota`.`noNota` "
                    +"JOIN `t_barang` ON `t_nota_item`.`idBarang` = `t_barang`.`id` where "
                    + "`t_nota`.`noNota` LIKE '%"+txt_cari_nota.getText()+"%'";
            ResultSet res = stt.executeQuery(SQL);
            while(res.next()){
                dataNota[0] = res.getString(2);
                dataNota[1] = res.getString(3);
                dataNota[2] = res.getString(12);
                dataNota[3] = res.getString(4);
                dataNota[4] = res.getString(5);
                dataNota[5] = res.getString(10);
                tblModelNota.addRow(dataNota);
            }
            res.close();
            stt.close();
            kon.close();
        }catch(Exception ex){
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(),"ERROR",
                JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }//GEN-LAST:event_txt_cari_notaKeyReleased

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        frm_utama frm = new frm_utama();
        frm.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    public void insertItemNota(int noNota,int idBarang,int qty, int totalHarga){
       try{
           Class.forName(driver);
                    Connection kon = DriverManager.getConnection(
                        database,
                        user,
                        pass);
                    Statement stt = kon.createStatement(); 
        String QT_nota_item = "INSERT INTO `t_nota_item`(`noNota`,`idBarang`,`qty`,`totalHarga`) "
                                    + "VALUES "
                                    + "( '"+noNota+"', "
                                    + " '" + idBarang + "', "
                                    + " '" + qty + "', "
                                    + " '" + totalHarga + "')";

        stt.executeUpdate(QT_nota_item);
       }catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                    ex.getMessage(),"ERROR",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        
    }
    
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
    private javax.swing.JComboBox<String> comboBarang;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTable tbl_barang;
    private javax.swing.JTable tbl_keranjang;
    private javax.swing.JTable tbl_pembelian;
    private javax.swing.JTextField txt_bayar;
    private javax.swing.JTextField txt_cari_nota;
    private javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_kembalian;
    private javax.swing.JTextField txt_qty;
    private javax.swing.JTextField txt_tagihan;
    private javax.swing.JTextField txt_totHarga;
    // End of variables declaration//GEN-END:variables
}
