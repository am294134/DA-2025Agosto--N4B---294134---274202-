import com.example.obligatorio_dda.Modelo.Puesto;
import com.example.obligatorio_dda.Modelo.Vehiculo;
import com.example.obligatorio_dda.Modelo.Propietario;

public class TransitoEmuladoDTO {

    private Puesto puesto;
    //private ArrayList<Tarifa> tarifas;
    private Vehiculo vehiculo;
    private Propietario propietario;

    public TransitoEmuladoDTO(Puesto puesto, Vehiculo vehiculo, Propietario propietario) {
        this.puesto = puesto;
        this.vehiculo = vehiculo;
        this.propietario = propietario;
    }   
}
