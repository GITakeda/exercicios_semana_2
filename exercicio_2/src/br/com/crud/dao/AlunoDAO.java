package br.com.crud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.crud.factory.ConnectionFactory;
import br.com.crud.model.Aluno;

public class AlunoDAO {

	public int save(Aluno aluno) {
		if(aluno.getId() != 0) {
			return alterar(aluno);
		}
		
		return gravar(aluno);
	}
	
	public int save(int id, String nome, int idade) {
		Aluno a = new Aluno(id, nome, idade);
		
		return save(a);
	}
	
	public int gravar(Aluno aluno) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO ALUNO");
		sql.append("(nome, idade)");
		sql.append("VALUES (?, ?)");
		
		Connection con = null;
		PreparedStatement pstm = null;
		
		try {
			con = ConnectionFactory.createConnectionToMySQL();
			
			pstm = con.prepareStatement(sql.toString());
			pstm.setString(1, aluno.getNome());
			pstm.setInt(2, aluno.getIdade());
			
			pstm.execute();
			
		} catch(Exception e) {
			e.printStackTrace();
			
			return 0;
		} finally {
			try {
				if(pstm != null) {
					pstm.close();
				}
				
				if(con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return getLastId();
	}
	
	private int getLastId() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT MAX(id) AS id FROM aluno");
		
		Connection con = null;
		PreparedStatement pstm = null;
		
		try {
			con = ConnectionFactory.createConnectionToMySQL();
			
			pstm = con.prepareStatement(sql.toString());
			
			pstm.execute();
			
			ResultSet rs = pstm.getResultSet();
			rs.next();
			return rs.getInt("id");
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(pstm != null) {
					pstm.close();
				}
				
				if(con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return 0;
	}
	
	public int alterar(Aluno aluno) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("UPDATE ALUNO ");
		sql.append("SET nome = ?, idade = ? ");
		sql.append("WHERE id = ?");
		
		Connection con = null;
		PreparedStatement pstm = null;
		
		try {
			con = ConnectionFactory.createConnectionToMySQL();
			
			pstm = con.prepareStatement(sql.toString());
			pstm.setString(1, aluno.getNome());
			pstm.setInt(2, aluno.getIdade());
			pstm.setInt(3, aluno.getId());
			
			pstm.execute();
			
		} catch(Exception e) {
			e.printStackTrace();
			
			return 0;
		} finally {
			try {
				if(pstm != null) {
					pstm.close();
				}
				
				if(con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		
		return aluno.getId();
	}
	
	public boolean delete(int id) {
		StringBuilder sql = new StringBuilder();
		
		Connection con = null;
		PreparedStatement pstm = null;
		
		sql.append("DELETE FROM ALUNO WHERE id = ?");
		
		try {
			con = ConnectionFactory.createConnectionToMySQL();
			
			pstm = con.prepareStatement(sql.toString());
			
			pstm.setInt(1, id);
			
			pstm.execute();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			ConnectionFactory.close(pstm, con);
		}
		
		return true;
	}
	
	public Aluno getById(int id) {
		StringBuilder sql = new StringBuilder();
		
		Connection con = null;
		PreparedStatement pstm = null;
		
		sql.append("SELECT ");
		sql.append("id, ");
		sql.append("nome, ");
		sql.append("idade ");
		sql.append("FROM aluno ");
		sql.append("WHERE id = ?;");
		
		try {
			con = ConnectionFactory.createConnectionToMySQL();
			pstm = con.prepareStatement(sql.toString());
			
			pstm.setInt(1, id);
			
			pstm.execute();
			ResultSet r = pstm.getResultSet();
			
			Aluno aluno = new Aluno();
			
			if(!r.next()) {
				return null;
			}
			
			aluno.setId(r.getInt("id"));
			aluno.setNome(r.getString("nome"));
			aluno.setIdade(r.getInt("idade"));
			
			return aluno;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean validarCampos(String id, String nome, String idade) {
		if(id.isBlank()) {
			return false;
		}
		if(nome.isBlank()) {
			return false;
		}
		if(idade.isBlank()) {
			return false;
		}
		
		return true;
	}
	
}
