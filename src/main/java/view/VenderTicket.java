package view;

import controller.DescuentoController;
import controller.PeliculasController;
import controller.VentasController;
import model.business.pelicula.Pelicula;
import model.exception.CinemaException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static model.exception.ErrorCode.*;
import static model.exception.ErrorCode.NO_HAY_PELICULAS;


public class VenderTicket extends JFrame {
    private JPanel contentPane;
    private JComboBox<String> boxPeliculas;
    private JComboBox<String> boxTarjetaDescuento;
    private List<String> peliculasList;
    private final PeliculasController peliculasController = PeliculasController.obtenerInstancia();
    private final VentasController ventasController = VentasController.obtenerInstancia();
    private final DescuentoController descuentoController = DescuentoController.obtenerInstancia();


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

    public VenderTicket() throws CinemaException {
        peliculasList = new ArrayList<>();
        peliculasList = validarPeliculas();
        setLabels();
        setContenido(peliculasList);
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
                    mostrarSeleccionarFuncion();
                }
            }
        });
        btnAceptar.setBounds(150, 350, 114, 23);
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
        btnCancelar.setBounds(350, 350, 114, 23);
        contentPane.add(btnCancelar);
    }

    private void mostrarSeleccionarFuncion() {
        this.setVisible(false);
        try{
            SeleccionarFuncion eleccion = new SeleccionarFuncion(
                    boxPeliculas.getSelectedItem().toString(),
                    boxTarjetaDescuento.getSelectedItem().toString());
            eleccion.setVisible(true);
        } catch (CinemaException e) {
            mostrarNuevoAlertaDeError(e.getMessage());
            volverAlMenuPrincipal();
        }
    }

    private void mostrarNuevoAlertaDeError(String mensaje) {
        JOptionPane
                .showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }


    private void volverAlMenuPrincipal() {
        this.setVisible(false);
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        menuPrincipal.setVisible(true);
    }

    private boolean valoresNulos() {
        if (boxPeliculas.getSelectedItem() == null) {
            mostrarNuevoAlertaDeError(VALORES_NULOS.getMessage());
            return true;
        }
        return false;
    }

    private void setLabels(){
        setTitle("Registrar Venta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 600);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblPelicula = new JLabel("Pelicula");
        lblPelicula.setBounds(50, 50, 100, 23);
        contentPane.add(lblPelicula);

        JLabel lblTarjetaDescuento = new JLabel("Tarjeta Descuento");
        lblTarjetaDescuento.setBounds(50, 150, 200, 23);
        contentPane.add(lblTarjetaDescuento);

        botonAceptar();
        botonCancelar();
    }

    private void setContenido(List<String> peliculasList) {
        boxPeliculas = new JComboBox<String>();
        boxPeliculas.setBounds(200, 50, 200, 23);
        boxPeliculas.setModel(new DefaultComboBoxModel<String>
                (peliculasList.toArray(new String[0])));
        contentPane.add(boxPeliculas);

        List<String> tarjetasList = new ArrayList<>(descuentoController.obtenerTarjetas()
                .stream()
                .toList());

        tarjetasList.add(0, "Sin Descuento");

        boxTarjetaDescuento = new JComboBox<String>();
        boxTarjetaDescuento.setBounds(200, 150, 200, 23);
        boxTarjetaDescuento.setModel(new DefaultComboBoxModel<String>
                (tarjetasList.toArray(new String[0])));
        contentPane.add(boxTarjetaDescuento);
    }
}
