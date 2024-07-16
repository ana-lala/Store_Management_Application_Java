/**
 * La clase VentanaVenta representa una ventana de interfaz gráfica para gestionar ventas de productos.
 * Incluye funcionalidades para agregar productos a una tabla, calcular el subtotal y el total,
 * actualizar las cantidades de productos en una base de datos al realizar una venta,
 * y registrar la cantidad vendida y la fecha de la venta.
 * 
 * La clase utiliza componentes Swing para la interfaz gráfica y se conecta a una base de datos mediante
 * la clase Conexion proporcionada.
 * 
 * La clase EscuchadorBotones es una implementación de ActionListener para manejar las acciones de los botones.
 * 
 * Nota: Este código asume la existencia de una clase Conexion para la conectividad a la base de datos.
 * @author Ana Laura Chenoweth Galaz 
 */
package guis.ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.DecimalFormat;
import java.awt.Font;
import utilities.Conexion;

public class VentanaVenta extends javax.swing.JFrame {

    // Declaración de componentes y variables
    private javax.swing.JPanel panelDerecho;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelSuperior;
    private DefaultTableModel model = new DefaultTableModel();
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel iconUser;
    private javax.swing.JLabel labelIva;
    private javax.swing.JLabel labelSubTotal;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JLabel idLabel;
    private javax.swing.JLabel tituloLabel;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JSpinner cantProducto;
    private javax.swing.JTextField obtenerProducto;
    private Conexion Conexion;
    private float cobroTotal;
    private float cobroSubTotal;
    private static final float IVA = 1.16f;
    private static final java.text.DecimalFormat df = new DecimalFormat("#.##");

    public VentanaVenta(Conexion Conexion) {
        // Constructor para VentanaVenta

        // Configurar el diseño inicial de la interfaz gráfica y hacer visible la ventana
        this.Conexion = Conexion;
        setUndecorated(true); // Esto quita la barra de título y botones

        setSize(900, 600);
        setLocationRelativeTo(null);
        initComponets();
        crearTabla();

        setVisible(true);
    }

