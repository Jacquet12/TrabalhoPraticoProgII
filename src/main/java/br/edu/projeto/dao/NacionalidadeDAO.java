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
			ps = con.prepareStatement("SELEC id, tipo_nacionalidade FROM nacionalidades");
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
    
    
}