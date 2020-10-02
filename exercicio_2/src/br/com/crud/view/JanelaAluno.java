package br.com.crud.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.crud.dao.AlunoDAO;
import br.com.crud.model.Aluno;

public class JanelaAluno extends JFrame{

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	
	private JTextField txtId;
	private JTextField txtNome;
	private JTextField txtIdade;
	
	private JLabel lblId;
	private JLabel lblNome;
	private JLabel lblIdade;
	
	private JButton btnGravar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	
	private AlunoDAO alunoDAO;
	
	public JanelaAluno(String title) {
		super(title);
		
		alunoDAO = new AlunoDAO();
		
		criarComponentes();
		configurarJanela();
		configurarComponentes();
	}
	
	private void criarComponentes() {
		panel = new JPanel();
		
		txtId = new JTextField(5);
		txtNome = new JTextField(5);
		txtIdade = new JTextField(5);
		
		lblId = new JLabel("Codigo");
		lblNome = new JLabel("Nome");
		lblIdade = new JLabel("Idade");
		
		btnGravar = new JButton("Gravar");
		btnExcluir = new JButton("Excluir");
		btnLimpar = new JButton("Limpar");
		
		panel.add(lblId);
		panel.add(txtId);
		
		panel.add(lblNome);
		panel.add(txtNome);
		
		panel.add(lblIdade);
		panel.add(txtIdade);
		
		panel.add(btnGravar);
		panel.add(btnExcluir);
		panel.add(btnLimpar);
		
		add(panel);
	}
	
	private void configurarJanela() {
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}
	
	private void configurarComponentes() {
		setComponentesHabilitados(false, btnExcluir);
		
		OuvinteBotao ouvinteB = new OuvinteBotao();
		
		btnGravar.addActionListener(ouvinteB);
		btnExcluir.addActionListener(ouvinteB);
		btnLimpar.addActionListener(ouvinteB);
		txtId.addFocusListener(new OuvinteCampo());
	}
	
	private void setComponentesHabilitados(boolean flag, Component...components) {
		for (Component component : components) {
			component.setEnabled(flag);
		}
	}
	
	private void limparCampos(JTextField...components) {
		for (JTextField component : components) {
			component.setText("");
		}
	}
	
	protected boolean validarCampos() {
		if(txtIdade.getText().isBlank()) {
			return false;
		}
		if(txtNome.getText().isBlank()) {
			return false;
		}
		
		return true;
	}
	
	private void gravar() {
		if(!validarCampos()) {
			JOptionPane.showInternalMessageDialog(null, "Preencha todos os campos corretamente!");
			return;
		}
		
		Aluno aluno = new Aluno();
		
		if(!txtId.getText().isBlank()) {
			aluno.setId(Integer.parseInt(txtId.getText()));
		}
		aluno.setNome(txtNome.getText());
		aluno.setIdade(Integer.parseInt(txtIdade.getText()));
		
		int retorno = alunoDAO.save(aluno);
		
		if(retorno != 0) {
			JOptionPane.showInternalMessageDialog(null, String.format("Aluno salvo com sucesso! Codigo: %d", retorno));
			limparCampos(txtId, txtIdade, txtNome);
		}
	}
	
	private void excluir() {
		if(alunoDAO.delete(Integer.parseInt(txtId.getText()))) {
			JOptionPane.showInternalMessageDialog(null, "Registro excluido com sucesso!");
			limparCampos(txtId, txtIdade, txtNome);
			setComponentesHabilitados(false, btnExcluir);
		}
	}
	
	private boolean buscarRegistro() {
		if(txtId.getText().isBlank()) {
			return false;
		}
		int id = 0;
		
		try {
			id = Integer.parseInt(txtId.getText());
		}catch(NumberFormatException numberFormat) {
			JOptionPane.showMessageDialog(null, "Insira apenas números em id!");
			limparCampos(txtId, txtNome, txtIdade);
			return false;
		}
		
		Aluno alunoEncontrado = alunoDAO.getById(id);
		
		if(alunoEncontrado != null) {
			txtId.setText(String.valueOf(alunoEncontrado.getId()));
			txtNome.setText(alunoEncontrado.getNome());
			txtIdade.setText(String.valueOf(alunoEncontrado.getIdade()));
			
			setComponentesHabilitados(true, btnExcluir, btnLimpar);
		} else {
			JOptionPane.showInternalMessageDialog(null, "Registro não encontrado");
			setComponentesHabilitados(false, btnExcluir, btnLimpar);
			limparCampos(txtId, txtNome, txtIdade);
			return false;
		}
		
		return true;
	}
	
	public class OuvinteBotao implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnGravar) {
				gravar();
			}	
			
			if(e.getSource() == btnExcluir) {
				excluir();
			}
			
			if(e.getSource() == btnLimpar) {
				limparCampos(txtId, txtNome, txtIdade);
				setComponentesHabilitados(false, btnExcluir);
			}
		}
	}
	
	public class OuvinteCampo implements FocusListener{
		@Override
		public void focusGained(FocusEvent e) {
			limparCampos(txtId);
		}

		@Override
		public void focusLost(FocusEvent e) {
			if(e.getSource() == txtId) {
				buscarRegistro();
			}
		}
	}
}
