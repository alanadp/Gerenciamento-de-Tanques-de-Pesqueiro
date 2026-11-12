package dao;

import beans.Tanque;
import pesqueiro.conexao.Conexao;
import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TanqueDAO {
    private Conexao conexao;
    private Connection conn;
    
    public TanqueDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public void inserir(Tanque tanque) {
        String sql = "INSERT INTO tanque (nome, capacidade, ativo) VALUES (?,?,?);";
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1,tanque.getNome());
            stmt.setInt(2,tanque.getCapacidade());
            stmt.setBoolean(3,tanque.isAtivo());
            
            stmt.execute();
        } catch(SQLException ex) {
            System.out.println("Erro ao inserir tanque"+ ex.getMessage());
        }
    }
    
    public Tanque getTanque(int id) {
        String sql = "SELECT * FROM tanque WHERE id = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            Tanque t = new Tanque();
            
            rs.first();
            t.setId(id);
            t.setNome(rs.getString("nome"));
            t.setCapacidade(rs.getInt("capacidade"));
            t.setAtivo(rs.getBoolean("ativo"));
            return t;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar tanque: " + ex.getMessage());
            return null;
        }
    }
    
    public List<Tanque> getTanques() {
        
        String sql = "SELECT * FROM tanque";
 
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            ResultSet rs = stmt.executeQuery();
            List<Tanque> listaTanques = new ArrayList<>();
 
            while (rs.next()) {
                Tanque t = new Tanque();
                t.setId(rs.getInt("id"));
                t.setNome(rs.getString("nome"));
                t.setCapacidade(rs.getInt("capacidade"));
                t.setAtivo(rs.getBoolean("ativo"));
                listaTanques.add(t);
            }
            return listaTanques;
        } catch (SQLException ex) {
            System.out.println("Erro ao listar tanques: " + ex.getMessage());
            return null;
        }
          
    }
    
    public void editar(Tanque tanque) {
        try {
            String sql = "UPDATE tanque set nome=?, capacidade=?, ativo=? WHERE id=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,tanque.getNome());
            stmt.setInt(2,tanque.getCapacidade());
            stmt.setBoolean(3,tanque.isAtivo());
            stmt.setInt(4, tanque.getId()); 
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar tanque" + ex.getMessage());
        }
    }
    
    public void excluir(int id) {
        try {
            PreparedStatement filhos = conn.prepareStatement("DELETE FROM peixetanque WHERE idTanque=?");
            filhos.setInt(1, id);
            filhos.execute();

            PreparedStatement stmt = conn.prepareStatement("DELETE FROM tanque WHERE id=?");
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir tanque " + ex.getMessage());
      }
    }
    
    public List<Tanque> getTanquesNome(String nome) {
        String sql = "SELECT * FROM tanque WHERE nome LIKE ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1,"%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            List<Tanque> listaTanques = new ArrayList();
            
            while(rs.next()) {
                Tanque t = new Tanque();
                t.setId(rs.getInt("id"));
                t.setNome(rs.getString("nome"));
                t.setCapacidade(rs.getInt("capacidade"));
                t.setAtivo(rs.getBoolean("ativo"));
                listaTanques.add(t);
            }
            return listaTanques;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar todas os tanques: " + ex.getMessage());
            return null;
        }
    }
    
    public void alterarSituacao(int id, boolean ativo) {
        try {
            String sql = "UPDATE tanque SET ativo = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, ativo);
            stmt.setInt(2, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao alterar situacao do tanque: " + ex.getMessage());
        }
    }
}
