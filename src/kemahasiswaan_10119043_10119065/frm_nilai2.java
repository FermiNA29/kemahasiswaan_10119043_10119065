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
import java.util.Date;  
import java.text.DateFormat;  
import java.text.SimpleDateFormat;
/**
 *
 * @author FERMI
 */
public class frm_nilai2 extends javax.swing.JFrame {
    koneksi dbsetting;
    String driver, database,user,pass,angkatan;
    Object tabel;
    /**
     * Creates new form frm_nilai2
     */
    public frm_nilai2() {
        initComponents();
        
        dbsetting = new koneksi();
        driver = dbsetting.SettingPanel("DBDriver");
        database = dbsetting.SettingPanel("DBDatabase");
        user = dbsetting.SettingPanel("DBUsername");
        pass = dbsetting.SettingPanel("DBPassword");
        
        tblNilai.setModel(tablemodel);
        setModelComboNama();
        setModelComboMK();
        settableload();
    }
    
    private void setModelComboNama(){
       try{
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String SQL = "select nama from t_mahasiswa";
            ResultSet res = stt.executeQuery(SQL);
            comboNama.removeAllItems();
            comboNama.addItem("-- Pilih --");
            while(res.next()){
                comboNama.addItem(res.getString(1));
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
    
//    private void NimByNama() {
//        Object nama = comboNama.getSelectedItem();
//            try {
//            Class.forName(driver);
//            Connection kon = DriverManager.getConnection(database,user,pass);
//            Statement stt = kon.createStatement();
//            String SQL = "select nim `from` `t_mahasiswa` where `nama` = '"+nama+"';" ;
//            ResultSet res = stt.executeQuery(SQL);
//            txt_nim.setText(res.toString());
//            res.close();
//            stt.close();
//            kon.close();
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
//                    JOptionPane.INFORMATION_MESSAGE);
//            System.exit(0);
//        }
//    }
    
    private void setModelComboMK(){
       try{
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String SQL = "select nama_mk from t_mata_kuliah";
            ResultSet res = stt.executeQuery(SQL);
            comboKodeMK.removeAllItems();
            comboKodeMK.addItem("-- Pilih --");
            while(res.next()){
                comboKodeMK.addItem(res.getString(1));
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
            "Nama",
            "Nama MK",
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
            "Index",
            "Keterangan",
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
    
    String data[]=new String[15];
    
    private void settableload(){
        String stat = "";
        try{
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String SQL = "select * from t_nilai join t_mahasiswa on t_nilai.nim=t_mahasiswa.nim join t_mata_kuliah on t_mata_kuliah.kd_mk=t_nilai.kd_mk";
            ResultSet res = stt.executeQuery(SQL);
            while(res.next()){
                data[0] = res.getString(19);
                data[1] = res.getString(25);
                data[2] = res.getString(7);
                data[3] = res.getString(8);
                data[4] = res.getString(9);
                data[5] = res.getString(10);
                data[6] = res.getString(11);
                data[7] = res.getString(12);
                data[8] = res.getString(13);
                data[9] = res.getString(14);
                data[10] = res.getString(15);
                data[11] = res.getString(16);
                data[12] = res.getString(4);
                data[13] = res.getString(5);
                data[14] = res.getString(6);
                tablemodel.addRow(data);
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
    
    public void getAngkatanFromDb() {
        try{
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String SQL = "select angkatan from t_nilai";
            ResultSet res = stt.executeQuery(SQL);
            while(res.next()){
//                angkatan = res.getString(0);
                txt_angkatan.setText(res.getString(1));
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
    
     public void membersihkan_teks(){
        comboNama.setSelectedIndex(0);
        comboKodeMK.setSelectedIndex(0);
        txt_KodeMk.setText("");
        txt_angkatan.setText("");
        txt_kehadiran.setText("");
        txt_nim.setText("");
        txt_tugas1.setText("");
        txt_tugas2.setText("");
        txt_tugas3.setText("");
        txt_uas.setText("");
        txt_uts.setText("");
    }
    
     public void nonaktif_teks(){
//        txtNilai.setEnabled(false);
        txt_KodeMk.setEnabled(false);
        txt_angkatan.setEnabled(false);
        txt_kehadiran.setEnabled(false);
        txt_nim.setEnabled(false);
        txt_tugas1.setEnabled(false);
        txt_tugas2.setEnabled(false);
        txt_tugas3.setEnabled(false);
        txt_uas.setEnabled(false);
        txt_uts.setEnabled(false);
        comboNama.setEnabled(false);
        comboKodeMK.setEnabled(false);
     }
     
     public void aktif_teks(){
//        txtNilai.setEnabled(true);
        comboNama.setEnabled(true);
        comboKodeMK.setEnabled(true);
        txt_KodeMk.setEnabled(true);
        txt_angkatan.setEnabled(true);
        txt_kehadiran.setEnabled(true);
        txt_nim.setEnabled(true);
        txt_tugas1.setEnabled(true);
        txt_tugas2.setEnabled(true);
        txt_tugas3.setEnabled(true);
        txt_uas.setEnabled(true);
        txt_uts.setEnabled(true);
     }
    int row = 0;
     public void tampil_field(){
        row = tblNilai.getSelectedRow();
        comboNama.setSelectedItem(tablemodel.getValueAt(row, 0).toString());
        comboKodeMK.setSelectedItem(tablemodel.getValueAt(row, 1).toString());
        txt_kehadiran.setText(tablemodel.getValueAt(row, 2).toString());
        txt_tugas1.setText(tablemodel.getValueAt(row, 3).toString());
        txt_tugas2.setText(tablemodel.getValueAt(row, 4).toString());
        txt_tugas3.setText(tablemodel.getValueAt(row, 5).toString());
        txt_uts.setText(tablemodel.getValueAt(row, 6).toString());
        txt_uas.setText(tablemodel.getValueAt(row, 7).toString());
//        txt_angkatan.setText(angkatan);
        getAngkatanFromDb();
        btn_simpan.setEnabled(false);
        btn_ubah.setEnabled(true);
        btn_hapus.setEnabled(true);
        btn_batal.setEnabled(false);
        aktif_teks();
        System.out.println(angkatan);
     }
     
     public char getIndex(double nilai){
         char indeks = 0 ;
         if(nilai >= 80 && nilai <= 100){
             indeks = 'A';
         }else if(nilai >= 68 && nilai < 80){
             indeks = 'B';
         }else if(nilai >= 56 && nilai < 68){
             indeks = 'C';
         }else if(nilai >= 45 && nilai < 56){
             indeks = 'D';
         }else if(nilai >= 0 && nilai < 45){
             indeks = 'E';
         }
         
         return indeks;
     }
     
//     public String getKeterangan(double nilai, double absensi){
//        String hasil = null;
//         if((nilai >= 56 || nilai <= 100) && absensi >=11){
//                hasil = "Lulus";
//         }else if((nilai < 56  && nilai >= 0) && absensi < 11){
//                hasil = "Tidak Lulus";
//         }else{
//              JOptionPane.showMessageDialog(null, "Nilai Tidak Valid", "Pesan", JOptionPane.ERROR_MESSAGE);  
//         }
//         return hasil;
//     }
     
     public String getKeterangan(char index, double absensi){
        String hasil = null;
         if(absensi < 11 || index == 'D' || index == 'E'){
//             if (index == 'D' || index == 'E') {
                 hasil = "Tidak Lulus";
//             } else {
//                 hasil = "Tidak Lulus";
//             }
         }else if(absensi >= 11.0 || index == 'A' || index == 'B' || index == 'C'){
                hasil = "Lulus";
         }else{
              JOptionPane.showMessageDialog(null, "Nilai Tidak Valid", "Pesan", JOptionPane.ERROR_MESSAGE);  
         }
         return hasil;
     }
     
     public double hitungAbsensi(double absen) {
         return (((absen/14)*100*5)/100);
     }
     
     public double hitungNilaiTugas(double tugas1, double tugas2, double tugas3) {
         return ((tugas1+tugas2+tugas3)/3)*25/100;
     }
     
     public double hitungNilaiUTS (double nilai) {
         return (nilai*30)/100;
     }
     
     public double hitungNilaiUAS (double nilai) {
         return (nilai*40)/100;
     }
     
     private String getTahun() {  
        DateFormat dateFormat = new SimpleDateFormat("yyyy");  
        Date date = new Date();  
        return dateFormat.format(date);  
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_cari = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        comboNama = new javax.swing.JComboBox<>();
        txt_nim = new javax.swing.JTextField();
        txt_kehadiran = new javax.swing.JTextField();
        txt_tugas1 = new javax.swing.JTextField();
        txt_tugas2 = new javax.swing.JTextField();
        txt_tugas3 = new javax.swing.JTextField();
        comboKodeMK = new javax.swing.JComboBox<>();
        txt_KodeMk = new javax.swing.JTextField();
        txt_uts = new javax.swing.JTextField();
        txt_uas = new javax.swing.JTextField();
        txt_angkatan = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNilai = new javax.swing.JTable();
        btn_tambah = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FORM NILAI MAHASISWA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(215, 215, 215)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(42, 42, 42))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Pencarian Data"));

        jLabel2.setText("Masukan Data");

        txt_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cariKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Nama");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("NIM");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Kehadiran");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Tugas 1");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Tugas 2");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Tugas 3");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Nama Mata Kuliah");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Kode MK");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("UTS");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("UAS");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Angkatan");

        comboNama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Nama --" }));
        comboNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboNamaActionPerformed(evt);
            }
        });

        txt_kehadiran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kehadiranActionPerformed(evt);
            }
        });

        comboKodeMK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Mata Kuliah --" }));
        comboKodeMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboKodeMKActionPerformed(evt);
            }
        });

        tblNilai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nama", "Nama MK", "Absensi", "Tgs 1", "Tgs 2", "Tgs 3", "UTS", "UAS", "Nilai Absen", "Nilai Tugas", "Nilai UTS", "Nilai UAS", "Nilai Akhir", "Index", "Keterangan"
            }
        ));
        tblNilai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNilaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNilai);

        btn_tambah.setText("Tambah");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(txt_nim, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(44, 44, 44)
                                .addComponent(comboNama, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(31, 31, 31)
                                .addComponent(txt_tugas3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_tugas1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_kehadiran, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_tugas2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10))
                                .addGap(64, 64, 64)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_KodeMk, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txt_angkatan, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_uas, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_uts, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(216, 216, 216))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(comboKodeMK, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(167, 167, 167))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(184, 184, 184)
                                .addComponent(btn_tambah)
                                .addGap(53, 53, 53)
                                .addComponent(btn_ubah)
                                .addGap(50, 50, 50)
                                .addComponent(btn_hapus)
                                .addGap(47, 47, 47)
                                .addComponent(btn_simpan)
                                .addGap(46, 46, 46)
                                .addComponent(btn_batal)
                                .addGap(45, 45, 45)
                                .addComponent(btn_keluar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1015, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(comboKodeMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txt_KodeMk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_uts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_uas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txt_angkatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(comboNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_nim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_kehadiran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_tugas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_tugas2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_tugas3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah)
                    .addComponent(btn_ubah)
                    .addComponent(btn_hapus)
                    .addComponent(btn_simpan)
                    .addComponent(btn_batal)
                    .addComponent(btn_keluar))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_kehadiranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kehadiranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kehadiranActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        // TODO add your handling code here:
        Object nama = comboNama.getSelectedItem();
        Object namaMk = comboKodeMK.getSelectedItem();
        //
        if((comboNama.getSelectedIndex() == 0) || (comboKodeMK.getSelectedIndex() == 0) || (txt_nim.getText().isEmpty())
                || (txt_tugas1.getText().isEmpty()) || (txt_tugas2.getText().isEmpty()) || (txt_tugas3.getText().isEmpty())
                 || (txt_KodeMk.getText().isEmpty()) || (txt_kehadiran.getText().isEmpty())  || (txt_uts.getText().isEmpty())
                 || (txt_uas.getText().isEmpty()) || (txt_angkatan.getText().isEmpty())){
            JOptionPane.showMessageDialog(null, "Data tidak boleh kosong. silahkan dilengkapi");
            comboNama.requestFocus();
        }else{
            if (Double.valueOf(txt_kehadiran.getText()) >= 0 && Double.valueOf(txt_kehadiran.getText()) <=14) {
                double nilaiAbsensi = hitungAbsensi(Double.parseDouble(txt_kehadiran.getText()));
                double nilaiTugas = hitungNilaiTugas(Double.parseDouble(txt_tugas1.getText()), Double.parseDouble(txt_tugas2.getText()), Double.parseDouble(txt_tugas3.getText()));
                double nilaiUts = hitungNilaiUTS(Double.parseDouble(txt_uts.getText()));
                double nilaiUas = hitungNilaiUAS(Double.parseDouble(txt_uas.getText()));
                double nilaiAkhir = nilaiAbsensi + nilaiTugas + nilaiUts + nilaiUas;
                char index = getIndex(nilaiAkhir);
                String ket = getKeterangan(index, Double.parseDouble(txt_kehadiran.getText()));

                try{
                    Class.forName(driver);
                    Connection kon = DriverManager.getConnection(database,user,pass);
                    Statement stt = kon.createStatement();
                    String SQL = "UPDATE `t_nilai` "
                    + "SET `nilai` = '"+nilaiAkhir+"', "
                    + "`nim` = '"+txt_nim.getText()+"', "
                    + "`kd_mk` = '"+txt_KodeMk.getText()+"', "
                    + "`index` = '"+index+"', "
                    + "`ket` = '"+ket+"', "
                    + "`absensi` = '"+Double.parseDouble(txt_kehadiran.getText())+"', "
                    + "`tugas1` = '"+Double.parseDouble(txt_tugas1.getText())+"', "
                    + "`tugas2` = '"+Double.parseDouble(txt_tugas2.getText())+"', "
                    + "`tugas3` = '"+Double.parseDouble(txt_tugas3.getText())+"', "
                    + "`uts` = '"+Double.parseDouble(txt_uts.getText())+"', "
                    + "`uas` = '"+Double.parseDouble(txt_uas.getText())+"', "
                    + "`nilaiAbsen` = '"+nilaiAbsensi+"', "
                    + "`nilaiTugas` = '"+nilaiTugas+"', "
                    + "`nilaiUts` = '"+nilaiUts+"', "
                    + "`nilaiUas` = '"+nilaiUas+"',"
                    + "`angkatan` = '"+txt_angkatan.getText()+"' "
                    + "WHERE "
                    + "`nim` ='"+txt_nim.getText()+"' AND `kd_mk` = '"+txt_KodeMk.getText()+"'; ";
                    stt.executeUpdate(SQL);
                    data[0] = nama.toString();
                    data[1] = namaMk.toString();
                    data[2] = txt_kehadiran.getText();
                    data[3] = txt_tugas1.getText();
                    data[4] = txt_tugas2.getText();
                    data[5] = txt_tugas3.getText();
                    data[6] = txt_uts.getText();
                    data[7] = txt_uas.getText();
                    data[8] = String.valueOf(nilaiAbsensi);
                    data[9] = String.valueOf(nilaiTugas);
                    data[10] = String.valueOf(nilaiUts);
                    data[11] = String.valueOf(nilaiUas);
                    data[12] = String.valueOf(nilaiAkhir);
                    data[13] = String.valueOf(index);
                    data[14] = ket;
                    tablemodel.removeRow(row);
                    tablemodel.insertRow(row, data);
                    stt.close();
                    kon.close();
                    membersihkan_teks();
                    btn_simpan.setEnabled(false);
                    nonaktif_teks();
                }
                catch(Exception ex){
                    System.err.println(ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Kehadiran tidak boleh kurang dari 0 atau lebih dari 14 pertemuan!","ERROR",
                JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        membersihkan_teks();
        comboNama.requestFocus();
        btn_simpan.setEnabled(true);
        btn_ubah.setEnabled(false);
        btn_hapus.setEnabled(false);
        btn_keluar.setEnabled(false);
        txt_angkatan.setText(getTahun());
        aktif_teks();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void comboNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboNamaActionPerformed
        // TODO add your handling code here:
        Object nama = comboNama.getSelectedItem();
        
        try {
            if (comboNama.getSelectedIndex() == 0) {
                txt_nim.setText("");
            } else {
                Class.forName(driver);
                Connection kon = DriverManager.getConnection(database,user,pass);
                Statement stt = kon.createStatement();
                String SQL = "select nim from t_mahasiswa where `nama` = '"+nama+"';" ;
                ResultSet res = stt.executeQuery(SQL);
                while (res.next()) {
                    txt_nim.setText(res.getString(1));
                    
                }
//                System.out.println(res.getString(SQL));
                res.close();
                stt.close();
                kon.close();
            }
            
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }//GEN-LAST:event_comboNamaActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        Object nama = comboNama.getSelectedItem();
        Object namaMk = comboKodeMK.getSelectedItem();
        
        if((comboNama.getSelectedIndex() == 0) || (comboKodeMK.getSelectedIndex() == 0) || (txt_nim.getText().isEmpty())
                || (txt_tugas1.getText().isEmpty()) || (txt_tugas2.getText().isEmpty()) || (txt_tugas3.getText().isEmpty())
                 || (txt_KodeMk.getText().isEmpty()) || (txt_kehadiran.getText().isEmpty())  || (txt_uts.getText().isEmpty())
                 || (txt_uas.getText().isEmpty()) || (txt_angkatan.getText().isEmpty())){
            JOptionPane.showMessageDialog(null, "Data tidak boleh kosong. silahkan dilengkapi");
            comboNama.requestFocus();
        }else {
            if(Double.valueOf(txt_kehadiran.getText()) >= 0 && Double.valueOf(txt_kehadiran.getText()) <=14){
                try{
                    double nilaiAbsensi = hitungAbsensi(Double.parseDouble(txt_kehadiran.getText()));
                    double nilaiTugas = hitungNilaiTugas(Double.parseDouble(txt_tugas1.getText()), Double.parseDouble(txt_tugas2.getText()), Double.parseDouble(txt_tugas3.getText()));
                    double nilaiUts = hitungNilaiUTS(Double.parseDouble(txt_uts.getText()));
                    double nilaiUas = hitungNilaiUAS(Double.parseDouble(txt_uas.getText()));
                    double nilaiAkhir = nilaiAbsensi + nilaiTugas + nilaiUts + nilaiUas;
                    char index = getIndex(nilaiAkhir);
                    String ket = getKeterangan(index, Double.parseDouble(txt_kehadiran.getText()));
                    Class.forName(driver);
                    Connection kon = DriverManager.getConnection(
                        database,
                        user,
                        pass);
                    Statement stt = kon.createStatement();
                    String SQL = "INSERT INTO `t_nilai`(`nim`,`kd_mk`,`nilai`,`index`,`ket`,`absensi`,`tugas1`,`tugas2`,`tugas3`,`uts`,`uas`,`nilaiAbsen`,`nilaiTugas`,`nilaiUts`,`nilaiUas`,`-angkatan`) "
                    + "VALUES "
                    + "( '"+txt_nim.getText()+"', "
                    + " '"+txt_KodeMk.getText()+"', "
                    + " '"+nilaiAkhir+"', "
                    + " '"+ index + "', "
                    + " '"+ ket + "', "
                    + " '"+ Double.parseDouble(txt_kehadiran.getText()) + "', "
                    + " '"+ Double.parseDouble(txt_tugas1.getText()) + "', "
                    + " '"+ Double.parseDouble(txt_tugas2.getText()) + "', "
                    + " '"+ Double.parseDouble(txt_tugas3.getText()) + "', "
                    + " '"+ Double.parseDouble(txt_uts.getText()) + "', "
                    + " '"+ Double.parseDouble(txt_uas.getText()) + "', "
                    + " '"+ nilaiAbsensi + "', "
                    + " '"+ nilaiTugas + "', "
                    + " '"+ nilaiUts + "', "
                    + " '"+ nilaiUas +"',"
                    + " '"+ txt_angkatan.getText() +"')";

                    stt.executeUpdate(SQL);
                    data[0] = nama.toString();
                    data[1] = namaMk.toString();
                    data[2] = txt_KodeMk.getText();
                    data[3] = txt_tugas1.getText();
                    data[4] = txt_tugas2.getText();
                    data[5] = txt_tugas3.getText();
                    data[6] = txt_uts.getText();
                    data[7] = txt_uas.getText();
                    data[8] = String.valueOf(nilaiAbsensi);
                    data[9] = String.valueOf(nilaiTugas);
                    data[10] = String.valueOf(nilaiUts);
                    data[11] = String.valueOf(nilaiUas);
                    data[12] = String.valueOf(nilaiAkhir);
                    data[13] = String.valueOf(index);
                    data[14] = ket;

                    tablemodel.insertRow(0, data);
                    stt.close();
                    kon.close();
                    membersihkan_teks();
                    btn_simpan.setEnabled(false);
                    nonaktif_teks();
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                    ex.getMessage(),"ERROR",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Kehadiran tidak boleh kurang dari 0 atau lebih dari 14 pertemuan!","ERROR",
            JOptionPane.ERROR_MESSAGE);
        }
            
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void comboKodeMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboKodeMKActionPerformed
        // TODO add your handling code here:
        Object namaMk = comboKodeMK.getSelectedItem();
        
        try {
            if (comboKodeMK.getSelectedIndex() == 0) {
                txt_KodeMk.setText("");
            } else {
                Class.forName(driver);
                Connection kon = DriverManager.getConnection(database,user,pass);
                Statement stt = kon.createStatement();
                String SQL = "select kd_mk from t_mata_kuliah where `nama_mk` = '"+namaMk+"';" ;
                ResultSet res = stt.executeQuery(SQL);
                while (res.next()) {
                    txt_KodeMk.setText(res.getString(1));
                    
                }
//                System.out.println(res.getString(SQL));
                res.close();
                stt.close();
                kon.close();
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }//GEN-LAST:event_comboKodeMKActionPerformed

    private void tblNilaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNilaiMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            tampil_field();
        }
    }//GEN-LAST:event_tblNilaiMouseClicked

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        try{
//            System.out.println(txt_nim.getText());
//            System.out.println(txt_KodeMk.getText());
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String  SQL = "DELETE FROM t_nilai "
            + " where "
            + " nim = '"+txt_nim.getText()+"' AND "
            + " kd_mk = '"+txt_KodeMk.getText()+"'";
            stt.executeUpdate(SQL);
            tablemodel.removeRow(row);
            stt.close();
            kon.close();
            membersihkan_teks();
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void txt_cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariKeyReleased
        // TODO add your handling code here:
        tablemodel.setRowCount(0);

        try{
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database,user,pass);
            Statement stt = kon.createStatement();
            String SQL = "select * from t_nilai join t_mahasiswa on t_nilai.`nim`=t_mahasiswa.`nim` join t_mata_kuliah on t_mata_kuliah.`kd_mk`=t_nilai.`kd_mk` where t_mahasiswa.`nama` LIKE '%"+
                           txt_cari.getText()+"%' OR t_mata_kuliah.`nama_mk` LIKE '%"+txt_cari.getText()+"%'";
            ResultSet res = stt.executeQuery(SQL);
            while(res.next()){
                data[0] = res.getString(18);
                data[1] = res.getString(24);
                data[2] = res.getString(7);
                data[3] = res.getString(8);
                data[4] = res.getString(9);
                data[5] = res.getString(10);
                data[6] = res.getString(11);
                data[7] = res.getString(12);
                data[8] = res.getString(13);
                data[9] = res.getString(14);
                data[10] = res.getString(15);
                data[11] = res.getString(16);
                data[12] = res.getString(4);
                data[13] = res.getString(5);
                data[14] = res.getString(6);
                tablemodel.addRow(data);
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
    }//GEN-LAST:event_txt_cariKeyReleased

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        frm_utama frm = new frm_utama();
        frm.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        membersihkan_teks();
        aktif_teks();
        btn_simpan.setEnabled(true);
        btn_hapus.setEnabled(true);
        btn_keluar.setEnabled(true);
        btn_ubah.setEnabled(true);
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
        // TODO add your handling code here:
        frm_utama futama = new frm_utama();
        futama.setVisible(true);
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
            java.util.logging.Logger.getLogger(frm_nilai2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_nilai2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_nilai2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_nilai2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_nilai2().setVisible(true);
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
    private javax.swing.JComboBox<String> comboKodeMK;
    private javax.swing.JComboBox<String> comboNama;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JTable tblNilai;
    private javax.swing.JTextField txt_KodeMk;
    private javax.swing.JTextField txt_angkatan;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_kehadiran;
    private javax.swing.JTextField txt_nim;
    private javax.swing.JTextField txt_tugas1;
    private javax.swing.JTextField txt_tugas2;
    private javax.swing.JTextField txt_tugas3;
    private javax.swing.JTextField txt_uas;
    private javax.swing.JTextField txt_uts;
    // End of variables declaration//GEN-END:variables
}
