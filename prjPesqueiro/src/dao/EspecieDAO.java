package dao;

import beans.Especie;
import pesqueiro.conexao.Conexao;
import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EspecieDAO {
    private Conexao conexao;
    private Connection conn;
    
    public EspecieDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public void inserir(Especie especie) {
        String sql = "INSERT INTO especie (nome, precoKg, descricao) VALUES (?,?,?);";
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1,especie.getNome());
            stmt.setDouble(2,especie.getPrecoKg());
            stmt.setString(3,especie.getDescricao());
            
            stmt.execute();
        } catch(SQLException ex) {
            System.out.println("Erro ao inserir especie"+ ex.getMessage());
        }
    }
    
    public Especie getEspecie(int id) {
        String sql = "SELECT * FROM especie WHERE id = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            Especie e = new Especie();
            
            rs.first();
            e.setId(id);
            e.setNome(rs.getString("nome"));
            e.setPrecoKg(rs.getDouble("precoKg"));
            e.setDescricao(rs.getString("descricao"));
            return e;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar especie: " + ex.getMessage());
            return null;
        }
    }
    
    public List<Especie> getEspecies() {
        
        String sql = "SELECT * FROM especie";
 
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            ResultSet rs = stmt.executeQuery();
            List<Especie> listaEspecies = new ArrayList<>();
 
            while (rs.next()) {
                Especie e = new Especie();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setPrecoKg(rs.getDouble("precoKg"));
                e.setDescricao(rs.getString("descricao"));
                listaEspecies.add(e);
            }
            return listaEspecies;
        } catch (SQLException ex) {
            System.out.println("Erro ao listar especies: " + ex.getMessage());
            return null;
        }
          
    }
    
    public void editar(Especie especie) {
        try {
            String sql = "UPDATE especie set nome=?, precokg=?, descricao=? WHERE id=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,especie.getNome());
            stmt.setDouble(2,especie.getPrecoKg());
            stmt.setString(3,especie.getDescricao());
            stmt.setInt(4, especie.getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar especie" + ex.getMessage());
        }
    }
    
    public void excluir(int id) {
        try {
            PreparedStatement filhos = conn.prepareStatement("DELETE FROM peixetanque WHERE idEspecie=?");
            filhos.setInt(1, id);
            filhos.execute();

            PreparedStatement stmt = conn.prepareStatement("DELETE FROM especie WHERE id=?");
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir especie " + ex.getMessage());
        }
    }
    
    public List<Especie> getEspeciesNome(String nome) {
        String sql = "SELECT * FROM especie WHERE nome LIKE ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1,"%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            List<Especie> listaEspecies = new ArrayList();
            
            while(rs.next()) {
                Especie e = new Especie();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setPrecoKg(rs.getDouble("precoKg"));
                e.setDescricao(rs.getString("descricao"));
                listaEspecies.add(e);
            }
            return listaEspecies;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar todas as especies: " + ex.getMessage());
            return null;
        }
    }
}
