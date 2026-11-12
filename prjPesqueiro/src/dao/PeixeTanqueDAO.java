package dao;

import beans.PeixeTanque;
import pesqueiro.conexao.Conexao;
import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PeixeTanqueDAO {
    private Conexao conexao;
    private Connection conn;
    
    public PeixeTanqueDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public boolean inserir(PeixeTanque peixetanque) {
        String sql = "INSERT INTO peixetanque (idTanque, idEspecie, quantidade) VALUES (?,?,?) "
               + "ON DUPLICATE KEY UPDATE quantidade = quantidade + VALUES(quantidade)";

        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, peixetanque.getIdTanque());
            stmt.setInt(2, peixetanque.getIdEspecie());
            stmt.setInt(3, peixetanque.getQuantidade());

            stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir peixe em tanque" + ex.getMessage());
            return false;
        }
    }
    
     
    public List<PeixeTanque> listarPorTanque(int idTanque) {
        List<PeixeTanque> lista = new ArrayList<>();
        String sql = "SELECT pt.idTanque, pt.idEspecie, pt.quantidade, e.nome "
                   + "FROM peixetanque pt "
                   + "JOIN especie e ON e.id = pt.idEspecie "
                   + "WHERE pt.idTanque = ? "
                   + "ORDER BY e.nome";
 
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTanque);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PeixeTanque p = new PeixeTanque();
                    p.setIdTanque(rs.getInt("idTanque"));
                    p.setIdEspecie(rs.getInt("idEspecie"));
                    p.setQuantidade(rs.getInt("quantidade"));
                    p.setNomeEspecie(rs.getString("nome"));
                    lista.add(p);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar peixes do tanque: " + ex.getMessage());
        }
        return lista;
    }
    
    public void excluir(int idEspecie, int idTanque) {
        try {
            String sql = "delete from peixetanque WHERE idEspecie=? AND idTanque = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,idEspecie);
            stmt.setInt(2, idTanque);
            stmt.execute();
        } catch(SQLException ex) {
            System.out.println("Erro ao excluir especie "+ ex.getMessage());
        }
    }
    
    public boolean adicionar(int idTanque, int idEspecie, int quantidade) {
        String update = "UPDATE peixetanque SET quantidade = quantidade + ? "
                      + "WHERE idTanque = ? AND idEspecie = ?";
        String insert = "INSERT INTO peixetanque (idTanque, idEspecie, quantidade) VALUES (?,?,?)";
 
        try (PreparedStatement up = conn.prepareStatement(update)) {
            up.setInt(1, quantidade);
            up.setInt(2, idTanque);
            up.setInt(3, idEspecie);
 
            try (PreparedStatement ins = conn.prepareStatement(insert)) {
                ins.setInt(1, idTanque);
                ins.setInt(2, idEspecie);
                ins.setInt(3, quantidade);
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao adicionar peixe no tanque: " + ex.getMessage());
            return false;
        }
    }
    
    public void diminuir(int idTanque, int idEspecie, int quantidade) {
        try {
            String sql = "UPDATE peixetanque SET quantidade = quantidade - ? "
                       + "WHERE idTanque = ? AND idEspecie = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, quantidade);
            stmt.setInt(2, idTanque);
            stmt.setInt(3, idEspecie);
            stmt.execute();

            String sqlDel = "DELETE FROM peixetanque WHERE idTanque = ? AND idEspecie = ? AND quantidade <= 0";
            PreparedStatement del = conn.prepareStatement(sqlDel);
            del.setInt(1, idTanque);
            del.setInt(2, idEspecie);
            del.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao diminuir peixe do tanque: " + ex.getMessage());
        }
    }
    
    public int totalNoTanque(int idTanque) {
        try {
            String sql = "SELECT COALESCE(SUM(quantidade), 0) FROM peixetanque WHERE idTanque = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idTanque);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao somar peixes do tanque: " + ex.getMessage());
        }
        return 0;
}
}
