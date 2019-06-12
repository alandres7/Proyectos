package co.test.ada.svc.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import co.test.ada.entity.Marker;
import co.test.ada.gateway.MarkersProvider;
import co.test.ada.repo.MarkerDao;
import co.test.ada.rest.client.request.CoordenadaDto;
import co.test.ada.rest.client.response.Feature;
import co.test.ada.svc.LoadMarkersSvc;
import co.test.ada.util.GeometryUtil;

@Service
public class LoadLayersSvcImpl implements LoadMarkersSvc {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoadLayersSvcImpl.class);

	private static final String ALIAS_NOMBRE = "NOMBRE";
	private String urlRecurso;
	private static final String ALIAS_DESCRIPCION = "DESCR";

	private static final String ARCGIS_ID = "OBJECTID";
	private static final String ALIAS_MUNICIPIO = "MUNICIPIO";
	private String aliasTipo;
	private String aliasDireccion;
	private String aliasImagen;
	private String aliasImagen2;
	private Boolean isPoligono;
	private Long idCategoriaAux;

	@Autowired
	MarkersProvider proveedorMarkers;

	@Autowired
	MarkerDao markerDao;

	@Override
	public boolean updateMarkersCategoria(Long idCategoria) throws Exception {
		StringBuilder fieldsQuery = new StringBuilder("");
		urlRecurso = "http://sim.metropol.gov.co/arcgis/rest/services/App24-7/ClasificacionSuelo/MapServer/0";
		idCategoriaAux = idCategoria;
		if (!validateRequiredFieldsCategoria())
			return Boolean.FALSE;
		fieldsQuery.append(getItemsCategoriaWS());
		List<Feature> features = proveedorMarkers.getFeatures(urlRecurso, fieldsQuery.toString());
		if (features == null)
			return Boolean.FALSE;
		List<Marker> markers = new ArrayList<>();
		LOGGER.info("Cantidad de Polígonos: " + features.size());
		isPoligono = Boolean.TRUE;
		features.forEach(feature -> {
			if (!"".equals(feature.getAttributes().get(ALIAS_NOMBRE).getAsString().trim())) {
				LOGGER.info("Validación Recorrido OBJECT ID con Nombre:"+feature.getAttributes().get(ARCGIS_ID).getAsString());
				crearMarcador(feature, ALIAS_NOMBRE, 
								ALIAS_DESCRIPCION, 
								ALIAS_MUNICIPIO,
								aliasDireccion,
								aliasImagen,
								aliasImagen2);
			}else {
				LOGGER.error("Validación Recorrido OBJECT ID:"+feature.getAttributes().get(ARCGIS_ID).getAsString());
			}
		});
		return Boolean.TRUE;
	}

	private void crearMarcador(Feature feature, String aliasNombre, String aliasDescripcion, String aliasMunicipio,
			String aliasDireccion, String aliasImagen, String aliasImagen2) {
		Marker marcador = new Marker();
		marcador.setNombre(feature.getAttributes().get(aliasNombre).getAsString());
		try {
			marcador.setArcgisId(feature.getAttributes().get(ARCGIS_ID).getAsString());
		} catch (Exception e) {
			marcador.setArcgisId("");
		}
		try {
			marcador.setDescripcion(feature.getAttributes().get(aliasDescripcion).getAsString());
		} catch (Exception e) {
			marcador.setDescripcion(" ");
		}
		try {
			marcador.setNombreMunicipio(feature.getAttributes().get(aliasMunicipio).getAsString());
		} catch (Exception e) {
			marcador.setNombreMunicipio(" ");
		}
		try {
			marcador.setDireccion(feature.getAttributes().get(aliasDireccion).getAsString());
		} catch (Exception e) {
			marcador.setDireccion(" ");
		}
		try {
			marcador.setRutaImagen(feature.getAttributes().get(aliasImagen).getAsString());
		} catch (Exception e) {
			marcador.setRutaImagen(" ");
		}
		try {
			marcador.setRutaImagen2(feature.getAttributes().get(aliasImagen2).getAsString());
		} catch (Exception e) {
			marcador.setRutaImagen2(" ");
		}

		if (isPoligono) {
			marcador.setPoligono(Boolean.TRUE);
			marcador.setCoordenadaPolygon(polygonCreate(feature));
		} else {
			marcador.setPoligono(Boolean.FALSE);
			marcador.setCoordenadaPunto(pointCreate(feature));
		}

		marcador.setCategoria(idCategoriaAux);

		try {
			markerDao.save(marcador);
		} catch (Exception e) {
			//if (isPoligono) {
			//marcador.setPoligono(Boolean.TRUE);
			//marcador.setCoordenadaPolygon(forcePolygonCreate(feature));
			//marcadorDao.save(marcador);
			//}
			LOGGER.error("Error en anillos internos del polígono;ID ARCGIS del Polygon:" + marcador.getArcgisId(), e);
		}
	}
	
	private Polygon polygonCreate(Feature feature) {
		List<CoordenadaDto> outerRing = proveedorMarkers.guardarCoordenadas(feature);
		if(feature.getGeometry().getRings().size()==1) {
			return GeometryUtil.obtenerPuntosPolygon(outerRing);
		}else {
			List<List<CoordenadaDto>> innersRings = proveedorMarkers.getInnerRings(feature);
			return GeometryUtil.createPolygon(outerRing, innersRings);
		}
		
	}
	
	private Polygon forcePolygonCreate(Feature feature) {
		List<CoordenadaDto> outerRing = proveedorMarkers.guardarCoordenadas(feature);
		return GeometryUtil.obtenerPuntosPolygon(outerRing);
		
	}
	
	private Point pointCreate(Feature feature) {
		if (isPoligono) {
			List<CoordenadaDto> coordenadasModel = proveedorMarkers.guardarCoordenadas(feature);
			if (coordenadasModel.size() == 1) {
				CoordenadaDto punto = coordenadasModel.get(0);
				return GeometryUtil.obtenerPunto(punto.getLatitud(), punto.getLongitud());
			} else if (coordenadasModel.size() > 1) {
				Polygon polygon = GeometryUtil.obtenerPuntosPolygon(coordenadasModel);
				return polygon.getCentroid();
			} else {
				return null;
			}
		} else {
			double lat = feature.getGeometry().getY();
			double lng = feature.getGeometry().getX();
			return GeometryUtil.obtenerPunto(lat, lng);
		}
	}
	

	private boolean validateRequiredFieldsCategoria() {
		if (ALIAS_NOMBRE == null || "".equals(ALIAS_NOMBRE.trim()))
			return Boolean.FALSE;

		if (urlRecurso == null || "".equals(urlRecurso.trim()))
			return Boolean.FALSE;

		return Boolean.TRUE;
	}

	private String getItemsCategoriaWS() {
		StringBuilder itemsCategoria = new StringBuilder(ALIAS_NOMBRE);
		if (ARCGIS_ID != null && !"".equals(ARCGIS_ID.trim())) {
			itemsCategoria.append(",").append(ARCGIS_ID);
		}
		if (ALIAS_DESCRIPCION != null && !"".equals(ALIAS_DESCRIPCION.trim())) {
			itemsCategoria.append(",").append(ALIAS_DESCRIPCION);
		}
		if (ALIAS_MUNICIPIO != null && !"".equals(ALIAS_MUNICIPIO.trim())) {
			itemsCategoria.append(",").append(ALIAS_MUNICIPIO);
		}
		if (aliasTipo != null && !"".equals(aliasTipo.trim())) {
			itemsCategoria.append(",").append(aliasTipo);
		}
		if (aliasDireccion != null && !"".equals(aliasDireccion.trim())) {
			itemsCategoria.append(",").append(aliasDireccion);
		}
		if (aliasImagen != null && !"".equals(aliasImagen.trim())) {
			itemsCategoria.append(",").append(aliasImagen);
		}
		if (aliasImagen2 != null && !"".equals(aliasImagen2.trim())) {
			itemsCategoria.append(",").append(aliasImagen2);
		}
		return itemsCategoria.toString();
	}

}
