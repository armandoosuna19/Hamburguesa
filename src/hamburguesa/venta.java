/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hamburguesa;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fimaz2014
 */
public class venta extends javax.swing.JFrame {
    Conexion conexion;
    Connection cn;
    DefaultTableModel model;
    double totalVenta;
    

    /**
     * Creates new form venta
     */
    public venta() {
        initComponents();
        initComponents();
        
        conexion = new Conexion();
        cn = conexion.conectar();
        
        model = new DefaultTableModel();                                
        model.addColumn("id");
        model.addColumn("nombre");
        model.addColumn("precio");
        model.addColumn("cantidad");
        model.addColumn("total");
        
        tablaProductos.setModel(model);
        
        colocaFolio();
        colocaFecha();
        
        colocaFolio();
        colocaFecha();
    }
     private void colocaFolio(){
    
            String sql="SELECT folio FROM ventas ORDER BY folio DESC LIMIT 0,1";
        
        try{
            
            Statement consulta =  cn.createStatement();
            ResultSet rs = consulta.executeQuery(sql);
            
            if(rs.next()){
                txtFolio.setText(""+(rs.getInt("folio")+1));
            }else{
                txtFolio.setText("100");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }       
     }
    
    private void colocaFecha(){
        
        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = new Date();
        
        txtFecha.setText(formato.format(fecha));
    }
    
     private void btnAgreagarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        
       int id_producto=0;
       int cantidad=0;
       try{
        id_producto = Integer.valueOf(txtCodigo.getText());
        cantidad = Integer.valueOf(txtCantidad.getText());
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null, "Los datos del producto no son validos");
           return;
       }
       
       if(id_producto < 1){
           JOptionPane.showMessageDialog(null, "el codigo del producto no es valido");
           return;
       }
       
       if(cantidad < 1){
           JOptionPane.showMessageDialog(null, "No es una cantidad valida");
           return;
       }
       
       String sql="SELECT * FROM productos WHERE id = ?";
                
        try{
            //Statement consulta =  cn.createStatement();
            //ResultSet rs = consulta.executeQuery(sql);
            PreparedStatement consulta = cn.prepareStatement(sql);
            consulta.setInt(1,id_producto);

            ResultSet rs = consulta.executeQuery();
            
            if(rs.next()){
                //El producto se encontro
                
                double total = 0;
                double precio = rs.getDouble("precio");
                total = precio * cantidad;
                totalVenta += total;
                
                String[] datos = new String[5];
                datos[0] = ""+id_producto;
                datos[1] = ""+rs.getString("producto");
                datos[2] = ""+rs.getString("precio");
                datos[3] = ""+cantidad;
                datos[4] = ""+total;
                
                model.addRow(datos);
                
                txtCodigo.setText("");
                txtCantidad.setText("");
                txtCodigo.requestFocus();
                
                lblTotal.setText(""+totalVenta);
                
               //JOptionPane.showMessageDialog(null, "Producto vendido");
               return; 
            }else{
               JOptionPane.showMessageDialog(null, "No existe el producto.");
               return;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }                                           

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        
        String folio = txtFolio.getText();
        String fecha = txtFecha.getText();
        
        //convertir de dd/mm/YYYY a YYYY-mm-dd
        
        String monto_total = ""+totalVenta;
        
        String sql="INSERT INTO ventas (folio,monto_total,fecha) VALUES(?,?,?)";
                
        try{
            PreparedStatement consulta = cn.prepareStatement(sql);
            consulta.setString(1,folio);
            consulta.setString(2,monto_total);
            consulta.setString(3,fecha);            
            
            consulta.executeUpdate();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
       
        sql="SELECT LAST_INSERT_ID() as id FROM ventas";
        
        String id = null;
        
        try{
            Statement consulta =  cn.createStatement();
            ResultSet rs = consulta.executeQuery(sql);            
            if(rs.next()){
                id = rs.getString("id");
            }else{
                JOptionPane.showMessageDialog(null, "Error");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
     
        for(int i = 0; i < model.getRowCount(); i++){
            sql="INSERT INTO ventas_detalle (id_ventas,id_producto,precio,cantidad,total) VALUES(?,?,?,?,?)";    
            
            String id_venta=id;
            String id_producto=model.getValueAt(i, 0).toString();
            String precio=model.getValueAt(i, 2).toString();
            String cantidad=model.getValueAt(i, 3).toString();
            String total=model.getValueAt(i, 4).toString();
            
             try{
                 
                PreparedStatement consulta = cn.prepareStatement(sql);
                consulta.setString(1,id_venta);
                consulta.setString(2,id_producto);
                consulta.setString(3,precio);            
                consulta.setString(4,cantidad);            
                consulta.setString(5,total);            

                consulta.execute();   
                
            }catch(Exception ex){                                   
                ex.printStackTrace();                
            }   
        }
        
        JOptionPane.showMessageDialog(null, "Se guardo correctamente la venta");
        
        
    } 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        txtFolio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Cantidad = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Folio");

        jLabel2.setText("Fecha");

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tablaProductos);

        jLabel3.setText("Total");

        lblTotal.setText("0.0");

        jLabel5.setText("Codigo");

        Cantidad.setText("Cantidad");

        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel3)
                                .addGap(39, 39, 39)
                                .addComponent(lblTotal))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                                    .addComponent(txtFolio))
                                .addGap(65, 65, 65)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(Cantidad))))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtFolio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Cantidad)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblTotal))
                .addContainerGap(109, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

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
            java.util.logging.Logger.getLogger(venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new venta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Cantidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtFolio;
    // End of variables declaration//GEN-END:variables

    void setMaximizable(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
