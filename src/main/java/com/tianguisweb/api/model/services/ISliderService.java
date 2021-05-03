package com.tianguisweb.api.model.services;

import java.util.List;

import com.tianguisweb.api.model.entities.Slider;

public interface ISliderService {
	
	//Guardar el slider	
	public Slider saveSlider(Slider slider);
	//Eliminar el slider
	public void delateSlider(Slider slider);
	//Actualizar slider
	public Slider findSliderById(String id);
	//Listar slider
	public List<Slider> listAllSlider();
	
	
}
