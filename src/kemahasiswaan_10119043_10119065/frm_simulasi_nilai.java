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
import javax.swing.JOptionPane;

/**
 *
 * @author Fachriansyah PC
 */
public class frm_simulasi_nilai extends javax.swing.JFrame {
    koneksi dbsetting;
    String driver, database,user,pass;
    Object tabel;
    
    int totKehadiran,maksKehadiran = 14;
    double prest_absen,prest_tgs,prest_uts,prest_uas,
            nilai_tgs1,nilai_tgs2,nilai_tgs3,nilai_uts,nilai_uas,
            nilai_absen,nilai_tgs,n_uts,n_uas,na;
    char indek;
    String keterangan;
    Object nama_mk;
    /**
     * Creates new form frm_simulasi_nilai
     */
    public frm_simulasi_nilai() {
        initComponents();
        
        dbsetting = new koneksi();
        driver = dbsetting.SettingPanel("DBDriver");
        database = dbsetting.SettingPanel("DBDatabase");
        user = dbsetting.SettingPanel("DBUsername");
        pass = dbsetting.SettingPanel("DBPassword");
        tbl_simulasinilai.setModel(tablemodel);
        settableload();
        setModelComboMK();
    }
    
    private void setModelComboMK(){
       try{
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String SQL = "select * from t_mata_kuliah";
            ResultSet res = stt.executeQuery(SQL);
            comboMK.removeAllItems();
            comboMK.addItem("-- Pilih --");
            while(res.next()){
                comboMK.addItem(res.getString(2));
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
    
    private javax.swing.table.DefaultTableModel tablemodel = getDefaultTableModel();
    private javax.swing.table.DefaultTableModel getDefaultTableModel(){
        return new javax.swing.table.DefaultTableModel(
        new Object[][]{},
        new String [] {
            "Nama MK",
            "Presentase Absen",
            "Presentase Tugas",
            "Presentase UTS",
            "Presentase UAS",
            "Absensi",
            "Tgs1",
            "Tgs2",
            "Tgs3",
            "UTS",
            "UAS",
            "Nilai Absen",
            "Nilai Tugas",
            "Nilai UTS",
            "Nilai UAS",
            "Nilai Akhir",
            "Indek",
            "Keterangan"
        }
        )
    
        {
            boolean[] canEdit = new boolean[]
            {
                false,false,false,false,false,false,false,false,false,false,
                false,false,false,false,false,false,false,false
            };
            
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit[columnIndex];
            }
        };
    }
    
    String data[]=new String[18];
    
    private void settableload(){
        String stat = "";
//        try{
//            Class.forName(driver);
//            Connection kon = DriverManager.getConnection(database,user,pass);
//            Statement stt = kon.createStatement();
//            String SQL = "select * from t_mata_kuliah";
//            ResultSet res = stt.executeQuery(SQL);
//            while(res.next()){
//                data[0] = res.getString(1);
//                data[1] = res.getString(2);
//                tablemodel.addRow(data);
//            }
//            res.close();
//            stt.close();
//            kon.close();
//        }catch(Exception ex){
//            System.err.println(ex.getMessage());
//            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
//                    JOptionPane.INFORMATION_MESSAGE);
//            System.exit(0);
//        }
    }
    
    public String getKodeMK(String namaMK){
        String kode = "";
        try{
           Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String SQL = "select * from t_mata_kuliah where `nama_mk` = '"+namaMK+"'";
            ResultSet res = stt.executeQuery(SQL);
            res.next();
            kode = res.getString(1);
            res.close();
            stt.close();
            kon.close();
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
        return kode;
    }
    
    public void membersihkan_teks(){
        comboMK.setSelectedIndex(0);
        txt_kodemk.setText("");
        txt_kehadiran.setText("");
        txt_perst_tugas.setText("");
        txt_perst_absen.setText("");
        txt_perst_uts.setText("");
        txt_perst_uas.setText("");
        txt_tugas1.setText("");
        txt_tugas2.setText("");
        txt_tugas3.setText("");
        txt_uts.setText("");
        txt_uas.setText("");
    }
    
     public void nonaktif_teks(){
        txt_kodemk.setEnabled(false);
        txt_kehadiran.setEnabled(false);
        txt_perst_tugas.setEnabled(false);
        txt_perst_absen.setEnabled(false);
        txt_perst_uts.setEnabled(false);
        txt_perst_uas.setEnabled(false);
        txt_tugas1.setEnabled(false);
        txt_tugas2.setEnabled(false);
        txt_tugas3.setEnabled(false);
        txt_uts.setEnabled(false);
        txt_uas.setEnabled(false);
     }
     
     public void aktif_teks(){
        txt_kodemk.setEnabled(true);
        txt_kehadiran.setEnabled(true);
        txt_perst_tugas.setEnabled(true);
        txt_perst_absen.setEnabled(true);
        txt_perst_uts.setEnabled(true);
        txt_perst_uas.setEnabled(true);
        txt_tugas1.setEnabled(true);
        txt_tugas2.setEnabled(true);
        txt_tugas3.setEnabled(true);
        txt_uts.setEnabled(true);
        txt_uas.setEnabled(true);
     }
     
     int row = 0;
     public void tampil_field(){
         row = tbl_simulasinilai.getSelectedRow();
         comboMK.setSelectedItem(tablemodel.getValueAt(row, 0).toString());
         txt_perst_absen.setText(tablemodel.getValueAt(row, 1).toString().replace("%", ""));
         txt_perst_tugas.setText(tablemodel.getValueAt(row, 2).toString().replace("%", ""));
         txt_perst_uts.setText(tablemodel.getValueAt(row, 3).toString().replace("%", ""));
         txt_perst_uas.setText(tablemodel.getValueAt(row, 4).toString().replace("%", ""));
         txt_kehadiran.setText(tablemodel.getValueAt(row, 5).toString());
         txt_tugas1.setText(tablemodel.getValueAt(row, 6).toString());
         txt_tugas2.setText(tablemodel.getValueAt(row, 7).toString());
         txt_tugas3.setText(tablemodel.getValueAt(row, 8).toString());
         txt_uts.setText(tablemodel.getValueAt(row, 9).toString());
         txt_uas.setText(tablemodel.getValueAt(row, 10).toString());
         
         btn_simpan.setEnabled(false);
         btn_ubah.setEnabled(true);
         btn_hapus.setEnabled(true);
         btn_batal.setEnabled(false);
        aktif_teks();
     }
     
     public char getIndex(double nilai){
         char indeks;
         if(nilai >= 80 && nilai <= 100){
             indeks = 'A';
         }else if(nilai >= 68 && nilai < 80){
             indeks = 'B';
         }else if(nilai >= 56 && nilai < 68){
             indeks = 'C';
         }else if(nilai >= 45 && nilai < 56){
             indeks = 'D';
         }else{
             indeks = 'E';
         }
         
         return indeks;
     }
     
      public String getKeterangan(char indek){
         String keterangan;
         if(indek == 'A'||indek == 'B'||indek == 'C'){
             keterangan = "LULUS";
         }else{
             keterangan = "Tidak Lulus";
         }
         
         return keterangan;
     }
     
     public Double hitungNilaiAbsen(int kehadiran,int maksKehadiran,double prest_absen){
         //bug
       Double result = Double.valueOf((((kehadiran/maksKehadiran)*100*prest_absen)/100));
       return result;
     }
     
     public Double hitungNilaiTugas(double nilai_tgs1,double nilai_tgs2,double nilai_tgs3,double prest_tgs){
       Double result = ((nilai_tgs1+nilai_tgs2+nilai_tgs3)/3) * (prest_tgs/100);
       return result;
     }
     
     public void validasiInput(){
         if(txt_kehadiran.getText().isEmpty()){
             JOptionPane.showMessageDialog(null, "Tidak boleh ada data yang kosong","ERROR",
                JOptionPane.INFORMATION_MESSAGE);
         }
     }
     
     public void inisialisasiVariabel(){
         nama_mk = comboMK.getSelectedItem();
        
            prest_absen = Double.valueOf(txt_perst_absen.getText());
            prest_tgs = Double.valueOf(txt_perst_tugas.getText());
            prest_uts = Double.valueOf(txt_perst_uts.getText());
            prest_uas = Double.valueOf(txt_perst_uas.getText());
            totKehadiran = Integer.valueOf(txt_kehadiran.getText());
            nilai_tgs1 = Double.valueOf(txt_tugas1.getText());
            nilai_tgs2 = Double.valueOf(txt_tugas2.getText());
            nilai_tgs3 = Double.valueOf(txt_tugas3.getText());
            nilai_uts = Double.valueOf(txt_uts.getText());
            nilai_uas = Double.valueOf(txt_uas.getText());
     }
     
     public void addToTableAction(){
            inisialisasiVariabel();
                
            nilai_absen = hitungNilaiAbsen(totKehadiran,maksKehadiran,prest_absen);
            nilai_tgs = hitungNilaiTugas(nilai_tgs1,nilai_tgs2,nilai_tgs3,prest_tgs);
            n_uts = nilai_uts * prest_uts/100;
            n_uas = nilai_uas * prest_uas/100;
            na = nilai_absen + nilai_tgs + n_uts + n_uas;

            indek = getIndex(na);
            keterangan = getKeterangan(indek);

            data[0] = nama_mk.toString();
            data[1] = String.valueOf(prest_absen) + "%";
            data[2] = String.valueOf(prest_tgs) + "%";
            data[3] = String.valueOf(prest_uts) + "%";
            data[4] = String.valueOf(prest_uas) + "%";
            data[5] = String.valueOf(totKehadiran);
            data[6] = String.valueOf(nilai_tgs1);
            data[7] = String.valueOf(nilai_tgs2);
            data[8] = String.valueOf(nilai_tgs3);
            data[9] = String.valueOf(nilai_uts);
            data[10] = String.valueOf(nilai_uas);
            data[11] = String.valueOf(nilai_absen);
            data[12] = String.valueOf(nilai_tgs);
            data[13] = String.valueOf(n_uts);
            data[14] = String.valueOf(n_uas);
            data[15] = String.valueOf(na);
            data[16] = String.valueOf(indek);
            data[17] = String.valueOf(keterangan);
           
     }
     
     public boolean validation(){
         inisialisasiVariabel();
         if(totKehadiran >= 0 && totKehadiran <= maksKehadiran){
             return true;
         }else{
              JOptionPane.showMessageDialog(null, "Kehadiran tidak boleh kurang dari 0 atau lebih dari "+maksKehadiran+" pertemuan!","ERROR",
                JOptionPane.ERROR_MESSAGE);
             return false;
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboMK = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txt_kodemk = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_perst_absen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_perst_tugas = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_perst_uts = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_perst_uas = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_kehadiran = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_tugas1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_tugas2 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txt_tugas3 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txt_uts = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_uas = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_simulasinilai = new javax.swing.JTable();
        btn_tambah = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("FORM SIMULASI NILAI AKHIR");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(216, 216, 216)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Nama Mata Kuliah");

        comboMK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMKActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Kode MK");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Presentase Absen");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("%");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Presentase Tugas");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("%");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Presentase UTS");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("%");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Presentase UAS");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("%");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Kehadiran");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Pertemuan");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Tugas 1");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Tugas 2");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Tugas 3");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("UTS");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("UAS");

