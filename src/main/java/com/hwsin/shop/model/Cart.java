package com.hwsin.shop.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonIgnoreProperties({"cart","product"})
	private List<CartProduct> wishList = new ArrayList<>();
	
	public void addCartProducts(CartProduct cartProduct) {
		wishList.add(cartProduct);
	}

}
