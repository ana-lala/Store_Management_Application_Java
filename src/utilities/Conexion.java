/**
 *La clase Conexion proporciona métodos para establecer y gestionar la conexión a una base de datos MySQL. Incluye un método para establecer la conexión y otro para obtener la conexión.
 *@author Ana Laura Chenoweth Galaz
 */
package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public String URL,usuario,contrasena,controler;

    public Conexion(){
        URL = "jdbc:mysql://localhost:3306/proyecto-programacion";
        usuario = "root";
        contrasena = "";
        controler = "com.mysql.cj.jdbc.Driver";
    }
    public void conectar(Connection connection) {
        try {
            // Cargar el controlador JDBC
            Class.forName(controler);

            // Establecer la conexión con la base de datos
            connection = DriverManager.getConnection(URL, usuario, contrasena);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection conectar() throws SQLException{
        try {
            // Cargar el controlador JDBC
            Class.forName(controler);
            return DriverManager.getConnection(URL, usuario, contrasena);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setURL(String url){this.URL = url;}
    public void setUser(String user){this.usuario = user;}
    public void setPass(String contrasena){this.contrasena = contrasena;}
}
