package co.test.ada.rest.client.response;

import java.util.List;

public class StationsDataAgua implements IStationsData<StationAgua> {
	private String mensaje;
	private List<StationAgua> datos = null;
	@Override
	public String getMensaje() {
	return mensaje;
	}
	@Override
	public void setMensaje(String mensaje) {
	this.mensaje = mensaje;
	}
	@Override
	public List<StationAgua> getDatos() {
	return datos;
	}
	@Override
	public void setDatos(List<StationAgua> datos) {
	this.datos = datos;
	}
}
