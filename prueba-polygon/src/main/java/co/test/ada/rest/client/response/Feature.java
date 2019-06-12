package co.test.ada.rest.client.response;

import com.google.gson.JsonObject;



public class Feature {
	
	public Feature(JsonObject attributes, Geometry geometry) {
		this.attributes = attributes;
		this.geometry = geometry;
	}
	
	public Feature() {
	}
	
	private Geometry geometry;
	private JsonObject attributes;
	
	public JsonObject getAttributes() {
		return attributes;
	}

	public void setAttributes(JsonObject attributes) {
		this.attributes = attributes;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	} 
	
	
	/* private JsonObject attributes;
	private Geometry geometry;
	
	public Feature(Attribute attributes, Geometry geometry) {
		this.attributes = attributes;
		this.geometry = geometry;
	}

	public Attribute getAttributes() {
		return attributes;
	}

	public void setAttributes(Attribute attributes) {
		this.attributes = attributes;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	} 
	*/
	

}
