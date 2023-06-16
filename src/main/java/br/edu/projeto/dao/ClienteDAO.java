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
			ps = con.prepareStatement("SELECT cliente_nome,cliente_nome_social,cpf_cliente, altura_cliente ,massa_cliente,genero_cliente,idade_cliente,email_cliente,telefone_cliente,endereco_cliente FROM cliente");
			rs = ps.executeQuery();
			while (rs.next()) {//Pega próxima linha do retorno
				Cliente c = new Cliente();
				c.setNome(rs.getString("cliente_nome"));
				c.setNomeSocial(rs.getString("cliente_nome_social"));
				c.setCpf(rs.getString("cpf_cliente"));
                c.setAltura(rs.getDouble("altura_cliente"));
				c.setMassa(rs.getInt("massa_cliente"));
                c.setGenero(rs.getString("genero_cliente"));
				c.setIdade(rs.getInt("idade_cliente"));
                c.setEmail(rs.getString("email_cliente"));
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
			ps = con.prepareStatement("SELECT cliente_nome,cliente_nome_social,cpf_cliente, altura_cliente ,massa_cliente,genero_cliente,idade_cliente,email_cliente,telefone_cliente,endereco_cliente FROM cliente WHERE cpf = ?");
			ps.setString(1, cpf);
			rs = ps.executeQuery();
			while (rs.next()) {//Pega próxima linha do retorno
				Cliente c = new Cliente();
                c.setNome(rs.getString("cliente_nome"));
				c.setNomeSocial(rs.getString("cliente_nome_social"));
				c.setCpf(rs.getString("cpf_cliente"));
                c.setAltura(rs.getDouble("altura_cliente"));
                c.setMassa(rs.getInt("massa_cliente"));
                c.setGenero(rs.getString("genero_cliente"));
                c.setIdade(rs.getInt("idade_cliente"));
                c.setEmail(rs.getString("email_cliente"));
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
				ps = con.prepareStatement("INSERT INTO cliente (cliente_nome,cliente_nome_social,cpf_cliente , altura_cliente ,massa_cliente,genero_cliente,idade_cliente,email_cliente,telefone_cliente,endereco_cliente, tipo_nacionalidade_id) VALUES (?, ?,?,?,?,?,?,?,?,?,?)");
                ps.setString(1, c.getNome());
                ps.setString(2, c.getNomeSocial());
				ps.setString(3, c.getCpf());
				ps.setDouble(4, c.getAltura());
				ps.setInt(5, c.getMassa());
				ps.setString(6, c.getGenero());
                ps.setInt(7, c.getIdade());
				ps.setString(8, c.getEmail());
				ps.setString(9, c.getTelefone());
                ps.setString(10, c.getEndereco());
				ps.setString(11, c.getNacionalidade());
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
				ps = con.prepareStatement("UPDATE cliente SET cliente_nome_social=? , altura_cliente=? ,massa_cliente=?,genero_cliente=?,idade_cliente=?,email_cliente=?,telefone_cliente=?,endereco_cliente=? WHERE cpf_cliente = ?");
				
                ps.setString(1, c.getNomeSocial());
				ps.setDouble(2, c.getAltura());
				ps.setInt(3, c.getMassa());
				ps.setString(4, c.getGenero());
				ps.setInt(5, c.getIdade());
				ps.setString(6, c.getEmail());
				ps.setString(7, c.getTelefone());
				ps.setString(8, c.getEndereco()); 
				ps.setString(9, c.getCpf());              
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

	

	public List<Cliente> ClienteFiltrado(String nomeFiltrado) {
		List<Cliente> clientesFiltrados = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = this.ds.getConnection();
			ps = con.prepareStatement("SELECT cliente_nome, cliente_nome_social, cpf_cliente, altura_cliente, massa_cliente, genero_cliente, idade_cliente, email_cliente, telefone_cliente, endereco_cliente FROM cliente WHERE cliente_nome LIKE ?");
			ps.setString(1, "%" + nomeFiltrado + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				Cliente c = new Cliente();
				c.setNome(rs.getString("cliente_nome"));
				c.setNomeSocial(rs.getString("cliente_nome_social"));
				c.setCpf(rs.getString("cpf_cliente"));
				c.setAltura(rs.getDouble("altura_cliente"));
				c.setMassa(rs.getInt("massa_cliente"));
				c.setGenero(rs.getString("genero_cliente"));
				c.setIdade(rs.getInt("idade_cliente"));
				c.setEmail(rs.getString("email_cliente"));
				c.setTelefone(rs.getString("telefone_cliente"));
				c.setEndereco(rs.getString("endereco_cliente"));

				if (verificarNomeFiltrado(c.getNome(), nomeFiltrado)) {
					clientesFiltrados.add(c);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return clientesFiltrados;
	}

	private boolean verificarNomeFiltrado(String nomeCliente, String nomeFiltrado) {
		if (nomeCliente.length() >= 2) {
			for (int i = 0; i <= nomeCliente.length() - 2; i++) {
				if (nomeCliente.substring(i, i + 2).equalsIgnoreCase(nomeFiltrado.substring(0, 2))) {
					return true;
				}
			}
		}
		return false;
	}


	public List<Cliente> ClienteFiltradoPorgenero(String generoFiltrado) {
		List<Cliente> clientesFiltradoPorgenero = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = this.ds.getConnection();
			if ("Homem".equals(generoFiltrado) || "Mulher".equals(generoFiltrado) || "Outro".equals(generoFiltrado)) {
				ps = con.prepareStatement("SELECT cliente_nome, cliente_nome_social, cpf_cliente, altura_cliente, massa_cliente, genero_cliente, idade_cliente, email_cliente, telefone_cliente, endereco_cliente FROM cliente WHERE genero_cliente LIKE ?");
				ps.setString(1, "%" + generoFiltrado + "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					Cliente c = new Cliente();
					c.setNome(rs.getString("cliente_nome"));
					c.setNomeSocial(rs.getString("cliente_nome_social"));
					c.setCpf(rs.getString("cpf_cliente"));
					c.setAltura(rs.getDouble("altura_cliente"));
					c.setMassa(rs.getInt("massa_cliente"));
					c.setGenero(rs.getString("genero_cliente"));
					c.setIdade(rs.getInt("idade_cliente"));
					c.setEmail(rs.getString("email_cliente"));
					c.setTelefone(rs.getString("telefone_cliente"));
					c.setEndereco(rs.getString("endereco_cliente"));
					clientesFiltradoPorgenero.add(c);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return clientesFiltradoPorgenero;
	}
	
}



