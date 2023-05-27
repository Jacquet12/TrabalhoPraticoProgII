package br.edu.projeto.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.sql.DataSource;
import br.edu.projeto.util.DbUtil;
import br.edu.projeto.model.Cliente;
import br.edu.projeto.model.Cliente;
//Classe DAO responsável pelas regras de negócio envolvendo operações de persistência de dados
//Indica-se a acriação de um DAO específico para cada Modelo

//Anotação EJB que indica que Bean (objeto criado para a classe) será comum para toda a aplicação
//Isso faz com que recursos computacionais sejam otimizados e garante maior segurança nas transações com o banco
@Stateful
public class modeloDAO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private DataSource ds;
    
    public List<Cliente> listAll() {
    	List<Cliente> clientes = new ArrayList<Cliente>();
    	Connection con = null;//Conexão com a base
    	PreparedStatement ps = null;//Instrução SQL
    	ResultSet rs = null;//Resposta do SGBD
    	try {
			con = this.ds.getConnection();//Pegar um conexão
			ps = con.prepareStatement("SELECT cliente_nome,cliente_nome_social,cpf_cliente, altura_cliente ,massa_cliente,genero_cliente,idade_cliente,email_ciente,telefone_cliente,endereco_cliente FROM cliente");
			rs = ps.executeQuery();
			while (rs.next()) {//Pega próxima linha do retorno
				Cliente c = new Cliente();
				c.setNome(rs.getString("cliente_nome"));
				c.setNomeSocial(rs.getString("cliente_nome_social"));
				c.setCpf(rs.getString("cpf_cliente"));
                c.setAltura(rs.getInt("altura_cliente"));
				c.setMassa(rs.getInt("massa_cliente"));
                c.setGenero(rs.getString("genero_cliente"));
				c.setIdade(rs.getInt("idade_cliente"));
                c.setEmail(rs.getString("email_ciente"));
                c.setTelefone(rs.getString("telefone_cliente"));
                c.setEndereco(rs.getString("endereco_cliente"));
				clientes.add(c);
			}
		} catch (SQLException e) {e.printStackTrace();
		} finally {
			DbUtil.closeResultSet(rs);
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
        return clientes;
    }
    
    public List<Cliente> listBycpf(String cpf) {
    	List<Cliente> clientes = new ArrayList<Cliente>();
    	Connection con = null;//Conexão com a base
    	PreparedStatement ps = null;//Instrução SQLss
    	ResultSet rs = null;//Resposta do SGBD
    	try {
			con = this.ds.getConnection();//Pegar um conexão
			ps = con.prepareStatement("SELECT cliente_nome,cliente_nome_social,cpf_cliente, altura_cliente ,massa_cliente,genero_cliente,idade_cliente,email_ciente,telefone_cliente,endereco_cliente FROM cliente WHERE cpf = ?");
			ps.setString(1, cpf);
			rs = ps.executeQuery();
			while (rs.next()) {//Pega próxima linha do retorno
				Cliente c = new Cliente();
                c.setNome(rs.getString("cliente_nome"));
				c.setNomeSocial(rs.getString("cliente_nome_social"));
				c.setCpf(rs.getString("cpf_cliente"));
                c.setAltura(rs.getInt("altura_cliente"));
                c.setMassa(rs.getInt("massa_cliente"));
                c.setGenero(rs.getString("genero_cliente"));
                c.setIdade(rs.getInt("idade_cliente"));
                c.setEmail(rs.getString("email_ciente"));
                c.setTelefone(rs.getString("telefone_cliente"));
                c.setEndereco(rs.getString("endereco_cliente"));


				clientes.add(c);
			}
		} catch (SQLException e) {e.printStackTrace();
		} finally {
			DbUtil.closeResultSet(rs);
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
        return clientes;
    }
       
    public Boolean insert(Cliente c) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("INSERT INTO cliente (cliente_nome,cliente_nome_social,cpf_cliente , altura_cliente ,massa_cliente,genero_cliente,idade_cliente,email_ciente,telefone_cliente,endereco_cliente) VALUES (?, ?,?,?,?,?,?,?,?,?)");
                ps.setString(1, c.getNome());
                ps.setString(2, c.getNomeSocial());
				ps.setString(3, c.getCpf());
				ps.setInt(4, c.getAltura());
				ps.setInt(5, c.getMassa());
				ps.setString(6, c.getGenero());
                ps.setInt(7, c.getIdade());
				ps.setString(8, c.getEmail());
				ps.setString(9, c.getTelefone());
                ps.setString(10, c.getEndereco());
				ps.execute();
				resultado = true;
			} catch (SQLException e) {e.printStackTrace();}
    	} catch (SQLException e) {e.printStackTrace();
    	} finally {
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
    	return resultado;
    }
    
    public Boolean update(Cliente c) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("UPDATE cliente SET cliente_nome = ?,cliente_nome_social = ?, cpf_cliente= ? ,massa_cliente=?,altura_cliente =?,genero_cliente =?,idade_ciente =?,email_cliente =?,telefone_cliente=?, endereco_cliente=? WHERE cpf_cliente = ?");
				
                ps.setString(1, c.getNome());
                ps.setString(2, c.getNomeSocial());
				ps.setString(3, c.getCpf());
				ps.setInt(4, c.getMassa());
				ps.setDoubles(5, c.getAltura());
				ps.setString(6, c.getGenero());
				ps.setInt(7, c.getIdade());
				ps.setString(8, c.getEmail());
				ps.setString(9, c.getTelefone());
				ps.setString(10, c.getEndereco());               
				ps.execute();	
				resultado = true;
			} catch (SQLException e) {e.printStackTrace();}
    	} catch (SQLException e) {e.printStackTrace();
    	} finally {
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
    	return resultado;
    }
    
    public Boolean delete(Cliente c) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("DELETE FROM cliente WHERE cpf_cliente = ?");
				ps.setString(1, c.getCpf());
				ps.execute();
				resultado = true;
			} catch (SQLException e) {e.printStackTrace();}
    	} catch (SQLException e) {e.printStackTrace();
    	} finally {
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
    	return resultado;
    }
}


