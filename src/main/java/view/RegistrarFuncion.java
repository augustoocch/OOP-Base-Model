package view;


import controller.FuncionController;
import controller.PeliculasController;
import controller.SucursalController;
import model.business.cine.Sala;
import model.business.pelicula.Pelicula;
import model.dto.FuncionDTO;
import model.exception.CinemaException;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import static model.exception.ErrorCode.*;
import static model.utils.NegocioUtils.*;


public class RegistrarFuncion extends JFrame {
    private JComboBox<String> peliculaNombre;
    private JComboBox<String> horarios;
    private JComboBox<String> salas;
    private JPanel contentPane;
    UtilDateModel model;
    JDatePanelImpl datePanel;
    JDatePickerImpl datePicker;
    private PeliculasController peliculasController = PeliculasController.obtenerInstancia();
    private FuncionController funcionController = FuncionController.obtenerInstancia();
    private SucursalController sucursalController = SucursalController.obtenerInstancia();


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

    public RegistrarFuncion() throws CinemaException {
        List<String> peliculasList = validarPeliculas();
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

    private void setLabels() {
        setTitle("Registrar Funcion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblPelicula = new JLabel("Pelicula");
        lblPelicula.setBounds(112, 50, 200, 14);
        contentPane.add(lblPelicula);

        JLabel lblHorario = new JLabel("Horario");
        lblHorario.setBounds(112, 120, 200, 14);
        contentPane.add(lblHorario);

        JLabel lblFecha = new JLabel("Fecha");
        lblFecha.setBounds(112, 220, 200, 14);
        contentPane.add(lblFecha);

        JLabel lblSala = new JLabel("Sala");
        lblSala.setBounds(112, 290, 200, 14);
        contentPane.add(lblSala);
    }

    private void setContenido(List<String> peliculasList) {
        peliculaNombre = new JComboBox<String>();
        peliculaNombre.setBounds(112, 70, 200, 20);
        peliculaNombre.setModel(new DefaultComboBoxModel<String>
                (peliculasList.toArray(new String[0])));
        contentPane.add(peliculaNombre);

        horarios = new JComboBox<String>();
        horarios.setModel(new DefaultComboBoxModel<>(getListadoHorasCine().toArray(new String[0])));
        horarios.setBounds(112, 140, 200, 20);
        contentPane.add(horarios);


        LocalDate currentDate = LocalDate.now();
        model = new UtilDateModel();
        model.setDate(currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth());
        model.setSelected(true);

        datePanel = new JDatePanelImpl(model, new Properties());
        datePicker = new JDatePickerImpl(datePanel, null);
        datePicker.setBounds(112, 240, 200, 20);
        contentPane.add(datePicker);

        List<String> salasList = obtenerSalas();
        salas = new JComboBox<String>();
        salas.setModel(new DefaultComboBoxModel<>(salasList.toArray(new String[0])));
        salas.setBounds(112, 310, 200, 20);
        contentPane.add(salas);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(100, 350, 114, 23);
        contentPane.add(btnRegistrar);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarFuncion();
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarNuevoAlertaDeError(OPERACION_CANCELADA.getMessage());
                volverAlMenuPrincipal();
            }
        });
        btnCancelar.setBounds(250, 350, 114, 23);
        contentPane.add(btnCancelar);
    }

    private void registrarFuncion() {
        try {
            Pelicula pelicula = peliculasController.obtenerPeliculaPorNombre((String) peliculaNombre.getSelectedItem());
            Sala sala = sucursalController.obtenerSalaPorDenominacion((String) salas.getSelectedItem());
            FuncionDTO funcionDTO = new FuncionDTO(
                    pelicula,
                    (String) horarios.getSelectedItem(),
                    model.getValue(),
                    sala
            );
            funcionController.registrarFuncionPorGenero(funcionDTO);
            mostrarMensajeExito();
            volverAlMenuPrincipal();
        } catch (CinemaException e) {
            mostrarNuevoAlertaDeError(e.getMessage());
        }
    }

    private List<String> obtenerSalas() {
        List<Sala> obtenerSalas = getTodasLasSalas();
        return obtenerSalas.stream()
                .map(Sala::getDenominacion)
                .toList();
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
        this.setVisible(false);
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        menuPrincipal.setVisible(true);
    }


}
