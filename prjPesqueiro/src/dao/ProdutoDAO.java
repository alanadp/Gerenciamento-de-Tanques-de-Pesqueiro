package dao;

import beans.Produto;
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

public class ProdutoDAO {
    private Conexao conexao;
    private Connection conn;
    
    public ProdutoDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public void inserir(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, descricao) VALUES (?,?,?);";
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1,produto.getNome());
            stmt.setDouble(2,produto.getPreco());
            stmt.setString(3,produto.getDescricao());
            
            stmt.execute();
        } catch(SQLException ex) {
            System.out.println("Erro ao inserir produto"+ ex.getMessage());
        }
    }
    
    public Produto getProduto(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            Produto p = new Produto();
            
            rs.first();
            p.setId(id);
            p.setNome(rs.getString("nome"));
            p.setPreco(rs.getDouble("preco"));
            p.setDescricao(rs.getString("descricao"));
            return p;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar produto: " + ex.getMessage());
            return null;
        }
    }
    
    /*
    public List<Produto> getProdutos() {
        
        String sql = "SELECT * FROM produto";
 
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            ResultSet rs = stmt.executeQuery();
            List<Produto> listaProdutos = new ArrayList<>();
 
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                p.setDescricao(rs.getString("descricao"));
                listaProdutos.add(p);
            }
            return listaProdutos;
        } catch (SQLException ex) {
            System.out.println("Erro ao listar produtos: " + ex.getMessage());
            return null;
        }
          
    }
*/
    
    public void editar(Produto produto) {
        try {
            String sql = "UPDATE produto set nome=?, preco=?, descricao=? WHERE id=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,produto.getNome());
            stmt.setDouble(2,produto.getPreco());
            stmt.setString(3,produto.getDescricao());
            stmt.setInt(4, produto.getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar produto" + ex.getMessage());
        }
    }
    
    public void excluir(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM produto WHERE id=?");
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir produto " + ex.getMessage());
        }
    }
    
    public List<Produto> getProdutosNome(String nome) {
        String sql = "SELECT * FROM produto WHERE nome LIKE ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1,"%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            List<Produto> listaProdutos = new ArrayList();
            
            while(rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                p.setDescricao(rs.getString("descricao"));
                listaProdutos
                        .add(p);
            }
            return listaProdutos;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar todas os produtos: " + ex.getMessage());
            return null;
        }
    }
    
}
