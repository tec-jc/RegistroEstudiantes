package interfazgrafica;

import entidadesdenegocio.Estudiante;
import logicadenegocio.EstudianteBL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class MantenimientoEstudiantes extends JFrame {
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtCodigo;
    private JTextField txtApellido;
    private JComboBox cbCarrera;
    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnCancelar;
    private JButton btnSalir;
    private JRadioButton rdbId;
    private JRadioButton rdbApellido;
    private JRadioButton rdbCarrera;
    private JTextField txtCriterio;
    private JButton btnBuscar;
    private JTable jtEstudiantes;
    private JPanel jpPrincipal;
    private ButtonGroup criterioBusqueda;

    // instancias propias
    ArrayList<Estudiante> listaEstudiantes;
    Estudiante student;
    EstudianteBL estudianteBL = new EstudianteBL();

    public static void main(String[] args) throws SQLException {
        new MantenimientoEstudiantes();
    }

    public MantenimientoEstudiantes() throws SQLException {
        inicializar();
        actualizarForm();
        btnSalir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });
        btnCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try{
                    actualizarForm();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnNuevo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                txtCodigo.setEnabled(true);
                txtNombre.setEnabled(true);
                txtApellido.setEnabled(true);
                cbCarrera.setEnabled(true);
                txtCodigo.grabFocus();
                btnGuardar.setEnabled(true);
                btnNuevo.setEnabled(false);
                btnCancelar.setEnabled(true);
            }
        });
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                student = new Estudiante();
                student.setCodigo(txtCodigo.getText());
                student.setNombre(txtNombre.getText());
                student.setApellido(txtApellido.getText());
                student.setCarrera(cbCarrera.getSelectedItem().toString());
                try{
                    estudianteBL.guardar(student);
                    JOptionPane.showMessageDialog(null, "Se guardó con éxito");
                    actualizarForm();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                student = new Estudiante();
                student.setId(Integer.parseInt(txtId.getText()));
                student.setCodigo(txtCodigo.getText());
                student.setNombre(txtNombre.getText());
                student.setApellido(txtApellido.getText());
                student.setCarrera(cbCarrera.getSelectedItem().toString());
                try{
                    estudianteBL.modificar(student);
                    JOptionPane.showMessageDialog(null, "Se modificó con éxito");
                    actualizarForm();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        jtEstudiantes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int fila = jtEstudiantes.getSelectedRow();
                txtId.setText(jtEstudiantes.getValueAt(fila, 0).toString());
                txtCodigo.setText(jtEstudiantes.getValueAt(fila, 1).toString());
                txtNombre.setText(jtEstudiantes.getValueAt(fila, 2).toString());
                txtApellido.setText(jtEstudiantes.getValueAt(fila, 3).toString());
                cbCarrera.setSelectedItem(jtEstudiantes.getValueAt(fila, 4));

                txtCodigo.setEnabled(true);
                txtNombre.setEnabled(true);
                txtApellido.setEnabled(true);
                cbCarrera.setEnabled(true);
                txtCodigo.grabFocus();

                btnNuevo.setEnabled(false);
                btnModificar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnCancelar.setEnabled(true);
            }
        });
        btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                student = new Estudiante();
                student.setId(Integer.parseInt(txtId.getText()));
                try{
                    estudianteBL.eliminar(student);
                    JOptionPane.showMessageDialog(null, "Se eliminó correctamente");
                    actualizarForm();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(txtCriterio.getText().equals("") || (!rdbId.isSelected() &&
                        !rdbApellido.isSelected() && !rdbCarrera.isSelected()) ){
                    JOptionPane.showMessageDialog(null,
                            "Seleccione un criterio de búsqueda o escriba el valor a buscar");
                }

                student = new Estudiante();

                if(rdbId.isSelected()){
                    student.setId(Integer.parseInt(txtCriterio.getText()));
                    try{
                        llenarTabla(estudianteBL.obtenerDatosFiltrados(student));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if(rdbApellido.isSelected()){
                    student.setApellido(txtCriterio.getText());
                    try{
                        llenarTabla(estudianteBL.obtenerDatosFiltrados(student));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if(rdbCarrera.isSelected()){
                    student.setCarrera(txtCriterio.getText());
                    try{
                        llenarTabla(estudianteBL.obtenerDatosFiltrados(student));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    void inicializar(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 700);
        setTitle("Control de Estudiantes");
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        criterioBusqueda = new ButtonGroup();
        criterioBusqueda.add(rdbId);
        criterioBusqueda.add(rdbApellido);
        criterioBusqueda.add(rdbCarrera);

        setContentPane(jpPrincipal);
        setVisible(true);
    }

    void llenarTabla(ArrayList<Estudiante> estudiantes){
        Object[] objects = new Object[5];
        listaEstudiantes = new ArrayList<>();
        String[] encabezado = {"ID", "CÓDIGO", "NOMBRE", "APELLIDO", "CARRERA"};
        DefaultTableModel tm = new DefaultTableModel(null, encabezado);
        listaEstudiantes.addAll(estudiantes);
        for(Estudiante item : listaEstudiantes){
            objects[0] = item.getId();
            objects[1] = item.getCodigo();
            objects[2] = item.getNombre();
            objects[3] = item.getApellido();
            objects[4] = item.getCarrera();
            tm.addRow(objects);
        }
        jtEstudiantes.setModel(tm);
    }

    void actualizarForm() throws SQLException {
        txtId.setText("");
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        cbCarrera.setSelectedIndex(0);

        txtId.setEnabled(false);
        txtCodigo.setEnabled(false);
        txtNombre.setEnabled(false);
        txtApellido.setEnabled(false);
        cbCarrera.setEnabled(false);

        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);

        txtCriterio.setText("");
        criterioBusqueda.clearSelection();

        llenarTabla(estudianteBL.obtenerTodos());
    }
}
