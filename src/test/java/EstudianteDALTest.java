import accesoadatos.EstudianteDAL;
import entidadesdenegocio.Estudiante;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

public class EstudianteDALTest {
    @Test
    public void guardarTest() throws SQLException {
        Estudiante student = new Estudiante();
        student.setCodigo("PL23007");
        student.setNombre("Juan Carlos");
        student.setApellido("Pérez López");
        student.setCarrera("TIDS");

        int esperado = 1;
        int actual = EstudianteDAL.guardar(student);
        Assertions.assertEquals(esperado, actual);
    }

    @Test
    public void modificarTest() throws SQLException{
        Estudiante student = new Estudiante();
        student.setId(2);
        student.setCodigo("PL23007");
        student.setNombre("Juan Carlos");
        student.setApellido("Pérez López");
        student.setCarrera("Mercadeo");

        int esperado = 1;
        int actual = EstudianteDAL.modificar(student);
        Assertions.assertEquals(esperado, actual);
    }

    @Test
    public void eliminarTest() throws SQLException{
        Estudiante student = new Estudiante();
        student.setId(2);

        int esperado = 1;
        int actual = EstudianteDAL.eliminar(student);
        Assertions.assertEquals(esperado, actual);
    }

    @Test
    public void obtenerTodosTest() throws SQLException{
        int actual = EstudianteDAL.obtenerTodos().size();
        Assertions.assertNotEquals(0, actual);
    }

    @Test
    public void obtenerDatosFiltradosTest() throws SQLException{
        Estudiante student = new Estudiante();
        student.setCarrera("TIE");
        student.setId(0);
        student.setApellido("");

        int actual = EstudianteDAL.obtenerDatosFiltrados(student).size();
        Assertions.assertNotEquals(0, actual);
    }
}
