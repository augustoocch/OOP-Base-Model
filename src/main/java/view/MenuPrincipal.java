package view;

import model.exception.CinemaException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.exception.ErrorCode.OPERACION_EXITOSA;

public class MenuPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;

	JButton btnCrearNuevaFuncion;
	JButton btnRegistrarPelicula;
	JButton btnConsultarPelicula;
	JButton btnVenderTicket;
	JButton btnConsultarRecaudacion;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		botonRegistrarFuncion();
		botonRegistrarPelicula();
		botonConsultarPelicula();
		botonVenderTicket();
		botonConsultarRecaudacion();
	}

	private void botonRegistrarFuncion() {
		btnCrearNuevaFuncion = new JButton("Crear Nueva Funcion");
		btnCrearNuevaFuncion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarCamposNuevaFuncion();
			}
		});
		btnCrearNuevaFuncion.setBounds(107, 79, 210, 23);
		contentPane.add(btnCrearNuevaFuncion);
	}

	private void botonRegistrarPelicula() {
		btnRegistrarPelicula = new JButton("Registrar Pelicula");
		btnRegistrarPelicula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarCamposRegistrarPelicula();
			}
		});
		btnRegistrarPelicula.setBounds(107, 130, 210, 23);
		contentPane.add(btnRegistrarPelicula);
	}

	private void botonConsultarPelicula() {
		btnConsultarPelicula = new JButton("Consultar Pelicula");
		btnConsultarPelicula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarCamposConsultarPelicula();
			}
		});
		btnConsultarPelicula.setBounds(107, 182, 210, 23);
		contentPane.add(btnConsultarPelicula);
	}

	private void botonVenderTicket() {
		btnVenderTicket = new JButton("Vender Ticket");
		btnVenderTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarCamposVenderTicket();
			}
		});
		btnVenderTicket.setBounds(107, 230, 210, 23);
		contentPane.add(btnVenderTicket);
	}

	private void botonConsultarRecaudacion() {
		btnConsultarRecaudacion= new JButton("Consultar Recaudacion");
		btnConsultarRecaudacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarCamposConsultarRecaudacion();
			}
		});
		btnConsultarRecaudacion.setBounds(107,282, 210, 23);
		contentPane.add(btnConsultarRecaudacion);
	}

	private void mostrarCamposNuevaFuncion() {
		this.setVisible(false);
		try{
			RegistrarFuncion registrarFuncion = new RegistrarFuncion();
			registrarFuncion.setVisible(true);
		} catch (CinemaException e) {
			mostrarNuevoAlertaDeError(e.getMessage());
			volverAlMenuPrincipal();
		}
	}

	private void mostrarCamposRegistrarPelicula(){
		this.setVisible(false);
		RegistrarPelicula registrarPelicula = new RegistrarPelicula();
		registrarPelicula.setVisible(true);
	}

	private void mostrarCamposConsultarPelicula() {
		this.setVisible(false);
		ConsultarPelicula consultarPelicula = new ConsultarPelicula();
		consultarPelicula.setVisible(true);
	}

	private void mostrarCamposVenderTicket() {
		this.setVisible(false);
		try{
			VenderTicket venderTicket = new VenderTicket();
			venderTicket.setVisible(true);
		} catch (CinemaException e) {
			mostrarNuevoAlertaDeError(e.getMessage());
			volverAlMenuPrincipal();
		}
	}

	private void mostrarCamposConsultarRecaudacion() {
		try{
			this.setVisible(false);
			ConsultarRecaudacion consultarRecaudacion = new ConsultarRecaudacion();
			consultarRecaudacion.setVisible(true);
		} catch (CinemaException e) {
			mostrarNuevoAlertaDeError(e.getMessage());
			volverAlMenuPrincipal();
		}
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

	private void volverAlMenuPrincipal() {
		this.setVisible(true);
	}

}