    private void initComponets() {
        // Inicializar y configurar los componentes de la interfaz gráfica

        
        this.setLayout(new BorderLayout());
        panelDerecho = new javax.swing.JPanel();
        panelTabla = new javax.swing.JPanel();
        panelBotones = new javax.swing.JPanel();
        panelSuperior = new javax.swing.JPanel();
        iconUser = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        btnCobrar = new javax.swing.JButton();
        btnAgregarProducto = new javax.swing.JButton();

        labelIva = new javax.swing.JLabel();
        tituloLabel = new javax.swing.JLabel("Tienda");
        labelSubTotal = new javax.swing.JLabel();
        idLabel = new javax.swing.JLabel();
        labelTotal = new javax.swing.JLabel();
        cantProducto = new javax.swing.JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        obtenerProducto = new javax.swing.JTextField();

        labelIva.setText("Iva: " + Float.parseFloat(df.format(IVA - 1)));
        labelSubTotal.setText("Sub Total: ");
        labelTotal.setText("Total: ");
        idLabel.setText("Id:");

        panelDerecho.setBackground(new java.awt.Color(154, 143, 141));
        panelDerecho.setLayout(new GridLayout(5, 1));

        panelTabla.setBackground(new java.awt.Color(154, 143, 141));
        panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));
         
        panelBotones.setBackground(new java.awt.Color(154, 143, 141));
        panelBotones.setLayout(new GridLayout(4, 2));

        panelSuperior.setBackground(new java.awt.Color(82, 78, 77));
        panelSuperior.setLayout(new BorderLayout());
        panelSuperior.setPreferredSize(new Dimension(900, 70));
        panelSuperior.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        iconUser.setIcon(new javax.swing.ImageIcon("src/utilities/imagenes/usuario.png"));
        iconUser.setText("Usuario");
       
        agregarPaneles();
        jTable1 = new JTable(model);

        jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(jTable1); 

        panelTabla.add(jScrollPane1);

        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tituloLabel.setHorizontalAlignment(JLabel.CENTER);

        panelSuperior.add(iconUser, BorderLayout.WEST);
        panelSuperior.add(tituloLabel, BorderLayout.CENTER);
        
        panelTabla.add(btnRegresar);

        this.add(panelTabla, BorderLayout.WEST);
        this.add(panelDerecho, BorderLayout.CENTER);
        this.add(panelSuperior, BorderLayout.NORTH);
    }

    private void crearTabla() {
        // Crear y configurar la tabla para mostrar productos
        jTable1 = new JTable(model);
        jTable1.setEnabled(false);
        jScrollPane1.setViewportView(jTable1);

        model.addColumn(" Id");
        model.addColumn(" Nombre");
        model.addColumn(" Precio");
        model.addColumn(" Cantidad");
    }

    private void agregarPaneles() {
        // Agregar paneles y configurar el diseño
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new EscuchadorBotones(this));

        btnCobrar.setText("Cobrar");
        btnCobrar.addActionListener(new EscuchadorBotones(this));

        btnAgregarProducto.setText("Agregar");
        btnAgregarProducto.addActionListener(new EscuchadorBotones(this));

        panelBotones.add(idLabel);
        panelBotones.add(obtenerProducto);
        panelBotones.add(labelSubTotal);
        panelBotones.add(btnAgregarProducto);
        panelBotones.add(labelIva);
        panelBotones.add(cantProducto);
        panelBotones.add(labelTotal);
        panelBotones.add(btnCobrar);

        for (int i = 0; i < 4; i++)
            panelDerecho.add(new JPanel() {
                {
                    setBackground(new java.awt.Color(154, 143, 141));
                }
            });
        panelDerecho.add(panelBotones);
    }

    public void cobrar() {
        // Realizar la operación de venta, actualizar las cantidades de productos y mostrar mensajes de éxito o error
        if (jTable1.getRowCount() > 0) {
            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = null;
                ResultSet rs = null;

                pst = cn.prepareStatement("UPDATE productos SET cantidad = cantidad - ? WHERE id = ?");

                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    int id = Integer.parseInt(model.getValueAt(i, 0).toString());
                    int cantidadVendida = Integer.parseInt(model.getValueAt(i, 3).toString());

                    // Obtener la cantidad actual en la base de datos
                    PreparedStatement pst2 = cn.prepareStatement("SELECT cantidad FROM productos WHERE id = ?");
                    pst2.setInt(1, id);
                    rs = pst2.executeQuery();

                    if (rs.next()) {
                        int cantidadActual = rs.getInt("cantidad");

                        // Verificar si hay suficiente cantidad para la venta
                        if (cantidadActual >= cantidadVendida) {
                            // Actualizar la cantidad en la base de datos
                            pst.setInt(1, cantidadVendida);
                            pst.setInt(2, id);
                            pst.executeUpdate();
                            
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "No hay suficiente cantidad disponible para el producto con ID: " + id, "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    pst2.close();
                }
                actualizarDinero();
                JOptionPane.showMessageDialog(null, "Cobro exitoso", "Hecho",
                JOptionPane.INFORMATION_MESSAGE);

                cn.close();
                pst.close();
                rs.close();
                model.setRowCount(0);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                                              "Error " + e.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay productos para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void agregarProducto() {
         // Agregar un producto a la tabla según la entrada del usuario, verificar la existencia del producto y actualizar el total
        String id = obtenerProducto.getText();
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = null;
            if (ExisteProducto(id, cn, pst)  && Integer.parseInt(cantProducto.getValue().toString()) > 0) {
                pst = cn.prepareStatement("select id,nombre,precio from productos where id = '" + id + "'");
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    Object[] fila = new Object[4];

                    fila[0] = rs.getString("id");
                    fila[1] = rs.getString("nombre");
                    fila[2] = rs.getString("precio");
                    fila[3] = Integer.parseInt(cantProducto.getValue().toString());
                    model.addRow(fila);
                    obtenerProducto.setText("");
                    obtenerCobro();
                    pst.close();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el producto", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            cn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, "Error",
                            JOptionPane.ERROR_MESSAGE);
        }
    }

    private void obtenerCobro() {
        // Calcular el subtotal y el total según los productos en la tabla
        float value = 0;
        for (int row = 0; row < model.getRowCount(); row++) {
            try {
                // Intentar convertir el valor a Integer
                value += Float.parseFloat(model.getValueAt(row, 2).toString())*Float.parseFloat(model.getValueAt(row, 3).toString());
            } catch (NumberFormatException e) {
                // Manejar la excepción si no se puede convertir a Integer
                JOptionPane.showMessageDialog(null, "Error " +  e, "Error",
                            JOptionPane.ERROR_MESSAGE);
            }
        }
        cobroSubTotal = Float.parseFloat(df.format(value));
        cobroTotal = Float.parseFloat(df.format(value * IVA));
        
        labelSubTotal.setText("Sub Total: " + cobroSubTotal);
        
        labelTotal.setText("Total: " + cobroTotal);
        cantProducto.setValue(0);
    }

    private boolean ExisteProducto(String id, Connection cn, PreparedStatement pst) {
         // Verificar si un producto con el ID dado existe y tiene una cantidad suficienet
        try {
            pst = cn.prepareStatement("select cantidad from productos where id = '" + id + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cantidad") >= Integer.parseInt(cantProducto.getValue().toString())) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "No hay suficiente cantidad", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, "Error",
                            JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    private void actualizarDinero() {
        // Actualizar la base de datos con la cantidad vendida y la fecha actual
        try {
            Connection conexion = Conexion.conectar();

            // Obtiene la fecha y hora actual
            java.util.Date fecha = new java.util.Date();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(fecha.getTime());

            // Crea la sentencia SQL para insertar dinero y fecha actual
            PreparedStatement pst = conexion.prepareStatement("INSERT INTO dinero (Cantidad_de_Dinero, Fecha) VALUES ( ?, ?)");

            // Establece los parámetros (monto y fecha)
            pst.setFloat(1, cobroSubTotal);
            pst.setTimestamp(2, timestamp);

            // Ejecuta la inserción
            pst.executeUpdate();

            // Cierra la conexión y el PreparedStatement
            conexion.close();
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class EscuchadorBotones implements ActionListener {

    VentanaVenta v;

    public EscuchadorBotones(VentanaVenta v) {
        this.v = v;
    }

    public void actionPerformed(java.awt.event.ActionEvent evt) {
        // Manejar las acciones de los botones según el comando de acción
        if (evt.getActionCommand().equals("Agregar"))
            v.agregarProducto();
        else if (evt.getActionCommand().equals("Cobrar"))
            v.cobrar();
        else if (evt.getActionCommand().equals("Regresar"))
            v.dispose();
    }
}