        tbl_simulasinilai.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_simulasinilai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_simulasinilaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_simulasinilai);

        btn_tambah.setText("Tambah");
        btn_tambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_tambahMouseClicked(evt);
            }
        });
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
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

        btn_simpan.setText("Simpan");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_batal.setText("Batal");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        btn_keluar.setText("Keluar");
        btn_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboMK, 0, 173, Short.MAX_VALUE)
                            .addComponent(txt_kodemk)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(80, 80, 80)
                                .addComponent(txt_perst_tugas, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(80, 80, 80)
                                .addComponent(txt_perst_absen, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10))
                                .addGap(92, 92, 92)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txt_perst_uts, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txt_perst_uas, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addGap(80, 80, 80)
                                    .addComponent(txt_kehadiran, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_tugas1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_tugas2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel17)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_uts, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(117, 117, 117)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_tugas3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_uas, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btn_ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_kodemk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_perst_absen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_perst_tugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txt_perst_uts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txt_perst_uas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txt_kehadiran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txt_tugas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txt_tugas2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txt_tugas3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txt_uts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txt_uas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_batal)
                        .addComponent(btn_keluar))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_ubah)
                        .addComponent(btn_hapus)
                        .addComponent(btn_simpan))
                    .addComponent(btn_tambah))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_tambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_tambahMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_tambahMouseClicked

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        membersihkan_teks();
        comboMK.requestFocus();
        btn_simpan.setEnabled(true);
        btn_ubah.setEnabled(false);
        btn_hapus.setEnabled(false);
        btn_keluar.setEnabled(false);
        aktif_teks();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        // TODO add your handling code here:
       try{
            addToTableAction();
            if(validation()){
                tablemodel.removeRow(row);
                tablemodel.insertRow(row, data);

                membersihkan_teks();
                btn_simpan.setEnabled(false);
                nonaktif_teks();
            }
         }
         catch(Exception ex){
              JOptionPane.showMessageDialog(null, ex.getMessage(),"ERROR",
                JOptionPane.ERROR_MESSAGE);
         }
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        try{
            tablemodel.removeRow(row);
            membersihkan_teks();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(),"ERROR",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        
        try {
            if(validation()){
                addToTableAction();
                tablemodel.insertRow(0, data);

                membersihkan_teks();
                btn_simpan.setEnabled(false);
                btn_ubah.setEnabled(true);
                btn_hapus.setEnabled(true);
                btn_keluar.setEnabled(true);
                nonaktif_teks();
            }
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, ex.getMessage(),"ERROR",
                JOptionPane.ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void comboMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMKActionPerformed
        // TODO add your handling code here:
        String mk;
        if(comboMK.getSelectedIndex() == 0){
            mk = "";
        }else{
            mk = getKodeMK(String.valueOf(comboMK.getSelectedItem()));
        }
        txt_kodemk.setText(mk);
    }//GEN-LAST:event_comboMKActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        membersihkan_teks();
        btn_simpan.setEnabled(true);
        btn_ubah.setEnabled(true);
        btn_hapus.setEnabled(true);
        btn_keluar.setEnabled(true);
        aktif_teks();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void tbl_simulasinilaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_simulasinilaiMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            tampil_field();
        }
    }//GEN-LAST:event_tbl_simulasinilaiMouseClicked

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
        // TODO add your handling code here:
        frm_utama frm = new frm_utama();
        frm.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_keluarActionPerformed

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
            java.util.logging.Logger.getLogger(frm_simulasi_nilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_simulasi_nilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_simulasi_nilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_simulasi_nilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_simulasi_nilai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JComboBox<String> comboMK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_simulasinilai;
    private javax.swing.JTextField txt_kehadiran;
    private javax.swing.JTextField txt_kodemk;
    private javax.swing.JTextField txt_perst_absen;
    private javax.swing.JTextField txt_perst_tugas;
    private javax.swing.JTextField txt_perst_uas;
    private javax.swing.JTextField txt_perst_uts;
    private javax.swing.JTextField txt_tugas1;
    private javax.swing.JTextField txt_tugas2;
    private javax.swing.JTextField txt_tugas3;
    private javax.swing.JTextField txt_uas;
    private javax.swing.JTextField txt_uts;
    // End of variables declaration//GEN-END:variables
}
