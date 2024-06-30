package view;

import controller.DescuentoController;
import controller.FuncionController;
import controller.PeliculasController;
import controller.VentasController;
import model.business.cine.Funcion;
import model.business.negocio.TarjetaDescuento;
import model.dto.FuncionDTO;
import model.dto.VentaDTO;
import model.exception.CinemaException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;
import static model.exception.ErrorCode.*;
import static model.exception.ErrorCode.VALORES_NULOS;

public class SeleccionarFuncion extends JFrame {
    private JPanel contentPane;
    private JTextField idTarjeta;
    private JTextField numeroTarjeta;
    private List<String> funciones;
    private JTextField asientos;
    private JComboBox<String> boxFunciones;
    private final PeliculasController peliculasController = PeliculasController.obtenerInstancia();
    private final VentasController ventasController = VentasController.obtenerInstancia();
    private final DescuentoController descuentoController = DescuentoController.obtenerInstancia();
    private final FuncionController funcionesController = FuncionController.obtenerInstancia();


    public void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RegistrarFuncion frame = new RegistrarFuncion();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SeleccionarFuncion(String pelicula, String tipoTarjeta) throws CinemaException {
        funciones = new ArrayList<>();
        List<Funcion> funcionesList = funcionesController
                .obtenerFuncionesPorNombrePelicula(pelicula);
        if (funcionesList.isEmpty()) {
            throw new CinemaException(NO_HAY_FUNCIONES.getMessage(), NO_HAY_FUNCIONES.getCode());
        }
        funciones = funcionesList
                .stream()
                .map(Funcion::toString).toList();
        setLabels(tipoTarjeta);
        setContenido(funcionesList);

        botonAceptar();
        botonCancelar();
    }


    public void setLabels(String tipoTarjeta) {
        setTitle("Selecionar Funcion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        if(!tipoTarjeta.equals("Sin Descuento")) {
            JLabel lblTarjetId = new JLabel("ID Tarjeta");
            lblTarjetId.setBounds(100, 100, 100, 20);
            contentPane.add(lblTarjetId);

            idTarjeta = new JTextField();
            idTarjeta.setBounds(200, 100, 100, 20);
            contentPane.add(idTarjeta);

            JLabel lblNumeroTarjeta = new JLabel("Numero Tarjeta");
            lblNumeroTarjeta.setBounds(100, 150, 100, 20);
            contentPane.add(lblNumeroTarjeta);

            numeroTarjeta = new JTextField();
            numeroTarjeta.setBounds(200, 150, 100, 20);
            contentPane.add(numeroTarjeta);
        }

        JLabel lblFunciones = new JLabel("Funciones");
        lblFunciones.setBounds(100, 200, 100, 20);
        contentPane.add(lblFunciones);

        JLabel asientos = new JLabel("Asientos (Separados por coma)");
        asientos.setBounds(100, 250, 100, 20);
        contentPane.add(asientos);
    }

    public void setContenido(List<Funcion> funcionesList) {
        boxFunciones = new JComboBox<>();
        boxFunciones.setBounds(200, 200, 200, 20);
        contentPane.add(boxFunciones);

        for (Funcion funcion : funcionesList) {
            boxFunciones.addItem(funcion.getPelicula().getNombrePelicula() + " - " +
                    funcion.getFuncionID());
        }

        asientos = new JTextField();
        asientos.setBounds(200, 250, 200, 20);
        contentPane.add(asientos);
    }



    private void botonAceptar(){
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!valoresNulos()) {
                        String idFuncion = boxFunciones
                                .getSelectedItem()
                                .toString()
                                .split("-")[1];
                        int idFuncionInt = parseInt(idFuncion.trim());
                        FuncionDTO funcion = funcionesController.obtenerFuncionPorId(idFuncionInt);

                        TarjetaDescuento tarjeta = new TarjetaDescuento();
                        tarjeta.setNumeroTarjeta(numeroTarjeta.getText());
                        tarjeta.setTarjetaID(parseInt(idTarjeta.getText()));
                        //validar tarjeta

                        List<Integer> asientosSeleccionados = obtenerAsientosSeleccionados(asientos.getText());
                        int cantidadDeAsientos = asientosSeleccionados.size();

                        VentaDTO ventaDTO = new VentaDTO();
                        ventaDTO.setFuncionDTO(funcion);
                        ventaDTO.setAsientosSeleccionados(asientosSeleccionados);
                        ventaDTO.setFchVenta(Date.from(Instant.now()));
                        ventaDTO.setTarjetaDescuento(tarjeta);
                        ventaDTO.setAsientos(cantidadDeAsientos);
                        ventaDTO.setTarjetaDescuento();

                        ventasController.registrarVenta(ventaDTO);
                        mostrarMensajeExito();
                        volverAlMenuPrincipal();
                    }
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

    private List<Integer> obtenerAsientosSeleccionados(String asientos) {
        String[] asientosArray = asientos.split(",");
        List<Integer> asientosSeleccionados = new ArrayList<>();
        for (String asiento : asientosArray) {
            asientosSeleccionados.add(parseInt(asiento));
        }
        return asientosSeleccionados;
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
        if (idTarjeta.getText().isEmpty() || numeroTarjeta.getText().isEmpty()) {
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




}
