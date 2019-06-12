package co.test.ada.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.test.ada.svc.LoadMarkersSvc;

@RestController
@RequestMapping("/api-test")
public class LoadMarkerController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoadMarkerController.class);
	
	@Autowired
	LoadMarkersSvc loadLayersSvc;
	

//	@ApiOperation(value = "/contenedora/load-markers-categoria", notes = "Rest TEST para cargar info de los marcadores por CATEGORIA desde el AMVA")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Recuperación exitosa"),
//			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
//			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto") })
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	
	
	@ResponseBody
	@PutMapping("/contenedora/application/category/{idCategoria}/markers")
	public ResponseEntity<Boolean> getMarkerXCategoria(@PathVariable("idCategoria") Long idCategoria) {
		boolean cargadoComercio;
		try {
			cargadoComercio = loadLayersSvc.updateMarkersCategoria(idCategoria);
			return new ResponseEntity<Boolean>(cargadoComercio, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error al actualizar markers por categoría!!", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
