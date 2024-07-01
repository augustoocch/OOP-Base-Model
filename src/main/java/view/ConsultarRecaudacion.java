package view;

import controller.PeliculasController;
import controller.VentasController;
import model.business.pelicula.Pelicula;
import model.exception.CinemaException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static model.exception.ErrorCode.*;
import static model.exception.ErrorCode.NO_HAY_PELICULAS;

public class ConsultarRecaudacion extends JFrame {
    private JPanel contentPane;
    private JComboBox<String> boxDePeliculasEncontradas;

    private final PeliculasController peliculasController = PeliculasController.obtenerInstancia();
    private final VentasController ventasController = VentasController.obtenerInstancia();

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

    public ConsultarRecaudacion() throws CinemaException {
        List<String> peliculasList = validarPeliculas();
        setTitle("Consultar Recaudacion por pelicula");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        setLabels();
        setContenido(peliculasList);

        botonAceptar();
        botonCancelar();
    }

    private List<String> validarPeliculas() throws CinemaException {
        List<Pelicula> peliculasList = peliculasController.getPeliculas();
        if (peliculasList.isEmpty()) {
            throw new CinemaException(NO_HAY_PELICULAS.getMessage(), NO_HAY_PELICULAS.getCode());
        }
        return peliculasList.stream().map(Pelicula::getNombrePelicula).toList();
    }

    private void botonAceptar(){
        JButton btnAceptar = new JButton("Buscar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!valoresNulos()) {
                    String pelicula = boxDePeliculasEncontradas.getSelectedItem().toString();
                    Pelicula peliculaEncontrada =  peliculasController
                            .obtenerPeliculaPorNombre(boxDePeliculasEncontradas.getSelectedItem().toString());
                    int id = peliculaEncontrada.getPeliculaID();
                    float recaudacion = ventasController.recaudacionPorPelicula(id);
                    mostrarMensajeExito(recaudacion, pelicula);
                }
            }
        });
        btnAceptar.setBounds(300, 150, 114, 23);
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
        btnCancelar.setBounds(450, 150, 114, 23);
        contentPane.add(btnCancelar);
    }


    private void mostrarMensajeExito(float recaudacion, String pelicula) {
        JOptionPane
                .showMessageDialog(this,
                        "Recaudacion de la pelicula " + pelicula + ": " + recaudacion,
                        "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarNuevoAlertaDeError(String mensaje) {
        JOptionPane
                .showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }


    private boolean valoresNulos() {
        if (boxDePeliculasEncontradas.getSelectedItem() == null) {
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

    private void setLabels(){
        JLabel lblGenero = new JLabel("Pelicula a consultar:");
        lblGenero.setBounds(10, 10, 200, 14);
        contentPane.add(lblGenero);
    }

    private void setContenido(List<String> peliculasString) {
        boxDePeliculasEncontradas = new JComboBox<String>();
        boxDePeliculasEncontradas.setBounds(10, 50, 200, 20);
        boxDePeliculasEncontradas.setModel(new DefaultComboBoxModel<String>
                (peliculasString.toArray(new String[0])));
        contentPane.add(boxDePeliculasEncontradas);
    }
}
