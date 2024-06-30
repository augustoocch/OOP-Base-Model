package view;
import config.CineConfig;
import controller.PeliculasController;
import model.constants.TipoGenero;
import model.constants.TipoProyeccion;
import model.dto.PeliculaDTO;
import model.exception.CinemaException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static model.exception.ErrorCode.*;


public class RegistrarPelicula extends JFrame {

	private JPanel contentPane;
	private JComboBox<TipoGenero> generoID;
	private JTextField nombre;
	private JTextField duracionEnMinutos;
	private JTextField director;
	private JComboBox<TipoProyeccion> proyeccion;
	private JComboBox<String> condicionesDescuento;
	private List<JToggleButton> actorButtons;
	private JPanel actorPanel;
	private CineConfig config = CineConfig.getInstance();


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrarPelicula frame = new RegistrarPelicula();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RegistrarPelicula() {
		setTitle("Registrar Pelicula");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		actorPanel = new JPanel();
		actorPanel.setBounds(112, 390, 600, 150);

		setearLabels();
		setearCampos();

		botonAceptar();
		botonCancelar();
	}

	private void botonAceptar(){
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(valoresNulos()) return;
					PeliculasController controller = PeliculasController.obtenerInstancia();
					PeliculaDTO peliculaDTO = new PeliculaDTO();
					peliculaDTO.setGeneroID((TipoGenero) generoID.getSelectedItem());
					peliculaDTO.setNombrePelicula(nombre.getText());
					peliculaDTO.setDuracionEnMinutos(Integer.parseInt(duracionEnMinutos.getText()));
					peliculaDTO.setDirector(director.getText());
					peliculaDTO.setActores(actorButtons.stream()
							.filter(JToggleButton::isSelected)
							.map(JToggleButton::getText)
							.collect(Collectors.toList()));
					peliculaDTO.setCondicionesDescuento(condicionesDescuento.getSelectedItem().toString());
					controller.registrarPelicula(peliculaDTO);
					mostrarMensajeExito();
					volverAlMenuPrincipal();
				} catch (CinemaException ex) {
					mostrarNuevoAlertaDeError(ex.getMessage());
				} catch (NumberFormatException ex) {
					mostrarNuevoAlertaDeError(DURACION_INVALIDA.getMessage());
				}
			}
		});
		btnAceptar.setBounds(300, 600, 116, 23);
		contentPane.add(btnAceptar);
	}

	private void botonCancelar(){
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarNuevoAlertaDeError(OPERACION_CANCELADA.getMessage());
				volverAlMenuPrincipal();
			}
		});
		btnCancelar.setBounds(450, 600, 114, 23);
		contentPane.add(btnCancelar);
	}


	private void mostrarMensajeExito() {
		JOptionPane
				.showMessageDialog(this,
						OPERACION_EXITOSA.getMessage(),
						"Exito", JOptionPane.INFORMATION_MESSAGE);
	}

	private void mostrarNuevoAlertaDeError(String mensaje) {
		JOptionPane
				.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}


	private boolean valoresNulos() {
		if (nombre.getText().isEmpty()
				|| duracionEnMinutos.getText().isEmpty()
				|| director.getText().isEmpty() || proyeccion.getSelectedItem() == null
				|| condicionesDescuento.getSelectedItem() == null
				|| actorButtons.stream().noneMatch(JToggleButton::isSelected)) {
			mostrarNuevoAlertaDeError(VALORES_NULOS.getMessage());
			return true;
		}
		return false;
	}

	private void volverAlMenuPrincipal() {
		this.setVisible(false);
		MenuPrincipal menuPrincipal = new MenuPrincipal();
		menuPrincipal.setVisible(true);
	}

	private void setearLabels(){
		JLabel lblGenero = new JLabel("Genero Pelicula:");
		lblGenero.setBounds(10, 10, 150, 14);
		contentPane.add(lblGenero);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 70, 150, 14);
		contentPane.add(lblNombre);

		JLabel lblDuracion = new JLabel("Duracion:");
		lblDuracion.setBounds(10, 130, 150, 14);
		contentPane.add(lblDuracion);

		JLabel lblDirector = new JLabel("Director:");
		lblDirector.setBounds(10, 190, 150, 14);
		contentPane.add(lblDirector);

		JLabel lblProyeccion = new JLabel("Proyeccion:");
		lblProyeccion.setBounds(10, 250, 150, 14);
		contentPane.add(lblProyeccion);

		JLabel lblCondicionesDescuento = new JLabel("Condiciones Descuento:");
		lblCondicionesDescuento.setBounds(10, 310, 175, 14);
		contentPane.add(lblCondicionesDescuento);

		JLabel lblActores = new JLabel("Actores:");
		lblActores.setBounds(10, 370, 150, 14);
		contentPane.add(lblActores);
	}

	private void setearCampos() {
		generoID = new JComboBox<TipoGenero>();
		generoID.setModel(new DefaultComboBoxModel<TipoGenero>(TipoGenero.values()));
		generoID.setBounds(10, 40, 366, 20);
		contentPane.add(generoID);

		nombre = new JTextField();
		nombre.setBounds(10, 100, 305, 20);
		contentPane.add(nombre);
		nombre.setColumns(10);

		duracionEnMinutos = new JTextField();
		duracionEnMinutos.setBounds(10, 160, 305, 20);
		contentPane.add(duracionEnMinutos);

		director = new JTextField();
		director.setBounds(10, 220, 305, 20);
		contentPane.add(director);

		proyeccion = new JComboBox<TipoProyeccion>();
		proyeccion.setModel(new DefaultComboBoxModel<TipoProyeccion>(TipoProyeccion.values()));
		proyeccion.setBounds(10, 280, 305, 20);
		contentPane.add(proyeccion);

		List<String> condicionesList = config.getCondicionesDescuento()
				.stream()
				.map(i -> String.valueOf(i.getIdDescuento()))
				.toList();

		condicionesDescuento = new JComboBox<String>();
		condicionesDescuento.setModel(new DefaultComboBoxModel<String>
				(condicionesList.toArray(new String[0])));

		condicionesDescuento.setBounds(10, 340, 305, 20);
		contentPane.add(condicionesDescuento);

		List<String> actoresList = config.getListaActores();
		actorButtons = new ArrayList<>();
		int numRows = 2;
		int numCols = (int) Math.ceil((double) actoresList.size() / numRows);
		actorPanel.setLayout(new GridLayout(numRows, numCols, 5, 5));
		actorButtons = new ArrayList<>();
		Dimension buttonSize = new Dimension(100, 5);
		for (String actor : actoresList) {
			JToggleButton button = new JToggleButton(actor);
			button.setPreferredSize(buttonSize);
			actorButtons.add(button);
			actorPanel.add(button);
		}
		contentPane.add(actorPanel);
	}
}
