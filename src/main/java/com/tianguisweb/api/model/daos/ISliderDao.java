package com.tianguisweb.api.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tianguisweb.api.model.entities.Slider;

public interface ISliderDao extends JpaRepository<Slider, String>{

}
