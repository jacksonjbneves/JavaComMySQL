
package com.mycompany.javacommysql;
import java.sql.*;
/* @author Jackson */

public class JavaComMySQL {

    public static void main(String[] args) {
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            // Configurações de conexão
            String url = "jdbc:mysql://localhost:3306/mydb";
            String usuario = "root";
            String senha = "";
            
            // Estabelecer conexão com o banco de dados
            conn = DriverManager.getConnection(url, usuario, senha);
            
            // Criar um objeto Statement para enviar consultas SQL
            stmt = conn.createStatement();
            
            // Consulta SQL
            //String sql = "SELECT * FROM clientes";
            
            String sql = "SELECT CliComp.nomeCLiente, CliComp.emailCliente, CliComp.idadeCliente, SUM(CliComp.precoProduto) AS SomaTotal"+ 
                       " FROM (SELECT c.nomeCliente, c.emailCliente, c.idadeCliente, p.nomeProduto, p.precoProduto FROM clientes c"+
                           " INNER JOIN Compras co ON co.idCliente = c.idCliente"+
                           " INNER JOIN Produtos p ON p.idProduto = co.idProduto) CliComp"+
                       " GROUP BY CliComp.nomeCliente;";
            
            // Executar a consulta e obter o resultado
            rs = stmt.executeQuery(sql);
            
            // Processar os resultados
            while (rs.next()) {
                //String nome = rs.getString("nome");
                /*int idCliente = rs.getInt("idCliente");
                String nomeCliente = rs.getString("nomeCliente");*/                
                //System.out.println("ID: " + idCliente + ", Nome: " + nomeCliente);
                
                String nomeCLiente = rs.getString("nomeCLiente");
                String emailCliente = rs.getString("emailCliente");
                String idadeCliente = rs.getString("idadeCliente");
                int SomaTotal = rs.getInt("SomaTotal");
                // Aqui você pode fazer o que quiser com os dados, como armazená-los em objetos Java
                System.out.println("NomeCliente: "+nomeCLiente+", EmailCliente: "+emailCliente+", IdadeCliente: "+idadeCliente+", SomaTotal: "+SomaTotal);
                
            }
            
            System.out.println("END CODE SQL");
            
        } catch (SQLException  e) {
            System.out.println("ERROR CODE SQL");
            e.printStackTrace();            
        } finally {
            // Fechar os recursos em uma ordem reversa
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* Ignorar */ }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { /* Ignorar */ }
            try { if (conn != null) conn.close(); } catch (SQLException e) { /* Ignorar */ }
        }
    
    }
}
