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

import br.edu.projeto.model.Camiseta;
import br.edu.projeto.model.Cliente;
import br.edu.projeto.util.DbUtil;

//Classe DAO responsável pelas regras de negócio envolvendo operações de persistência de dados
//Indica-se a acriação de um DAO específico para cada Modelo

//Anotação EJB que indica que Bean (objeto criado para a classe) será comum para toda a aplicação
//Isso faz com que recursos computacionais sejam otimizados e garante maior segurança nas transações com o banco
@Stateful
public class ClienteDAO implements Serializable{
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
			ps = con.prepareStatement("SELECT cliente_nome,cliente_nome_social,cpf_cliente text, altura_cliente ,massa_cliente,genero_cliente,idade_cliente,email_ciente,telefone_cliente,endereco_cliente FROM cliente");
			rs = ps.executeQuery();
			while (rs.next()) {//Pega próxima linha do retorno
				Cliente c = new Cliente();
				c.setNome(rs.getString("nome"));
				c.setNomeSocial(rs.getString("nomeSocial"));
				c.setTelefone(rs.getString("telefone"));
                c.setIdade(rs.getInt("idade"));
                c.setGenero(rs.getString("genero"));
                c.setEmail(rs.getString("email"));
                c.setCpf(rs.getString("cpf"));
                c.setCelular(rs.getString("celular"));
                c.setAltura(rs.getInt("altura"));
                c.setMassa(rs.getInt("massa"));


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
    	PreparedStatement ps = null;//Instrução SQL
    	ResultSet rs = null;//Resposta do SGBD
    	try {
			con = this.ds.getConnection();//Pegar um conexão
			ps = con.prepareStatement("SELECT cliente_nome,cliente_nome_social,cpf_cliente text, altura_cliente ,massa_cliente,genero_cliente,idade_cliente,email_ciente,telefone_cliente,endereco_cliente FROM camiseta WHERE cpf = ?");
			ps.setString(1, cpf);
			rs = ps.executeQuery();
			while (rs.next()) {//Pega próxima linha do retorno
				Cliente c = new Cliente();
                c.setNome(rs.getString("nome"));
				c.setNomeSocial(rs.getString("nomeSocial"));
				c.setTelefone(rs.getString("telefone"));
                c.setIdade(rs.getInt("idade"));
                c.setGenero(rs.getString("genero"));
                c.setEmail(rs.getString("email"));
                c.setCpf(rs.getString("cpf"));
                c.setCelular(rs.getString("celular"));
                c.setAltura(rs.getInt("altura"));
                c.setMassa(rs.getInt("massa"));


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
				ps = con.prepareStatement("INSERT INTO cliente (cliente_nome,cliente_nome_social,cpf_cliente text, altura_cliente ,massa_cliente,genero_cliente,idade_cliente,email_ciente,telefone_cliente,endereco_cliente) VALUES (?, ?,?,?,?,?,?,?,?,?)");
                ps.setString(1, c.getCelular());
                ps.setString(2, c.getCpf());
                ps.setString(3, c.getEmail());
                ps.setString(4, c.getNome());
                ps.setString(5, c.getNomeSocial());
                ps.setString(6, c.getGenero());
                ps.setInt(7, c.getAltura());
                ps.setInt(8, c.getIdade());
                ps.setInt(9, c.getMassa());
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
    
    public Boolean update(Camiseta c) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("UPDATE camiseta SET tamanho = ?, descricao = ? WHERE id_camiseta = ?");
				ps.setString(1, c.getTamanho());
				ps.setString(2, c.getDescricao());
				ps.setLong(3, c.getIdCamiseta());
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
    
    public Boolean delete(Camiseta c) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("DELETE FROM camiseta WHERE id_camiseta = ?");
				ps.setLong(1, c.getIdCamiseta());
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

