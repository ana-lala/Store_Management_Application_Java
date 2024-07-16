/**
 * VentanaCompra - Esta clase representa la ventana de compra de productos.
 * Contiene métodos para la gestión de la compra, como la actualización de la tabla,
 * el cálculo del subtotal, y la interacción con la base de datos a través de la clase Conexion.
 * @author Ana Laura Chenoweth Galaz 
 */

package guis.ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.sql.*;
import utilities.Conexion;

/**
 * VentanaCompra - Clase que define la interfaz de usuario para la compra de productos.
 */

public class VentanaCompra extends javax.swing.JFrame {

    // Atributos
    private String nombre;
    private String productoVenta;
    private float subTotal = 0;
    private float Total = 0;
    private float IVA = 0;
    int cantidad;

    // Componentes del JFrame

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBoxProducto;
    private javax.swing.JComboBox<String> jComboBoxProveedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextFieldIVA;
    private javax.swing.JTextField jTextFieldSubtotal;
    private javax.swing.JTextField jTextFieldTotal;
    private NonEditableTableModel model = new NonEditableTableModel();
    private CustomTableModel model2 = new CustomTableModel(3);
    private Conexion Conexion;

    /**
     * Constructor de la clase VentanaCompra.
     * @param Conexion Objeto de la clase Conexion para la interacción con la base de datos.
     */
    public VentanaCompra(Conexion Conexion) {
        this.Conexion = Conexion;
        setUndecorated(true); // Esto quita la barra de título y botones
        setSize(1280, 720);
        setLocationRelativeTo(null);
        initComponents();
        new java.awt.Color(154, 143, 141);

        SetImageButton(jButton1, "src/utilities/imagenes/VentanaComprador/nuevo.png");
        SetImageButton(jButton2, "src/utilities/imagenes/VentanaComprador/cruz.png");
        SetImageButton(jButton3, "src/utilities/imagenes/VentanaComprador/flecha.png");

        llenarComboBoxProveedores();
        llenarComboBoxProducto();
        llenarTabla();

        model2.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                calcularSubtotal();
            }
        });

        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel(""));
        panel.add(closeButton);
        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void SetImageButton(JButton labelName, String root) {
        ImageIcon image = new ImageIcon(root);
        Icon icon = new ImageIcon(
                image.getImage().getScaledInstance(labelName.getWidth(), labelName.getHeight(), Image.SCALE_DEFAULT));
        labelName.setIcon(icon);
        this.repaint();
    }

    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jComboBoxProveedor = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxProducto = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldSubtotal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldIVA = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldTotal = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setFont(new java.awt.Font("Serif", 0, 10)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null },
                        { null, null, null },
                        { null, null, null },
                        { null, null, null }
                },
                new String[] {
                        "Proveedores", "Producto", "Precio"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel1.setText("AGREGAR UN PROVEEDOR:");

        jLabel2.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel2.setText("NOMBRE:");

        jLabel4.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel4.setText("PRODUCTO:");

        jLabel5.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel5.setText("PRECIO:");

        jButton3.setText("jButton3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 219,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGroup(jPanel1Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                                        false)
                                                                        .addComponent(jLabel5,
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(jLabel4,
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(jLabel2,
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                79,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(jButton1))
                                                        .addGroup(jPanel1Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                                        false)
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addGap(29, 29, 29)
                                                                        .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(jTextField2,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        127,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jTextField4,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        127,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jTextField5,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        127,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addGap(16, 16, 16)
                                                                        .addComponent(jButton2)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(jButton4))))
                                                .addComponent(jButton3))
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 286,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jButton4,
                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 55,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                jPanel1Layout.createSequentialGroup()
                                                                        .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel2)
                                                                                .addComponent(jTextField2,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGap(18, 18, 18)
                                                                        .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jTextField4,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel4))
                                                                        .addGap(18, 18, 18)
                                                                        .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel5)
                                                                                .addComponent(jTextField5,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGap(34, 34, 34)
                                                                        .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(jButton1,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        55,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jButton2,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        55,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31,
                                                        Short.MAX_VALUE)
                                                .addComponent(jButton3)
                                                .addGap(25, 25, 25))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
                                                        Short.MAX_VALUE)
                                                .addContainerGap()))));

        jTabbedPane1.addTab("Proveedores", jPanel1);

        jComboBoxProveedor.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jComboBoxProveedor.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProveedorActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel3.setText("Proveedor:");

        jLabel6.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel6.setText("Producto:");

        jComboBoxProducto.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jComboBoxProducto.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel7.setText("BUSCAR POR:");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Proveedor", "Producto", "Precio", "Cantidad"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jComboBoxProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productoSeleccionado = (String) jComboBoxProducto.getSelectedItem();
                String sql = "select proveedor, nombre, precio from productos WHERE nombre='" + productoSeleccionado
                        + "';";
                llenarTabla2(sql);
            }
        });

        jComboBoxProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String proveedorSeleccionado = (String) jComboBoxProveedor.getSelectedItem();
                String sql = "select proveedor, nombre, precio from productos WHERE proveedor='" + proveedorSeleccionado
                        + "';";
                llenarTabla2(sql);
            }
        });

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
                String nombre = jTextField2.getText();
                String producto = jTextField4.getText();
                String precioCad = jTextField5.getText();

                if (nombre.length() > 100 || producto.length() > 100 || producto.length() == 0 || nombre.length() == 0
                        || precioCad.length() == 0) {
                    JOptionPane.showMessageDialog(VentanaCompra.this, "Los campos estan mal ingresados", "Usuario:",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        int precio = Integer.parseInt(precioCad);
                        agregarProveedor(nombre, producto, precio);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(VentanaCompra.this, "El precio no es un número válido",
                                "Usuario:", JOptionPane.ERROR_MESSAGE);
                    }

                }

                jTextField2.setText("");
                jTextField4.setText("");
                jTextField5.setText("");
            }

        });

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2.setText("");
                jTextField4.setText("");
                jTextField5.setText("");
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
                VentanaCompra.this.dispose();
            }
        });

        jButton6.setText("COMPRAR");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                // Verificar cada fila en la columna de cantidad
                for (int i = 0; i < model.getRowCount(); i++) {
                    Object cantidadObj = model.getValueAt(i, 3); // 3 es el índice de la columna de cantidad
                    // Verificar si la celda está vacía o no es un número
                    if (cantidadObj != null) {
                        String cantidadStr = cantidadObj.toString().trim();
                        float precioStr = Float.parseFloat(jTextFieldTotal.getText());
                        if (!cantidadStr.isEmpty() && precioStr > 0) {
                            try {
                                int cantidad = Integer.parseInt(cantidadStr);
                                String proveedor = model.getValueAt(i, 0).toString();
                                String producto = model.getValueAt(i, 1).toString();

                                sumarCantidadesEnBaseDeDatos(proveedor, producto, cantidad);
                                restarDineroEnBaseDeDatos(proveedor, producto, precioStr);
                                actualizarTablaSQL();
                                jTextFieldIVA.setText("0");
                                jTextFieldSubtotal.setText("0");
                                jTextFieldTotal.setText("0");
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(VentanaCompra.this,
                                        "La fila " + (i + 1) + " no es un número válido", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                return; // Detener el proceso si se encuentra un error
                            }
                        }
                    }
                }
            }
        });
        jLabel8.setText("Subtotal:");
        jTextFieldSubtotal.setEditable(false);

        jLabel9.setText("IVA:");
        jTextFieldIVA.setEditable(false);

        jLabel10.setText("TOTAL:");
        jTextFieldTotal.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 179,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(27, 27, 27)
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jComboBoxProveedor,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 137,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jComboBoxProducto,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 137,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(27, 27, 27)
                                                .addComponent(jLabel7)))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 363,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldSubtotal)
                                        .addComponent(jTextFieldIVA)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jButton6)
                                                        .addComponent(jLabel8)
                                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 47, Short.MAX_VALUE))
                                        .addComponent(jTextFieldTotal))
                                .addContainerGap()));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jLabel7)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jSeparator1,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 10,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jLabel3)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jComboBoxProveedor,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(41, 41, 41)
                                                                .addComponent(jLabel6)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jComboBoxProducto,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(64, 64, 64))
                                                        .addComponent(jScrollPane2,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 258,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(93, 93, 93)
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextFieldSubtotal,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextFieldIVA, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(28, 28, 28)
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextFieldTotal, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton6)))
                                .addContainerGap(43, Short.MAX_VALUE)));

        jTabbedPane1.addTab("Comprar", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1));

        pack();
    }

    private void calcularSubtotal() {
        subTotal = 0;
        Total = 0;
        IVA = 0;
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            Object cantidadObj = model.getValueAt(i, 3); // 3 es el índice de la columna de cantidad
            Object precioObj = model.getValueAt(i, 2);

            if (cantidadObj != null) {
                String cantidadStr = cantidadObj.toString().trim();
                String precioStr = precioObj.toString().trim();

                if (!cantidadStr.isEmpty()) {
                    try {
                        int cantidad = Integer.parseInt(cantidadStr);
                        int precio = Integer.parseInt(precioStr);

                        subTotal += precio * cantidad;
                        IVA = (float) (subTotal * 0.16);
                        Total = subTotal + IVA;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(VentanaCompra.this,
                                "La fila " + (i + 1) + " no es un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
        }

        // Actualizar el campo jTextFieldSubtotal
        jTextFieldSubtotal.setText(String.valueOf(subTotal));
        jTextFieldIVA.setText(String.valueOf(IVA));
        jTextFieldTotal.setText(String.valueOf(Total));
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void jComboBoxProveedorActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void model2ActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void llenarComboBoxProveedores() {
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("SELECT DISTINCT proveedor FROM productos");
            ResultSet rs = pst.executeQuery();

            // Limpiar y llenar el modelo de la JComboBox de proveedores
            DefaultComboBoxModel<String> proveedorModel = new DefaultComboBoxModel<>();
            while (rs.next()) {
                proveedorModel.addElement(rs.getString("proveedor"));
            }
            jComboBoxProveedor.setModel(proveedorModel);

            cn.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, "Error",
                            JOptionPane.ERROR_MESSAGE);
        }
    }

    private void llenarComboBoxProducto() {
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("SELECT DISTINCT nombre FROM productos");
            ResultSet rs = pst.executeQuery();

            // Limpiar y llenar el modelo de la JComboBox de productos
            DefaultComboBoxModel<String> productoModel = new DefaultComboBoxModel<>();
            while (rs.next()) {
                productoModel.addElement(rs.getString("nombre"));
            }
            jComboBoxProducto.setModel(productoModel);

            cn.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, "Error",
                            JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarProveedor(String proveedor, String nombre, int precio) {
        try {
            Connection cn = Conexion.conectar();

            // Verificar si el producto ya existe en la base de datos
            PreparedStatement pstExistencia = cn.prepareStatement("SELECT COUNT(*) FROM productos WHERE nombre = ?");
            pstExistencia.setString(1, nombre);
            ResultSet rsExistencia = pstExistencia.executeQuery();
            rsExistencia.next();
            int existencia = rsExistencia.getInt(1);
            pstExistencia.close();

            if (existencia > 0) {
                JOptionPane.showMessageDialog(this, "El producto ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insertar el nuevo proveedor en la base de datos
            PreparedStatement pstInsertar = cn
                    .prepareStatement("INSERT INTO productos (proveedor, nombre, precio) VALUES (?, ?, ?)");
            pstInsertar.setString(1, proveedor);
            pstInsertar.setString(2, nombre);
            pstInsertar.setInt(3, precio);
            pstInsertar.executeUpdate();
            pstInsertar.close();

            // Actualizar el modelo de la JComboBox de productos
            llenarComboBoxProducto();
            llenarComboBoxProveedores();
            llenarTabla();

            // Cerrar la conexión
            cn.close();

            JOptionPane.showMessageDialog(this, "Proveedor agregado con éxito", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar proveedor", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void llenarTabla() {
        model.setRowCount(0);
        model.setColumnCount(0);
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("select proveedor, nombre, precio from productos");
            ResultSet rs = pst.executeQuery();
            jTable1 = new JTable(model);
            jScrollPane1.setViewportView(jTable1);

            model.addColumn("Proveedor");
            model.addColumn("Producto");
            model.addColumn("Precio");

            while (rs.next()) {
                Object[] fila = new Object[4];

                for (int i = 0; i < 3; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                model.addRow(fila);
            }
            cn.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, "Error",
                            JOptionPane.ERROR_MESSAGE);
        }

        jScrollPane1.setViewportView(jTable1);
    }

    private void llenarTabla2(String sql) {
        // Limpiar todas las filas y columnas existentes
        model2.setRowCount(0);
        model2.setColumnCount(0);

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            jTable2 = new JTable(model2);
            jScrollPane2.setViewportView(jTable2);

            model2.addColumn("Proveedor");
            model2.addColumn("Producto");
            model2.addColumn("Precio");
            model2.addColumn("Cantidad");

            while (rs.next()) {
                Object[] fila = new Object[4];

                for (int i = 0; i < 3; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                model2.addRow(fila);
            }
            cn.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, "Error",
                            JOptionPane.ERROR_MESSAGE);
        }

    }

    private void sumarCantidadesEnBaseDeDatos(String proveedor, String producto, int cantidadComprada) {
        try {
            Connection cn = Conexion.conectar();

            // Obtener la cantidad actual en la base de datos
            PreparedStatement pstConsulta = cn
                    .prepareStatement("SELECT id, cantidad FROM productos WHERE proveedor = ? AND nombre = ?");
            pstConsulta.setString(1, proveedor);
            pstConsulta.setString(2, producto);

            ResultSet rsConsulta = pstConsulta.executeQuery();

            if (rsConsulta.next()) {
                int id = rsConsulta.getInt("id");

                int cantidadActual = rsConsulta.getInt("cantidad");

                // Sumar la cantidad comprada a la cantidad existente
                int nuevaCantidad = cantidadActual + cantidadComprada;

                // Actualizar la cantidad en la base de datos
                PreparedStatement pstActualizar = cn.prepareStatement("UPDATE productos SET cantidad = ? WHERE id = ?");
                pstActualizar.setInt(1, nuevaCantidad); // Primer parámetro
                pstActualizar.setInt(2, id); // Segundo parámetro

                pstActualizar.executeUpdate();
                pstActualizar.close();

            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado en la base de datos", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            cn.setAutoCommit(false);
            cn.commit();

            rsConsulta.close();
            pstConsulta.close();
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al sumar cantidades en la base de datos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTablaSQL() {
        model.setRowCount(0);
        model.setColumnCount(0);

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("SELECT proveedor, nombre, precio FROM productos");
            ResultSet rs = pst.executeQuery();
            jTable1 = new JTable(model);
            jScrollPane1.setViewportView(jTable1);

            model.addColumn("Proveedor");
            model.addColumn("Producto");
            model.addColumn("Precio");

            while (rs.next()) {
                Object[] fila = new Object[3];

                for (int i = 0; i < 3; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                model.addRow(fila);
            }
            cn.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, "Error",
                            JOptionPane.ERROR_MESSAGE);
        }
    }

    private void restarDineroEnBaseDeDatos(String proveedor, String producto, float total) {

        try {
            // Establecer la conexión con la base de datos
            Connection conexion = Conexion.conectar();
            java.util.Date fecha = new java.util.Date();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(fecha.getTime());

            // Consultar la suma actual de la columna Cantidad_de_Dinero
            float sumaActual = obtenerSumaActual(conexion);

            // Verificar que la suma actual sea mayor que el total
            if (sumaActual >= total) {
                // Insertar el nuevo proveedor en la base de datos
                PreparedStatement pstInsertar = conexion
                        .prepareStatement("INSERT INTO dinero (Cantidad_de_Dinero, Fecha) VALUES (?, ?)");
                pstInsertar.setFloat(1, -total);
                pstInsertar.setTimestamp(2, timestamp);
                pstInsertar.executeUpdate();
                JOptionPane.showMessageDialog(this, "Transaccion con exito", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No hay suficiente dinero en la base de datos.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                // Puedes mostrar un mensaje o manejar la situación según tus necesidades
            }

            conexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private float obtenerSumaActual(Connection conexion) throws SQLException {
        float sumaActual = 0;

        // Consulta para obtener la suma actual de la columna Cantidad_de_Dinero
        String consulta = "SELECT SUM(Cantidad_de_Dinero) AS suma FROM dinero";

        try (PreparedStatement pst = conexion.prepareStatement(consulta)) {
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                sumaActual = rs.getFloat("suma");
            }
        }

        return sumaActual;
    }

    class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    class CustomTableModel extends DefaultTableModel {
        private int editableColumn;

        public CustomTableModel(int editableColumn) {
            this.editableColumn = editableColumn;
        }

        // Sobrescribir el método isCellEditable para hacer solo la columna deseada
        // editable
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == editableColumn;
        }
    }

}
