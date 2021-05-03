package com.tianguisweb.api.model.servicesImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianguisweb.api.model.daos.ISliderDao;
import com.tianguisweb.api.model.entities.Slider;
import com.tianguisweb.api.model.services.ISliderService;

@Service
@Transactional
public class SliderServiceImpl implements ISliderService {

	@Autowired
	private ISliderDao slidarDao;
	
	@Override
	public Slider saveSlider(Slider slider) {
		return this.slidarDao.save(slider);
	}


	@Override
	public void delateSlider(Slider slider) {
		this.slidarDao.delete(slider);
	}


	@Override
	public Slider findSliderById(String id) {
		return this.slidarDao.findById(id).orElse(null);
	}


	@Override
	public List<Slider> listAllSlider() {
		return this.slidarDao.findAll();
	}




}
