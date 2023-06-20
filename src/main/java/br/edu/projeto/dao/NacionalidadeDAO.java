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
import br.edu.projeto.model.Camiseta;
import br.edu.projeto.model.TipoNacionalidade;



@Stateful
public class NacionalidadeDAO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private DataSource ds;
    
    public List<TipoNacionalidade> obterTodos() {
    	List<TipoNacionalidade> tiposNacionalidade = new ArrayList<TipoNacionalidade>();
    	Connection con = null;//Conexão com a base
    	PreparedStatement ps = null;//Instrução SQL
    	ResultSet rs = null;//Resposta do SGBD
    	try {
			con = this.ds.getConnection();//Pegar um conexão
			ps = con.prepareStatement("SELECT id, tipo_nacionalidade FROM nacionalidades");
			rs = ps.executeQuery();
			while (rs.next()) {//Pega próxima linha do retorno
                TipoNacionalidade tipoNacionalidade = new TipoNacionalidade();
				tipoNacionalidade.setId(rs.getInt("id"));
                tipoNacionalidade.setTipo_nacionalidade(rs.getString("tipo_nacionalidade"));
                tiposNacionalidade.add(tipoNacionalidade);
			}
		} catch (SQLException e) {e.printStackTrace();
		} finally {
			DbUtil.closeResultSet(rs);
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
        return tiposNacionalidade;
    }

    // public TipoNacionalidade obterPorId(Integer id) {
    //     return null;
    // }

	public Boolean insert(TipoNacionalidade c) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("INSERT INTO nacionalidades (id, tipo_nacionalidade) VALUES (?, ?)");
				ps.setInt(1, c.getId());
				ps.setString(2, c.getTipo_nacionalidade());
					
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
    

	public Boolean update(TipoNacionalidade c) {
    Boolean resultado = false;
    Connection con = null;
    PreparedStatement ps = null;
    try {
        con = this.ds.getConnection();
        try {
            ps = con.prepareStatement("UPDATE nacionalidades SET tipo_nacionalidade = ? WHERE id = ?");
            ps.setString(1, c.getTipo_nacionalidade());
            ps.setInt(2, c.getId()); // Mantém o mesmo ID na cláusula WHERE
            ps.executeUpdate();
            resultado = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        DbUtil.closePreparedStatement(ps);
        DbUtil.closeConnection(con);
    }
    return resultado;
	}


	public boolean delete(TipoNacionalidade c) {
		if (c == null) {
			return false;
		}
		
		boolean resultado = false;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = this.ds.getConnection();
			
			// Verifica se o TipoNacionalidade está em uso na tabela "cliente"
			ps = con.prepareStatement("SELECT COUNT(*) FROM cliente WHERE tipo_nacionalidade_id = ?");
			ps.setInt(1, c.getId());
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			
			if (count == 0) {
				// O objeto TipoNacionalidade não está em uso
				ps.close();
				ps = con.prepareStatement("DELETE FROM nacionalidades WHERE id = ?");
				ps.setInt(1, c.getId());
				int rowsAffected = ps.executeUpdate();
				
				if (rowsAffected > 0) {
					resultado = true;
				}
			} else {
				// O objeto TipoNacionalidade está em uso na tabela "cliente"
				resultado = false;
				System.out.println("O tipo de nacionalidade não pode ser excluído, pois está em uso.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return resultado;
	}

}