package view;

import controller.PeliculasController;
import model.business.pelicula.Pelicula;
import model.constants.TipoGenero;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static model.exception.ErrorCode.*;


public class ConsultarPelicula extends JFrame {

    private JPanel contentPane;
    private JComboBox<TipoGenero> boxSeleccionGenero;
    private JComboBox<String> boxDePeliculasEncontradas;

    private final PeliculasController peliculasController = PeliculasController.obtenerInstancia();

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

    public ConsultarPelicula() {
        setTitle("Consultar Peliculas por genero");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        setearLabels();
        setearCampos();

        botonAceptar();
        botonCancelar();
    }

    private void botonAceptar(){
        JButton btnAceptar = new JButton("Buscar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!valoresNulos()) {
                    List<Pelicula> peliculas =  peliculasController
                            .obtenerPeliculasPorGenero((TipoGenero) boxSeleccionGenero.getSelectedItem());

                    List<String> peliculasString = peliculas.stream()
                            .map(Pelicula::getNombrePelicula)
                            .toList();

                    boxDePeliculasEncontradas.setModel(new DefaultComboBoxModel<String>(peliculasString.toArray(new String[0])));
                    mostrarMensajeExito(peliculasString.size());
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


    private void mostrarMensajeExito(int size) {
        JOptionPane
                .showMessageDialog(this,
                        "Resultados encontrados: " + size,
                        "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarNuevoAlertaDeError(String mensaje) {
        JOptionPane
                .showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }


    private boolean valoresNulos() {
        if (boxSeleccionGenero.getSelectedItem() == null) {
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
        lblGenero.setBounds(10, 10, 200, 14);
        contentPane.add(lblGenero);

        JLabel lblNombre = new JLabel("Peliculas encontradas:");
        lblNombre.setBounds(10, 70, 250, 14);
        contentPane.add(lblNombre);
    }

    private void setearCampos() {
        boxSeleccionGenero = new JComboBox<TipoGenero>();
        boxSeleccionGenero.setModel(new DefaultComboBoxModel<TipoGenero>(TipoGenero.values()));
        boxSeleccionGenero.setBounds(10, 40, 366, 20);
        contentPane.add(boxSeleccionGenero);

        boxDePeliculasEncontradas = new JComboBox<String>();
        boxDePeliculasEncontradas.setBounds(10, 90, 150, 20);
        contentPane.add(boxDePeliculasEncontradas);
    }
}
