package co.test.ada.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import co.test.ada.util.GeometryUtil;

@Entity
@Table(name = "D247CON_MARKER", schema = "CONTENEDOR")
public class Marker implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_MARCADOR_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	private Long id;

	@Column(name = "N_CODIGO")
	private int codigo;

	@Column(name = "S_NOMBRE", unique = true)
	private String nombre;

	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	@Column(name = "S_DIRECCION")
	private String direccion;
	
	@Column(name = "S_RUTA_IMAGEN")
	private String rutaImagen;
	
	@Column(name = "S_RUTA_IMAGEN2")
	private String rutaImagen2;

	@Column(name = "N_POLIGONO")
	private boolean poligono;

	@Size(max = 50)
	@Column(name = "S_COLOR_BORDE")
	private String colorBorde;

	@Size(max = 50)
	@Column(name = "S_COLOR_FONDO")
	private String colorFondo;

	@Column(name = "N_ACTIVO")
	private boolean activo;
	
	@Column(name = "ID_ARCGIS")
	private String arcgisId;
	
	@Column(name = "ID_ICONO")
	private Long icono;

	@Column(name = "ID_TIPO_RECURSO")
	private Long tipoRecurso;
	
	@Column(name = "ID_VENTANA_INFO")
	private Long ventanaInformacion;
	
	@Column(name="S_MUNICIPIO")
	private String nombreMunicipio;
	
	@JsonFormat(shape=JsonFormat.Shape.ARRAY)
	@Column(name = "CLB_COORDENADA_PUNTO")
	private Point coordenadaPunto;
	
	@JsonFormat(shape=JsonFormat.Shape.ARRAY)
	@Column(name = "CLB_COORDENADA_POLYGON")
	private Polygon coordenadaPolygon;
	
	@ManyToOne
	@JoinColumn(name="ID_CATEGORIA", referencedColumnName = "ID")
	private Long categoria;
	
	@Column(name = "ID_CAPA")
	private Long capa;
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
				
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
	
	public String getRutaImagen2() {
		return rutaImagen2;
	}

	public void setRutaImagen2(String rutaImagen2) {
		this.rutaImagen2 = rutaImagen2;
	}

	public String getColorBorde() {
		return colorBorde;
	}

	public void setColorBorde(String colorBorde) {
		this.colorBorde = colorBorde;
	}

	public String getColorFondo() {
		return colorFondo;
	}

	public void setColorFondo(String colorFondo) {
		this.colorFondo = colorFondo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public boolean isPoligono() {
		return poligono;
	}

	public void setPoligono(boolean poligono) {
		this.poligono = poligono;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}

	public List<String> getCoordenadaPunto() {
		return GeometryUtil.conversorCoordenada(coordenadaPunto);
	}
	
	public Point getPoint() {
		return coordenadaPunto;
	}

	public void setCoordenadaPunto(Point coordenadaPunto) {
		this.coordenadaPunto = coordenadaPunto;
	}

	public List<String> getCoordenadaPolygon() {
		return GeometryUtil.conversorCoordenada(coordenadaPolygon);
	}

	public void setCoordenadaPolygon(Polygon coordenadaPolygon) {
		this.coordenadaPolygon = coordenadaPolygon;
	}
	
	public Polygon getPolygon() {
		return this.coordenadaPolygon;
	}

	public Long getIcono() {
		return icono;
	}

	public void setIcono(Long icono) {
		this.icono = icono;
	}

	public Long getTipoRecurso() {
		return tipoRecurso;
	}

	public void setTipoRecurso(Long tipoRecurso) {
		this.tipoRecurso = tipoRecurso;
	}

	public Long getVentanaInformacion() {
		return ventanaInformacion;
	}

	public void setVentanaInformacion(Long ventanaInformacion) {
		this.ventanaInformacion = ventanaInformacion;
	}

	public Long getCategoria() {
		return categoria;
	}

	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}

	public Long getCapa() {
		return capa;
	}

	public void setCapa(Long capa) {
		this.capa = capa;
	}

	public String getArcgisId() {
		return arcgisId;
	}

	public void setArcgisId(String arcgisId) {
		this.arcgisId = arcgisId;
	}	
	
}