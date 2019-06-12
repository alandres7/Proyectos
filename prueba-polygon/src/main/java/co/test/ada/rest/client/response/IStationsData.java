package co.test.ada.rest.client.response;

import java.util.List;


public interface IStationsData<T> {
	public List<T> getDatos();
	public String getMensaje();
	public void setDatos(List<T> datos);
	public void setMensaje(String mensaje);
}
